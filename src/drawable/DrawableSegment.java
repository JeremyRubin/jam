package drawable;

import java.lang.Long;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class DrawableSegment implements Drawable<DrawableSegment> {
    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;

    // from http://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
    private final Color color;

    // represents thickness of brush strokes
    private final int brushWidth;

    public DrawableSegment(int x1, int y1, int x2, int y2, Color color, int brushWidth) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
        this.brushWidth = brushWidth;
    };

    public DrawableSegment(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = Color.BLACK;
        this.brushWidth = 1;
    };

    /**
     * this is only used as a hack to allow "static" access to non-static
     * methods
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

    public static final DrawableSegment STATIC = new DrawableSegment();

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

    @Override
    public void draw(Graphics2D g) {
        Color oldColor = g.getColor();
        Stroke oldStroke = g.getStroke();
        g.setColor(this.color);
        g.setStroke(new BasicStroke(this.brushWidth));
        g.drawLine(this.x1, this.y1, this.x2, this.y2);
        g.setColor(oldColor);
        g.setStroke(oldStroke);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        // TODO add types ??

        j.put("x1", this.x1);
        j.put("y1", this.y1);
        j.put("x2", this.x2);
        j.put("y2", this.y2);
        j.put("r", this.color.getRed());
        j.put("g", this.color.getGreen());
        j.put("b", this.color.getBlue());
        j.put("a", this.color.getAlpha());
        j.put("width", this.brushWidth);
        return j;
    };

    @Override
    public DrawableSegment fromJSON(String data) {
        return fromJSON((JSONObject) JSONValue.parse(data));
    };

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
