package drawable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class DrawableSegment implements Drawable<DrawablePath> {
    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;

    private final Color color;
    // from http://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
    private final int brushWidth;

    // represents thickness of brush strokes

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
     * Null constructor
     */
    public DrawableSegment() {
        this.x1 = 0;
        this.y1 = 0;
        this.x2 = 0;
        this.y2 = 0;
        this.color = Color.BLACK;
        this.brushWidth = 1;
    }

    @Override
    public JSONObject toJSON() {
        Map m = new LinkedHashMap();
        JSONObject j = new JSONObject();
        // TODO add types
        m.put("x1", this.x1);
        m.put("y1", this.y1);
        m.put("x2", this.x2);
        m.put("y2", this.y2);
        m.put("r", this.color.getRed());
        m.put("g", this.color.getGreen());
        m.put("b", this.color.getBlue());
        m.put("a", this.color.getAlpha());
        m.put("width", this.brushWidth);
        j.putAll(m);
        return j;
    };

    @Override
    public DrawablePath fromJSON(String data) {
        return fromJSON((JSONObject) JSONValue.parse(data));
    };

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DrawableSegment))
            return false;
        // TODO proper equals function, assume true for now.
        return true;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(1.0f));
        g.drawLine(x1, y1, x2, y2);

        // need to deal with this - IMPORTANT! every time we draw on the
        // internal drawing buffer, we
        // have to notify Swing to repaint this component on the screen.
        // JPanel.repaint();
    }

    @Override
    public DrawablePath fromJSON(JSONObject j) {
        // TODO proper deserialize
        return null;
    };

}