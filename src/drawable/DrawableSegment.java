package drawable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Implementation of Drawable that represents a drawn line segment from (x1, y1)
 * to (x2, y2).
 * 
 */
public class DrawableSegment implements Drawable<DrawableSegment> {
    // empty DrawableSegment, accessed in various parts of the code.
    public static final DrawableSegment STATIC = new DrawableSegment();

    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;

    // from http://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
    private final Color color;

    // represents thickness of brush strokes
    private final int brushWidth;

    /**
     * Constructor for DrawableSegment.
     * 
     * @param x1
     *            x coordinate of starting point
     * @param y1
     *            y coordinate of starting point
     * @param x2
     *            x coordinate of ending point
     * @param y2
     *            y coordinate of ending point
     * @param color
     *            color of line segment
     * @param brushWidth
     *            width of line segment
     */
    public DrawableSegment(int x1, int y1, int x2, int y2, Color color, int brushWidth) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
        this.brushWidth = brushWidth;
    };

    /**
     * Constructor for DrawableSegment that defaults color to black and width to
     * 1.
     * 
     * @param x1
     *            x coordinate of starting point
     * @param y1
     *            y coordinate of starting point
     * @param x2
     *            x coordinate of ending point
     * @param y2
     *            y coordinate of ending point
     */
    public DrawableSegment(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = Color.BLACK;
        this.brushWidth = 1;
    };

    /**
     * An empty constructor, only used as a hack to allow "static" access to
     * non-static methods
     * 
     * see {@link #STATIC}
     */
    private DrawableSegment() {
        this.x1 = 0;
        this.y1 = 0;
        this.x2 = 0;
        this.y2 = 0;
        this.color = Color.BLACK;
        this.brushWidth = 1;
    }

    /**
     * Asserts whether an object is equivalent to this DrawableSegment.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DrawableSegment) {
            DrawableSegment d = (DrawableSegment) obj;
            if (this.x1 == d.x1 && this.x2 == d.x2 && this.y1 == d.y1 && this.y2 == d.y2 && this.color.equals(d.color)
                    && this.brushWidth == d.brushWidth) {
                return true;
            }
        }
        return false;
    }

    /**
     * See Drawable Interface for specs.
     */
    @Override
    public void draw(Graphics2D g) {
        Color oldColor = g.getColor();
        Stroke oldStroke = g.getStroke();

        g.setColor(this.color);
        g.setStroke(new BasicStroke(this.brushWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(this.x1, this.y1, this.x2, this.y2);

        // reset color and stroke to g's original
        g.setColor(oldColor);
        g.setStroke(oldStroke);
    }

    /**
     * See JSONable Interface for specs.
     */
    @Override
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        j.put("x1", this.x1);
        j.put("y1", this.y1);
        j.put("x2", this.x2);
        j.put("y2", this.y2);
        j.put("r", this.color.getRed());
        j.put("g", this.color.getGreen());
        j.put("b", this.color.getBlue());
        j.put("a", this.color.getAlpha());
        j.put("width", this.brushWidth);
        j.put(Drawables.type, Drawables.drawableSegment);
        return j;
    };

    /**
     * See JSONable Interface for specs.
     */
    @Override
    public DrawableSegment fromJSON(String data) {
        return fromJSON((JSONObject) JSONValue.parse(data));
    };

    /**
     * See JSONable Interface for specs.
     */
    @Override
    public DrawableSegment fromJSON(JSONObject j) {

        int x1 = ((Long) j.get("x1")).intValue();
        int y1 = ((Long) j.get("y1")).intValue();
        int x2 = ((Long) j.get("x2")).intValue();
        int y2 = ((Long) j.get("y2")).intValue();

        int r = ((Long) j.get("r")).intValue();
        int g = ((Long) j.get("g")).intValue();
        int b = ((Long) j.get("b")).intValue();
        int a = ((Long) j.get("a")).intValue();

        int w = ((Long) j.get("width")).intValue();

        return new DrawableSegment(x1, y1, x2, y2, new Color(r, g, b, a), w);
    };

}
