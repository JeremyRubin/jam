package client.tools;

import whiteboard.WhiteboardClientModel;
import drawable.Drawable;

public interface DrawingTool {
    public Drawable draw(int x1, int y1, int x2, int y2);

    public DrawingTool createFromModel(WhiteboardClientModel model);
}
