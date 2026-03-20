package juego;

import clases.Carta;
import clases.Cuadrante;
import clases.Mazo;
import clases.OrdenDeJugada;
import java.util.ArrayList;
import java.util.List;

public class JuegoSolitario {

    private static final int CANTIDAD_ESPACIOS = 8;
    private static final int INDICE_INICIAL = 102;

    private Mazo mazo;
    private int indice;
    private final List<List<Carta>> espacios = new ArrayList<>();

    public JuegoSolitario(boolean mazoOrdenado) {
        for (int i = 0; i < CANTIDAD_ESPACIOS; i++) {
            espacios.add(new ArrayList<Carta>());
        }
        reiniciar(mazoOrdenado);
    }

    public void reiniciar(boolean mazoOrdenado) {
        for (List<Carta> espacio : espacios) {
            espacio.clear();
        }
        mazo = new Mazo(mazoOrdenado);
        indice = INDICE_INICIAL;
        normalizarIndice();
    }

    public Carta getCartaActual() {
        normalizarIndice();
        if (indice >= mazo.getMazo().size()) {
            return null;
        }
        return mazo.getCarta(indice);
    }

    public Carta getCartaInferiorVisible() {
        normalizarIndice();
        int siguienteIndice = indice + 1;
        if (siguienteIndice < mazo.getMazo().size()) {
            return mazo.getCarta(siguienteIndice);
        }
        return null;
    }

    public int getCartasRestantes() {
        return mazo.getMazo().size();
    }

    public int getIndiceVisible() {
        normalizarIndice();
        return indice == -2 ? mazo.getMazo().size() - 2 : indice;
    }

    public boolean mazoVacio() {
        return mazo.getMazo().isEmpty();
    }

    public Carta getTopeEspacio(int espacio) {
        List<Carta> lista = getEspacio(espacio);
        if (lista.isEmpty()) {
            return null;
        }
        return lista.get(lista.size() - 1);
    }

    public boolean jugarCartaEnEspacio(int espacio) {
        Carta cartaActual = getCartaActual();
        if (cartaActual == null) {
            return false;
        }

        List<Carta> lista = getEspacio(espacio);
        int ordenDeJugada = getOrdenDeJugada(espacio);
        if (!puedeJugarseEnEspacio(cartaActual, lista, ordenDeJugada)) {
            return false;
        }
        if (!puedeUsarPintaEnGrupo(espacio, cartaActual)) {
            return false;
        }

        lista.add(cartaActual);
        mazo.getMazo().remove(indice);
        normalizarIndice();
        return true;
    }

    public void voltearCarta() {
        indice -= 2;
        normalizarIndice();
    }

    public int getEspacioJugable(Carta carta) {
        if (carta == null) {
            return -1;
        }

        for (int espacio = Cuadrante.ESPACIO1; espacio <= Cuadrante.ESPACIO8; espacio++) {
            if (puedeJugarseEnEspacio(carta, getEspacio(espacio), getOrdenDeJugada(espacio))
                && puedeUsarPintaEnGrupo(espacio, carta)) {
                return espacio;
            }
        }

        return -1;
    }

    private boolean puedeJugarseEnEspacio(Carta carta, List<Carta> lista, int ordenDeJugada) {
        if (lista.isEmpty()) {
            return carta.getNumero() == (ordenDeJugada == OrdenDeJugada.ASCENDENTE ? 1 : 13);
        }

        Carta ultimaCarta = lista.get(lista.size() - 1);
        int numeroEsperado = ordenDeJugada == OrdenDeJugada.ASCENDENTE
            ? ultimaCarta.getNumero() + 1
            : ultimaCarta.getNumero() - 1;

        return ultimaCarta.getPinta().equalsIgnoreCase(carta.getPinta())
            && carta.getNumero() == numeroEsperado;
    }

    private boolean puedeUsarPintaEnGrupo(int espacioDestino, Carta carta) {
        for (int espacio : getGrupoDeEspacios(espacioDestino)) {
            if (espacio == espacioDestino) {
                continue;
            }
            if (isPintaEnEspacio(espacio, carta)) {
                return false;
            }
        }
        return true;
    }

    private boolean isPintaEnEspacio(int espacio, Carta carta) {
        List<Carta> lista = getEspacio(espacio);
        if (lista.isEmpty()) {
            return false;
        }
        return lista.get(0).getPinta().equalsIgnoreCase(carta.getPinta());
    }

    private int[] getGrupoDeEspacios(int espacio) {
        if (espacio >= Cuadrante.ESPACIO1 && espacio <= Cuadrante.ESPACIO4) {
            return new int[]{Cuadrante.ESPACIO1, Cuadrante.ESPACIO2, Cuadrante.ESPACIO3, Cuadrante.ESPACIO4};
        }
        return new int[]{Cuadrante.ESPACIO5, Cuadrante.ESPACIO6, Cuadrante.ESPACIO7, Cuadrante.ESPACIO8};
    }

    private int getOrdenDeJugada(int espacio) {
        return espacio >= Cuadrante.ESPACIO5 ? OrdenDeJugada.ASCENDENTE : OrdenDeJugada.DESCENDIENTE;
    }

    private List<Carta> getEspacio(int espacio) {
        return espacios.get(espacio - 1);
    }

    private void normalizarIndice() {
        if (mazo.getMazo().isEmpty()) {
            indice = 0;
            return;
        }

        if (indice == -1) {
            indice = 0;
        } else if (indice <= -2) {
            indice = mazo.getMazo().size() - 2;
        } else if (indice > mazo.getMazo().size()) {
            indice = mazo.getMazo().size();
        }
    }
}
