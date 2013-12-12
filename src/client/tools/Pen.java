package client.tools;

import java.awt.Color;

import client.WhiteboardClientModel;
import drawable.Drawable;
import drawable.DrawableSegment;

/**
 * Represents a pen of a certain color and brush thickness to draw with.
 * 
 */
public class Pen implements DrawingTool {
    private final Color color;
    private final int thickness;

    /**
     * Empty constructor. Should never be used except to access the
     * createFromModel method.
     */
    public Pen() {
        this.color = null;
        this.thickness = -1;
    }

    /**
     * Constructor for pen.
     * 
     * @param color
     * @param thickness
     */
    public Pen(Color color, int thickness) {
        this.color = color;
        this.thickness = thickness;
    }

    @Override
    public Drawable draw(int x1, int y1, int x2, int y2) {
        return new DrawableSegment(x1, y1, x2, y2, color, thickness);
    }

    /**
     * Calls pen constructor.
     */
    @Override
    public DrawingTool createFromModel(WhiteboardClientModel model) {
        return new Pen(model.getColor(), model.getBrushWidth());
    }
}
