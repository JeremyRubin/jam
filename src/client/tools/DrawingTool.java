package client.tools;

import client.WhiteboardClientModel;
import drawable.Drawable;

/**
 * Interface for a drawing tool.
 * @author Merry
 *
 */
public interface DrawingTool {
    /**
     * See Drawable documentation.
     */
    public Drawable draw(int x1, int y1, int x2, int y2);

    /**
     * Returns an instance of the DrawingTool.
     * 
     * @param model
     * @return
     */
    public DrawingTool createFromModel(WhiteboardClientModel model);
}
