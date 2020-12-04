package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Utility class creating a window and showing a succession of rectangles, defined from an external method.
 *
 * <p>
 * This is a utility class which main-function {@link #start()} calls into a method <code>generate</code> defined in a user object,
 * then opens a minimal window, then successively highlights
 * rectangles, each being defined by a single (x,y)-coordinate from the <code>generate</code> method.
 * </p>
 * <p>
 * The coordinate system has the usual properties of coordinate systems in computer graphics,
 * i.e. x running from left to right, y from top to bottom, with the top left field being (0,0), the bottom right field (width-1,height-1).
 * Width (w) and height (h) of the system in rectangle units are user defined,
 * as is the screen window width (sw) and height (sh).
 * No border is drawn. It follows that a single rectangle has the screen dimensions (sw/w, sh/h).
 * </p>
 * <p>
 * To use this component it must be configured first.
 * Mandatory configuration consists only of creation ({@link #Gui(Object, int, int)}).
 * Optionally,
 * <ul>
 *     <li>set a window size ({@link #resizeWindow(int, int)}),</li>
 *     <li>set an update period ({@link #setWaitMs(int)}),</li>
 *     <li>configure the behaviour between execution of individual drawing commands ({@link #setDiscardOldDrawings()}).</li>
 * </ul>
 * <p>
 * Rectangles can be added via the different overloads of {@link #rectangleAt(int, int, Color)}.
 * This is only possible from the <code>generate</code> method of the target object, which is automatically called during {@link #start()}.
 * </p>
 * <p>
 * For more usage information, consult the documentation of the public interface.
 * </p>
 *
 * @author Gysar Flegel {@literal <gysar.flegel@fh-bielefeld.de>}
 * @author Anestis Lalidis Mateo {@literal <anestis-pere.lalidis_mateo@fh-bielefeld.de>}
 */
public class Gui extends JPanel implements ActionListener {

    /**
     * used to allow resize only if start has not been called
     */
    private boolean allowResize = true;

    /**
     * When set, rectangleAt-commands are processed.
     */
    private boolean allowRectangleCommands = false;

    /**
     * only repaint if certain condition occurs
     */
    private boolean doRepaint = false;

    /**
     * keep old drawings or discard them?
     */
    private boolean keepDrawings = true;

    /**
     * used to store the counter outside of a function
     */
    private int countGlobal = 0;

    Timer timer = null;

    /**
     * Window width in pixels
     */
    private int maxWidth = 500;
    /**
     * Window height in pixels
     */
    private int maxHeight = 500;
    /**
     * The number of rectangles to divide the window width by.
     */
    private final int width;
    /**
     * The number of rectangles to divide the window height by.
     */
    private final int height;

    /**
     * The object which generate(GUI) method is invoked.
     */
    private final Object mtc;


    /**
     * List of rows we wanna draw
     */
    private final List<Template> templates;

    /**
     * The time to wait between each drawing step.
     */
    private int waitMs = 2000;

    /**
     * The time before the initial drawing step.
     */
    private int initialWaitMs = 5000;

    /**
     * The minimum amount of rectangle units.
     */
    public static final int DIM_LIMIT_LOW = 5;

    /**
     * The maximum amount of rectangle units.
     */
    public static final int DIM_LIMIT_HIGH = 100;

    /**
     * Generates a gui object.
     *
     * <p>
     * The passed object must define a public method generate(GUI). See also {@link #start()}.
     * Width and height must be in range [{@link #DIM_LIMIT_LOW}, {@link #DIM_LIMIT_HIGH}] and define
     * the window dimensions in rectangle units (see also {@link #resizeWindow}).
     * </p>
     *
     * @param mtc    the object with a generate(GUI) method, which can
     *               be used by this gui.
     * @param width  the number of rectangles to divide the window width by.
     * @param height the number of rectangles to divide the window height by.
     */
    public Gui(Object mtc, int width, int height) {

        if (width > DIM_LIMIT_HIGH || height > DIM_LIMIT_HIGH || width < DIM_LIMIT_LOW || height < DIM_LIMIT_LOW) {
            System.out.println("width and height must be >= " + DIM_LIMIT_LOW + " and <= " + DIM_LIMIT_HIGH);
            System.exit(0);
        }
        if (width > maxWidth || height > maxHeight) {
            System.out.println("width and height must be <= maxWidth/maxHeight");
            System.exit(0);
        }

        templates = new LinkedList<>();

        this.height = height;
        this.width = width;
        this.mtc = mtc;
    }

    /**
     * Remove old drawings on each step of execution.
     * <p>
     * Set this, if prior to each execution of a rectangleAt-command, the already painted rectangles should be removed.
     * The default behaviour is to keep old drawings.
     * </p>
     */
    public void setDiscardOldDrawings() {
        keepDrawings = false;
    }

    /**
     * Keep old drawings on each step of execution.
     *
     * <p>
     * Set this, if already painted rectangles should be kept (repainted) with each execution of a rectangleAt-call.
     * This is also the default behaviour.
     * </p>
     */
    public void setKeepOldDrawings() {
        keepDrawings = true;
    }

    /**
     * Sets the period of the gui (time between individual rectangle-draw-command executions).
     *
     * @param waitMs The time in milliseconds to wait between execution of successive drawing commands.
     */
    public void setWaitMs(int waitMs) {

        final int LOWER = 10, UPPER = 2000;
        if (waitMs < LOWER || waitMs > UPPER) {
            System.out.println("waitMs must be >= " + LOWER + " and <= " + UPPER);
            System.exit(0);
        }
        this.waitMs = waitMs;
    }

    /**
     * Sets the time before the initial drawing step.
     *
     * @param initialWaitMs The time in milliseconds to wait before starting the drawing loop.
     */
    public void setInitialWaitMs(int initialWaitMs) {

        final int LOWER = 0, UPPER = 5000;
        if (initialWaitMs < LOWER || initialWaitMs > UPPER) {
            System.out.println("initialWaitMs must be >= " + LOWER + " and <= " + UPPER);
            System.exit(0);
        }
        this.initialWaitMs = initialWaitMs;
    }

    /**
     * Starts the gui.
     *
     * <p>
     * This method checks for the existence of a method with the extended signature
     * <code>
     * public void generate(GUI)
     * </code>
     * in the target object {@link #mtc}.
     * If that method exists, it is evaluated with the current instance. Exceptions from that
     * call are swallowed, and an error is printed.  During the execution of generate, calls to
     * the various overloads of {@link #rectangleAt} are allowed. When generate returns successfully,
     * the gui is loaded and the configured rectangle-draw commands are executed one by one with a
     * period of {@link #waitMs}. To access the configured width and height in rectangle units from
     * generate, {@link #getWidth()} and {@link #getHeight()} may be used.
     * </p>
     */
    public void start() {
        // From here on, prevent resizing.
        allowResize = false;

        Method generate = null;

        try {
            var cArg = new Class[1];
            cArg[0] = this.getClass();
            generate = mtc.getClass().getDeclaredMethod("generate", cArg);
        } catch (NoSuchMethodException e) {
            System.out.println("Missing method \"void generate(Gui gui)\"");
            System.exit(0);
        }

        try {
            // during generate, drawing commands may be added.
            allowRectangleCommands = true;
            generate.invoke(mtc, this);
            allowRectangleCommands = false;

        } catch (IllegalAccessException e) {
            System.out.println("The generate(Gui) is not public.");
            System.exit(0);
        } catch (IllegalArgumentException e) {
            System.out.println("The generate(Gui) didn't get the right types of arguments.");
            System.exit(0);
        } catch (InvocationTargetException e) {
            System.out.println(
                    "Something went wrong in generate(Gui). Please make sure, it doesn't throw any exceptions");
            System.exit(0);
        }

        var mainPanel = this;
        var frame = new JFrame(mtc.getClass().getSimpleName() + " execution gui");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        timer = new Timer(waitMs, this);
        timer.setInitialDelay(initialWaitMs);
        timer.start();
    }

    /**
     * Add a rectangle to be drawn at the specified coordinates.
     * <p>
     * This is only processed when called from the <code>generate</code> method of the user object passed during construction.
     *
     * @param i     the x coordinate of the rectangle
     * @param j     the y coordinate of the rectangle
     * @param color The awt.Color to paint the rectangle in
     */
    public void rectangleAt(int i, int j, Color color) {
        if (allowRectangleCommands) {
            this.templates.add(new Template(new Drawing(i, j, color)));
        }
    }

    /**
     * Add rectangles to be drawn at the specified coordinates.
     * <p>
     * This defines a single draw-command, i.e. all passed coordinates are drawn at once.
     * Only processed during <code>generate</code> of the user object passed during construction.
     * </p>
     *
     * @param is     X coordinates
     * @param js     Y coordinates
     * @param colors The awt.Colors to paint the rectangles in
     */
    public void rectangleAt(int[] is, int[] js, Color[] colors) {
        var points = new Point[is.length];
        for (var index = 0; index < is.length; index++) points[index] = new Point(is[index], js[index]);
        rectangleAt(points, colors);
    }

    /**
     * Add rectangles to be drawn at the specified coordinates in the same color.
     * <p>
     * This defines a single draw-command, i.e. all passed coordinates are drawn at once.
     * Only processed during <code>generate</code> of the user object passed during construction.
     * </p>
     *
     * @param is    is the list of x coordinates.
     * @param js    is the list of y coordinates.
     * @param color is the color for every coordinate.
     */
    public void rectangleAt(int[] is, int[] js, Color color) {
        Color[] colors = new Color[is.length];
        Arrays.fill(colors, color);
        rectangleAt(is, js, colors);
    }

    /**
     * Add rectangles to be drawn at the specified coordinates.
     * <p>
     * This defines a single draw-command, i.e. all passed coordinates are drawn at once.
     * Only processed during <code>generate</code> of the user object passed during construction.
     * </p>
     *
     * @param points The damn coordinates
     * @param colors The awt.Colors to paint the rectangles in
     */
    public void rectangleAt(Point[] points, Color[] colors) {
        if (allowRectangleCommands) {
            this.templates.add(new Template(Arrays.asList(points), Arrays.asList(colors)));
        }
    }

    /**
     * Returns the width in rectangle units.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height in rectangle units.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Paints the objects, defined by the generate(GUI) method.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (keepDrawings) {
            paintAllObjects();
        } else {
            paintSingleObject();
        }
    }

    private void paintAllObjects() {
        var count = 0;
        while (count < countGlobal) {

            var template = templates.get(count);
            drawTemplate(template);

            count++;
        }

        if (countGlobal < templates.size()) {
            if (doRepaint) {
                countGlobal++;
                doRepaint = false;
            }
        }
    }

    private void paintSingleObject() {
        this.getGraphics().clearRect(0, 0, (int) this.getBounds().getWidth(), (int) this.getBounds().getHeight());
        if (countGlobal > 0) {
            var current = countGlobal - 1;
            var template = templates.get(current);
            drawTemplate(template);
        }
        if (countGlobal < templates.size()) {
            if (doRepaint) {
                countGlobal++;
                doRepaint = false;
            }
        }
    }

    private void drawTemplate(Template template) {
        for (var drawing : template.getDrawings()) {

            var point = drawing.coordinate;
            var color = drawing.color;

            if (!(point.x < 0 || point.x >= width) && !(point.y < 0 || point.y >= height)) {
                paintRectangle(point.x, point.y, color);
            } else {
                System.out.println("Dimensions i=" + point.x + " and j=" + point.y + " are not in bounds.");
                System.exit(0);
            }
        }
    }

    /**
     * Paints a rectangle
     *
     * @param x     the x coordinate of the rectangle
     * @param y     the y coordinate of the rectangle
     * @param color The awt.Color to paint the rectangle in
     */
    private void paintRectangle(int x, int y, Color color) {
        var g = this.getGraphics();
        g.setColor(color);
        g.fillRect(x * this.getBounds().width / width, y * this.getBounds().height / height,
                (this.getBounds().width + width) / width, (this.getBounds().height + height) / height);
    }

    /**
     * Returns the dimensions of the window
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(maxWidth, maxHeight);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {

        if (ev.getSource() == timer) {
            doRepaint = true;
            update(this.getGraphics());
        }
    }

    /**
     * Change the size of the window.
     * <p>
     * These are screen dimensions; the size in rectangle units can only be set during construction.
     *
     * @param newMaxHeight new window height
     * @param newMaxWidth  new window width
     */
    public void resizeWindow(int newMaxHeight, int newMaxWidth) {

        final int LOWER = 50, UPPER = 1000;

        if (newMaxHeight < LOWER || newMaxWidth < LOWER || newMaxHeight > UPPER || newMaxWidth > UPPER) {
            System.out.println("newMaxHeight and newMaxWidth must be in the interval (" + LOWER + "," + UPPER + ")");
            System.exit(0);
        }
        if (allowResize) {
            maxHeight = newMaxHeight;
            maxWidth = newMaxWidth;
            getPreferredSize();
        } else {
            System.out.println("window should only be resized before calling gui.start()");
            System.exit(0);
        }
    }
}
