/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * form.java
 *
 * Created on 10-02-2011, 12:52:38 AM
 */
package app;
import cards.Card;
import cards.ImagePaths;
import cards.PixelUtils;
import cards.Quadrant;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import game.SolitaireGame;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import util.DialogMessages;
import util.UiUtils;

/**
 *
 * @author pato
 */
public class Application extends JFrame {

    private static final Logger LOGGER = createLogger();
    private static final int CARD_WIDTH = 120;
    private static final int CARD_HEIGHT = 170;
    private static final int CARD_STACK_X = 260;
    private static final int CARD_STACK_Y = 410;
    private static final int CARD_STACK_OFFSET_X = 240;
    private static final int INVALID_PLAY = -1;
    private static final int SPACE_SELECTOR_WIDTH = 610;
    private static final int SPACE_SELECTOR_HEIGHT = 230;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int TOP_SPACE_Y = 20;
    private static final int BOTTOM_SPACE_Y = 200;

    private final SolitaireGame game;
    private int labelX;
    private int labelY;
    private int dragX;
    private int dragY;
    private int dragReferenceX;
    private int dragReferenceY;

    private JFrame cardBackSelectorFrame;
    private JLabel cardBackOptionGilda;
    private JLabel cardBackOptionPato;
    private JLabel cardBackOptionFabi;
    private JLabel cardBackOptionAngeles;
    private JLabel cardBackOptionAru;
    private JLabel topCardLabel;
    private JLabel lowerCardLabel;
    private JLabel cardBackLabel;
    private JLabel descendingSpace2Label;
    private JLabel descendingSpace3Label;
    private JLabel descendingSpace4Label;
    private JLabel ascendingSpace1Label;
    private JLabel ascendingSpace2Label;
    private JLabel ascendingSpace3Label;
    private JLabel ascendingSpace4Label;
    private JLabel descendingSpace1Label;
    private JLabel deckCountTitleLabel;
    private JLabel indexLabel;
    private JLabel remainingCardsLabel;
    private JLabel backgroundLabel;
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem shuffleCardsMenuItem;
    private JPopupMenu.Separator gameMenuSeparator;
    private JMenuItem undoMoveMenuItem;
    private JMenuItem flipCardMenuItem;
    private JMenuItem changeCardBackMenuItem;
    private JMenuItem exitMenuItem;
    private JMenu viewMenu;
    private JCheckBoxMenuItem showLowerCardMenuItem;
    public Application() {
        game = new SolitaireGame(false);
        initComponents();
        ImagePaths.currentCardBack = ImagePaths.RED_CARD_BACK;
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("JSolitaire");
        refreshView();
        UiUtils.setFrameIcon(this, "/images/icon.png");
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        initCardBackSelectorFrame();
        initMainWindow();
        initMenuBar();
        pack();
    }

    private void initCardBackSelectorFrame() {
        cardBackSelectorFrame = new JFrame();
        cardBackOptionGilda = createCardBackSelectorLabel("/images/gilda.png");
        cardBackOptionPato = createCardBackSelectorLabel("/images/pato.png");
        cardBackOptionFabi = createCardBackSelectorLabel("/images/fabi.png");
        cardBackOptionAngeles = createCardBackSelectorLabel("/images/angeles.png");
        cardBackOptionAru = createCardBackSelectorLabel("/images/aru.png");

        cardBackOptionGilda.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent event) {
                handleGildaCardBackSelection(event);
            }
        });
        cardBackOptionPato.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent event) {
                handlePatoCardBackSelection(event);
            }
        });
        cardBackOptionFabi.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent event) {
                handleFabiCardBackSelection(event);
            }
        });
        cardBackOptionAngeles.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent event) {
                handleAngelesCardBackSelection(event);
            }
        });
        cardBackOptionAru.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent event) {
                handleAruCardBackSelection(event);
            }
        });

        GroupLayout selectorLayout = new GroupLayout(cardBackSelectorFrame.getContentPane());
        cardBackSelectorFrame.getContentPane().setLayout(selectorLayout);
        selectorLayout.setHorizontalGroup(
            selectorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    selectorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cardBackOptionPato, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cardBackOptionGilda, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cardBackOptionFabi, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cardBackOptionAngeles, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cardBackOptionAru, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
        );
        selectorLayout.setVerticalGroup(
            selectorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    selectorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            selectorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(cardBackOptionAru)
                                .addComponent(cardBackOptionAngeles)
                                .addComponent(cardBackOptionFabi)
                                .addComponent(cardBackOptionGilda)
                                .addComponent(cardBackOptionPato)
                        )
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
        );
    }

    private void initMainWindow() {
        topCardLabel = createPlayableCardLabel();
        lowerCardLabel = createBaseCardLabel();
        cardBackLabel = createCardBackLabel();
        descendingSpace2Label = createSpaceLabel("/images/emptySpaces/slot2.png");
        descendingSpace3Label = createSpaceLabel("/images/emptySpaces/slot3.png");
        descendingSpace4Label = createSpaceLabel("/images/emptySpaces/slot4.png");
        ascendingSpace1Label = createSpaceLabel("/images/emptySpaces/slot5.png");
        ascendingSpace2Label = createSpaceLabel("/images/emptySpaces/slot6.png");
        ascendingSpace3Label = createSpaceLabel("/images/emptySpaces/slot7.png");
        ascendingSpace4Label = createSpaceLabel("/images/emptySpaces/slot8.png");
        descendingSpace1Label = createSpaceLabel("/images/emptySpaces/slot1.png");
        deckCountTitleLabel = createStatusLabel("Cards in deck: ", SwingConstants.LEFT);
        indexLabel = createStatusLabel("", SwingConstants.RIGHT);
        remainingCardsLabel = createStatusLabel("xx", SwingConstants.LEFT);
        backgroundLabel = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new AbsoluteLayout());

        addMainWindowComponents();
    }

    private void initMenuBar() {
        menuBar = new JMenuBar();
        gameMenu = new JMenu();
        shuffleCardsMenuItem = new JMenuItem();
        gameMenuSeparator = new JPopupMenu.Separator();
        undoMoveMenuItem = new JMenuItem();
        flipCardMenuItem = new JMenuItem();
        changeCardBackMenuItem = new JMenuItem();
        exitMenuItem = new JMenuItem();
        viewMenu = new JMenu();
        showLowerCardMenuItem = new JCheckBoxMenuItem();
        gameMenu.setText("Game");

        shuffleCardsMenuItem.setText("Shuffle Cards");
        shuffleCardsMenuItem.addActionListener(this::handleShuffleCardsAction);
        gameMenu.add(shuffleCardsMenuItem);
        gameMenu.add(gameMenuSeparator);

        undoMoveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        undoMoveMenuItem.setText("Undo Move");
        undoMoveMenuItem.setEnabled(false);
        undoMoveMenuItem.addActionListener(this::handleUndoMoveAction);
        gameMenu.add(undoMoveMenuItem);

        flipCardMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, 0));
        flipCardMenuItem.setText("Flip Card");
        flipCardMenuItem.addActionListener(this::handleFlipCardAction);
        gameMenu.add(flipCardMenuItem);

        changeCardBackMenuItem.setText("Change Card Back");
        changeCardBackMenuItem.addActionListener(this::handleChangeCardBackAction);
        gameMenu.add(changeCardBackMenuItem);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(this::handleExitAction);
        gameMenu.add(exitMenuItem);

        menuBar.add(gameMenu);

        viewMenu.setText("View");
        showLowerCardMenuItem.setSelected(true);
        showLowerCardMenuItem.setText("Lower Card");
        showLowerCardMenuItem.addActionListener(this::handleShowLowerCardAction);
        viewMenu.add(showLowerCardMenuItem);
        menuBar.add(viewMenu);

        setJMenuBar(menuBar);
    }

    private void addMainWindowComponents() {
        getContentPane().add(topCardLabel, new AbsoluteConstraints(260, 410, 120, 170));
        getContentPane().add(lowerCardLabel, new AbsoluteConstraints(240, 410, 120, 170));
        getContentPane().add(cardBackLabel, new AbsoluteConstraints(400, 410, -1, 170));
        getContentPane().add(descendingSpace2Label, new AbsoluteConstraints(260, TOP_SPACE_Y, 120, 170));
        getContentPane().add(descendingSpace3Label, new AbsoluteConstraints(400, TOP_SPACE_Y, 120, 170));
        getContentPane().add(descendingSpace4Label, new AbsoluteConstraints(540, TOP_SPACE_Y, 120, 170));
        getContentPane().add(ascendingSpace1Label, new AbsoluteConstraints(120, BOTTOM_SPACE_Y, 120, 170));
        getContentPane().add(ascendingSpace2Label, new AbsoluteConstraints(260, BOTTOM_SPACE_Y, 120, 170));
        getContentPane().add(ascendingSpace3Label, new AbsoluteConstraints(400, BOTTOM_SPACE_Y, 120, 170));
        getContentPane().add(ascendingSpace4Label, new AbsoluteConstraints(540, BOTTOM_SPACE_Y, 120, 170));
        getContentPane().add(descendingSpace1Label, new AbsoluteConstraints(120, TOP_SPACE_Y, 120, 170));
        getContentPane().add(deckCountTitleLabel, new AbsoluteConstraints(10, 560, 150, 30));
        getContentPane().add(indexLabel, new AbsoluteConstraints(540, 560, 250, 30));
        getContentPane().add(remainingCardsLabel, new AbsoluteConstraints(140, 560, 50, 30));

        backgroundLabel.setIcon(loadImageIcon("/images/background.png"));
        getContentPane().add(backgroundLabel, new AbsoluteConstraints(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT));
    }

    private JLabel createCardBackSelectorLabel(String imagePath) {
        JLabel label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setIcon(loadImageIcon(imagePath));
        label.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        label.setOpaque(true);
        return label;
    }

    private JLabel createPlayableCardLabel() {
        JLabel label = createBaseCardLabel();
        label.setOpaque(true);
        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                handleTopCardClick(event);
            }

            public void mousePressed(MouseEvent event) {
                handleTopCardPress(event);
            }

            public void mouseReleased(MouseEvent event) {
                handleTopCardRelease(event);
            }
        });
        label.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent event) {
                handleTopCardDrag(event);
            }
        });
        return label;
    }

    private JLabel createBaseCardLabel() {
        JLabel label = new JLabel();
        label.setBackground(new Color(254, 254, 254));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        return label;
    }

    private JLabel createCardBackLabel() {
        JLabel label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setIcon(loadImageIcon("/images/red.png"));
        label.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        label.setOpaque(true);
        label.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                handleCardBackPress(event);
            }

            public void mouseReleased(MouseEvent event) {
                handleCardBackRelease(event);
            }
        });
        return label;
    }

    private JLabel createSpaceLabel(String imagePath) {
        JLabel label = new JLabel();
        label.setBackground(new Color(2, 102, 25));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setIcon(loadImageIcon(imagePath));
        label.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        label.setOpaque(true);
        return label;
    }

    private JLabel createStatusLabel(String text, int horizontalAlignment) {
        JLabel label = new JLabel();
        label.setFont(new Font("DejaVu Sans", Font.BOLD, 13));
        label.setForeground(new Color(254, 254, 254));
        label.setHorizontalAlignment(horizontalAlignment);
        label.setText(text);
        return label;
    }

    private void handleCardBackPress(MouseEvent event) {
    }

    private void handleTopCardPress(MouseEvent event) {
        dragReferenceX = event.getX();
        dragReferenceY = event.getY();
    }

    private void handleTopCardDrag(MouseEvent event) {
        lowerCardLabel.setVisible(true);
        labelX = topCardLabel.getX();
        labelY = topCardLabel.getY();
        dragX = event.getX();
        dragY = event.getY();

        Card currentCard = game.getCurrentCard();
        if (currentCard != null) {
            topCardLabel.setBounds(
                labelX - (dragReferenceX - dragX),
                labelY - (dragReferenceY - dragY),
                CARD_WIDTH,
                CARD_HEIGHT
            );
        }
    }

    private void handleTopCardRelease(MouseEvent event) {
        if (event.getClickCount() != 2) {
            Point mousePosition = getMousePosition();
            int quadrant = getQuadrantAt(mousePosition);
            if (quadrant != Quadrant.INVALID_SPACE) {
                game.playCardInSpace(quadrant);
            }
        } else {
            Card currentCard = game.getCurrentCard();
            int playableSpace = game.getPlayableSpace(currentCard);
            System.out.println("Double click: playable space: " + playableSpace);
            if (playableSpace != INVALID_PLAY) {
                game.playCardInSpace(playableSpace);
            }
        }

        refreshView();
        if (game.isDeckEmpty()) {
            int answer = DialogMessages.confirmYesNo(this, "You won! Do you want to play again?");
            if (answer == DialogMessages.YES) {
                startNewGame();
            }
        }
        resetTopCardPosition();
    }

    private void handleShuffleCardsAction(ActionEvent event) {
        int answer = DialogMessages.confirmYesNo(this, "Start a new game?");
        if (answer == DialogMessages.YES) {
            startNewGame();
        }
    }

    private void handleUndoMoveAction(ActionEvent event) {
        refreshView();
    }

    private void handleExitAction(ActionEvent event) {
        System.exit(0);
    }

    private void handleShowLowerCardAction(ActionEvent event) {
        if (!showLowerCardMenuItem.isSelected()) {
            lowerCardLabel.setBounds(CARD_STACK_X, CARD_STACK_Y, CARD_WIDTH, CARD_HEIGHT);
        } else {
            lowerCardLabel.setBounds(CARD_STACK_OFFSET_X, CARD_STACK_Y, CARD_WIDTH, CARD_HEIGHT);
        }
    }

    private void handleTopCardClick(MouseEvent event) {
    }

    private void handleFlipCardAction(ActionEvent event) {
        handleCardBackRelease(null);
    }

    private void handleCardBackRelease(MouseEvent event) {
        resetTopCardPosition();
        game.flipCard();
        refreshView();
    }

    private void handleChangeCardBackAction(ActionEvent event) {
        cardBackSelectorFrame.setBounds(0, 0, SPACE_SELECTOR_WIDTH, SPACE_SELECTOR_HEIGHT);
        cardBackSelectorFrame.setLocationRelativeTo(null);
        cardBackSelectorFrame.setVisible(true);
    }

    private void handleAruCardBackSelection(MouseEvent event) {
        updateCardBackImage(ImagePaths.ARU);
    }

    private void handleAngelesCardBackSelection(MouseEvent event) {
        updateCardBackImage(ImagePaths.ANGELES);
    }

    private void handleFabiCardBackSelection(MouseEvent event) {
        updateCardBackImage(ImagePaths.FABI);
    }

    private void handlePatoCardBackSelection(MouseEvent event) {
        updateCardBackImage(ImagePaths.PATO);
    }

    private void handleGildaCardBackSelection(MouseEvent event) {
        updateCardBackImage(ImagePaths.GILDA);
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException exception) {
            LOGGER.log(Level.SEVERE, null, exception);
        } catch (InstantiationException exception) {
            LOGGER.log(Level.SEVERE, null, exception);
        } catch (IllegalAccessException exception) {
            LOGGER.log(Level.SEVERE, null, exception);
        } catch (UnsupportedLookAndFeelException exception) {
            LOGGER.log(Level.SEVERE, null, exception);
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Application().setVisible(true);
            }
        });
    }

    private void refreshView() {
        updateCurrentCard();
        updateLowerCard();
        updateCardBackLabel();
        updateSpaces();
        remainingCardsLabel.setText(Integer.toString(game.getRemainingCards()));
        indexLabel.setText(Integer.toString(game.getVisibleIndex()));
    }

    private int getQuadrantAt(Point point) {
        if (point == null) {
            return Quadrant.INVALID_SPACE;
        }

        if (PixelUtils.isPointInside(point, descendingSpace1Label)) {
            return Quadrant.SPACE_1;
        }
        if (PixelUtils.isPointInside(point, descendingSpace2Label)) {
            return Quadrant.SPACE_2;
        }
        if (PixelUtils.isPointInside(point, descendingSpace3Label)) {
            return Quadrant.SPACE_3;
        }
        if (PixelUtils.isPointInside(point, descendingSpace4Label)) {
            return Quadrant.SPACE_4;
        }
        if (PixelUtils.isPointInside(point, ascendingSpace1Label)) {
            return Quadrant.SPACE_5;
        }
        if (PixelUtils.isPointInside(point, ascendingSpace2Label)) {
            return Quadrant.SPACE_6;
        }
        if (PixelUtils.isPointInside(point, ascendingSpace3Label)) {
            return Quadrant.SPACE_7;
        }
        if (PixelUtils.isPointInside(point, ascendingSpace4Label)) {
            return Quadrant.SPACE_8;
        }
        return Quadrant.INVALID_SPACE;
    }

    private void resetTopCardPosition() {
        topCardLabel.setBounds(CARD_STACK_X, CARD_STACK_Y, CARD_WIDTH, CARD_HEIGHT);
    }

    private void startNewGame() {
        game.restart(false);
        refreshView();
    }

    private void updateCardBackImage(String imagePath) {
        ImagePaths.currentCardBack = imagePath;
        UiUtils.setLabelImage(this, cardBackLabel, ImagePaths.currentCardBack);
    }

    private void updateCurrentCard() {
        Card currentCard = game.getCurrentCard();
        updateCardLabel(topCardLabel, currentCard);
    }

    private void updateLowerCard() {
        Card lowerCard = game.getVisibleLowerCard();
        updateCardLabel(lowerCardLabel, lowerCard);
    }

    private void updateCardBackLabel() {
        if (game.getVisibleIndex() == 0) {
            UiUtils.setLabelImage(this, cardBackLabel, ImagePaths.EMPTY);
        } else {
            UiUtils.setLabelImage(this, cardBackLabel, ImagePaths.currentCardBack);
        }
    }

    private void updateSpaces() {
        updateSpaceLabel(descendingSpace1Label, Quadrant.SPACE_1, ImagePaths.EMPTY_SPACE_1);
        updateSpaceLabel(descendingSpace2Label, Quadrant.SPACE_2, ImagePaths.EMPTY_SPACE_2);
        updateSpaceLabel(descendingSpace3Label, Quadrant.SPACE_3, ImagePaths.EMPTY_SPACE_3);
        updateSpaceLabel(descendingSpace4Label, Quadrant.SPACE_4, ImagePaths.EMPTY_SPACE_4);
        updateSpaceLabel(ascendingSpace1Label, Quadrant.SPACE_5, ImagePaths.EMPTY_SPACE_5);
        updateSpaceLabel(ascendingSpace2Label, Quadrant.SPACE_6, ImagePaths.EMPTY_SPACE_6);
        updateSpaceLabel(ascendingSpace3Label, Quadrant.SPACE_7, ImagePaths.EMPTY_SPACE_7);
        updateSpaceLabel(ascendingSpace4Label, Quadrant.SPACE_8, ImagePaths.EMPTY_SPACE_8);
    }

    private void updateSpaceLabel(JLabel label, int space, String emptyImagePath) {
        Card topCard = game.getTopCardAtSpace(space);
        if (topCard == null) {
            UiUtils.setLabelImage(this, label, emptyImagePath);
            return;
        }
        updateCardLabel(label, topCard);
    }

    private void updateCardLabel(JLabel label, Card card) {
        String cardImagePath = getCardImagePath(card);
        UiUtils.setLabelImage(this, label, cardImagePath);
    }

    private String getCardImagePath(Card card) {
        if (card == null) {
            return ImagePaths.EMPTY;
        }
        return ImagePaths.ROOT + card.getSuit() + "/" + card.getNumber() + ImagePaths.EXTENSION;
    }

    private static Logger createLogger() {
        String loggerName = Application.class.getName();
        return Logger.getLogger(loggerName);
    }

    private ImageIcon loadImageIcon(String imagePath) {
        URL imageUrl = getClass().getResource(imagePath);
        return new ImageIcon(imageUrl);
    }
}
