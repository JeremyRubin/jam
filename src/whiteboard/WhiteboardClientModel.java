package whiteboard;

import global.Constants;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import message.StrokeMessage;
import client.tools.DrawingTool;
import client.tools.Pen;
import drawable.Drawable;

public class WhiteboardClientModel {
    // Drawables that have been acknowledged by the server
    private Map<Integer, Drawable> syncedState = new HashMap<Integer, Drawable>();

    // Drawables that have not been acknowledged by the server yet
    private Map<Integer, Drawable> localState = new HashMap<Integer, Drawable>();

    private int nextLocalIndex = 0;

    public Color color = Color.BLACK;
    public int brushWidth = 5;
    public DrawingTool tool;

    public WhiteboardClientModel() {
        setTool(Pen.class);
    }

    /**
     * After the user draws onto the GUI, handleDrawable adds the Drawable to
     * the localState, then sends a StrokeMessage to the WhiteboardServerModel.
     * 
     * @param d
     *            Drawable that user drew
     */
    public void handleDrawable(int id, int userSeqId, Drawable d, String username, String whiteboardID) {
        StrokeMessage s = new StrokeMessage(id, userSeqId, d, username, whiteboardID);
        localState.put(localState.size(), d); // what should the key be?
    }

    public Image getImage() {
        BufferedImage image = new BufferedImage(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, image.getWidth(), image.getHeight());

        // draw local stuff last (on top)
        List<Drawable> drawables = new ArrayList<Drawable>();
        drawables.addAll(getSortedListFromMap(syncedState));
        drawables.addAll(getSortedListFromMap(localState));

        for (Drawable d : drawables) {
            d.draw(g);
        }

        return image;
    }

    private <T> List<T> getSortedListFromMap(Map<Integer, T> map) {
        List<T> list = new ArrayList<T>();
        List<Integer> keys = new ArrayList<Integer>(map.keySet());
        Collections.sort(keys);
        for (int index : keys) {
            list.add(map.get(index));
        }
        return list;
    }

    /**
     * The global state has assigned a certain index for the board, so add it to
     * syncedState and remove from localState
     * 
     * @param syncedTo
     * @param local
     * @param drawable
     */
    public void addDrawableAt(int syncedTo, int local, Drawable drawable) {

    }

    public void draw(Drawable drawable) {
        localState.put(nextLocalIndex, drawable);
        nextLocalIndex++;
    }

    public void setTool(Class<? extends DrawingTool> toolClass) {
        try {
            tool = toolClass.newInstance().createFromModel(this);
        } catch (InstantiationException e) {
            // this should never happen if there are no bugs, hence
            // System.exit(1)
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalAccessException e) {
            // this should never happen if there are no bugs, hence
            // System.exit(1)
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void setColor(Color color) {
        this.color = color;
        setTool(this.tool.getClass());
    }
}
