/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;

import java.awt.Point;
import javax.swing.JLabel;

/**
 *
 * @author pato
 */
public class PixelUtils {

    private static final int CARD_WIDTH = 120;
    private static final int CARD_HEIGHT = 170;

    public static Point getTopLeftPoint(JLabel label) {
        return new Point(label.getX(), label.getY());
    }

    public static Point getBottomRightPoint(JLabel label) {
        return new Point(label.getX() + CARD_WIDTH, label.getY() + CARD_HEIGHT);
    }

    public static void printCoordinates(JLabel label) {
        Point startPoint = getTopLeftPoint(label);
        Point endPoint = getBottomRightPoint(label);
        System.out.println("---------------------------------");
        System.out.println("x1 = " + startPoint.getX() + " y1 = " + startPoint.getY());
        System.out.println("x2 = " + endPoint.getX() + " y2 = " + endPoint.getY());
        System.out.println("---------------------------------");
    }

    public static boolean isPointInside(Point point, JLabel label) {
        Point startPoint = getTopLeftPoint(label);
        Point endPoint = getBottomRightPoint(label);
        boolean insideX = point.getX() >= startPoint.getX() && point.getX() <= endPoint.getX();
        boolean insideY = point.getY() >= startPoint.getY() && point.getY() <= endPoint.getY();
        return insideX && insideY;
    }
}
