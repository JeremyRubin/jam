package client;

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

import message.ToServerStrokeMessage;
import drawable.Drawable;

/**
 * Represents a specific whiteboard's client.
 */
public class Whiteboard {
    /**
     * the parent WhiteboardClientModel
     */
    public final WhiteboardClientModel model;

    /**
     * the ID of this whiteboard
     */
    public final String whiteboardID;

    // Drawables that have been acknowledged by the server
    private Map<Integer, Drawable> serverDrawables = new HashMap<Integer, Drawable>();

    /**
     * Creates a new local mirror of a remote whiteboard with id "whiteboardID", with the given
     * WhiteboardClientModel as a parent.
     * 
     * @param whiteboardID
     *            a String whiteboard ID
     * @param model
     *            the parent model
     */
    public Whiteboard(String whiteboardID, WhiteboardClientModel model) {
        this.whiteboardID = whiteboardID;
        this.model = model;
    }

    /**
     * Renders the whiteboard and returns an Image.
     * 
     * @return an Image
     */
    public Image getImage() {
        BufferedImage image = new BufferedImage(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, image.getWidth(), image.getHeight());

        for (Drawable d : getSortedListFromMap(serverDrawables)) {
            d.draw(g);
        }

        return image;
    }

    /**
     * @param map
     *            a Map<Integer, T>
     * @return a List<T> with each of the values of the map, sorted by their keys
     */
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
     * Adds a drawable to the whiteboard.
     * 
     * (Sends it to the server.)
     * 
     * @param drawable
     *            a Drawable
     */
    public void draw(Drawable drawable) {
        model.sendMessage(new ToServerStrokeMessage(drawable, whiteboardID));
    }

    /**
     * Adds a Drawable to the collection of Drawables to render, in the correct position.
     * 
     * @param index
     *            the index
     * @param drawable
     *            a Drawable
     */
    public void addDrawableFromServer(int index, Drawable drawable) {
        serverDrawables.put(index, drawable);
    }
}
