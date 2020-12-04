package gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores and handles a List of Drawings
 */
class Template {

    // List of Points and their Colors
    private List<Drawing> drawings;

    Template(Drawing drawing) {
        drawings = new ArrayList<>();
        drawings.add(drawing);
    }

    /**
     * Adds the Points and colors to the Drawing List
     *
     * @param points To be added
     * @param colors To be added
     */
    Template(List<Point> points, List<Color> colors) {

        drawings = new ArrayList<>();
        for (var index = 0; index < points.size(); index++) {

            var point = points.get(index);
            var color = colors.get(index);

            drawings.add(new Drawing(point, color));
        }
    }

    List<Drawing> getDrawings() {
        return drawings;
    }
}
