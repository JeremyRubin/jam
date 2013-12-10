package client.tools;

import java.awt.Color;

import whiteboard.WhiteboardClientModel;
import drawable.Drawable;
import drawable.DrawableSegment;

public class Pen implements DrawingTool {
    private final Color color;
    private final int thickness;

    public Pen() {
        // should never be used except to access the createFromModel method
        this.color = null;
        this.thickness = -1;
    }

    public Pen(Color color, int thickness) {
        this.color = color;
        this.thickness = thickness;
    }

    @Override
    public Drawable draw(int x1, int y1, int x2, int y2) {
        return new DrawableSegment(x1, y1, x2, y2, color, thickness);
    }

    @Override
    public DrawingTool createFromModel(WhiteboardClientModel model) {
        return new Pen(model.color, model.brushWidth);
    }
}
