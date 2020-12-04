package gui;

import java.awt.*;

/**
 * Data oriented pojo class which stores a drawing, its color and coordinate.
 */
class Drawing {

    Point coordinate;
    Color color;

    /**
     * Creates a drawing on a specifc coordinate with a certain color.
     *
     * @param x     The x coordinate
     * @param y     The y coordinate
     * @param color The color
     */
    Drawing(int x, int y, Color color) {
        this.coordinate = new Point(x, y);
        this.color = color;
    }

    /**
     * Creates a drawing on a specifc coordinate with a certain color.
     *
     * @param coordinate The drawings coordinate
     * @param color      The color
     */
    Drawing(Point coordinate, Color color) {
        this.coordinate = coordinate;
        this.color = color;
    }
}
