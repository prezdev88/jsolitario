/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrador
 */
public class DialogMessages {

    public static final int YES = JOptionPane.YES_OPTION;
    public static final int NO = JOptionPane.NO_OPTION;
    public static final int CANCEL = JOptionPane.CANCEL_OPTION;

    public static void showWarning(String title, String message) {
        JOptionPane.showMessageDialog(
            null,
            "Warning: " + message,
            title,
            JOptionPane.WARNING_MESSAGE
        );
    }

    public static void showWarning(String message) {
        JOptionPane.showMessageDialog(
            null,
            "Warning: " + message,
            "Warning",
            JOptionPane.WARNING_MESSAGE
        );
    }

    public static int confirmYesNo(JFrame frame, String message) {
        return JOptionPane.showConfirmDialog(frame, message, "Question", JOptionPane.YES_NO_OPTION);
    }

    public static int confirmYesNoCancel(String message) {
        return JOptionPane.showConfirmDialog(null, message, "Question", JOptionPane.YES_NO_CANCEL_OPTION);
    }

    public static void showInformation(String message) {
        JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showInformation(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showError(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static String showInput(String title, String message) {
        return JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
    }
}
