package client.tools;

import java.awt.Color;

import client.WhiteboardClientModel;
import drawable.Drawable;
import drawable.DrawableSegment;

public class Eraser implements DrawingTool {
    @Override
    public Drawable draw(int x1, int y1, int x2, int y2) {
        return new DrawableSegment(x1, y1, x2, y2, Color.WHITE, 20);
    }

    @Override
    public DrawingTool createFromModel(WhiteboardClientModel model) {
        return new Eraser();
    }
}
