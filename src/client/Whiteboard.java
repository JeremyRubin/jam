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

public class Whiteboard {
    public final WhiteboardClientModel model;

    public final String whiteboardID;

    // Drawables that have been acknowledged by the server
    private Map<Integer, Drawable> serverDrawables = new HashMap<Integer, Drawable>();

    // Drawables that have not been acknowledged by the server yet
    private Map<Integer, Drawable> localState = new HashMap<Integer, Drawable>();

    private int nextLocalIndex = 0;

    public Whiteboard(String whiteboardID, WhiteboardClientModel model) {
        this.whiteboardID = whiteboardID;
        this.model = model;
    }

    public Image getImage() {
        BufferedImage image = new BufferedImage(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, image.getWidth(), image.getHeight());

        // draw local stuff last (on top)
        List<Drawable> drawables = new ArrayList<Drawable>();
        drawables.addAll(getSortedListFromMap(serverDrawables));

        // TODO this is commented out for now because it saves us a lot of work
        // at the expense of a little performance
        //
        // drawables.addAll(getSortedListFromMap(localState));

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

    public void draw(Drawable drawable) {
        localState.put(nextLocalIndex, drawable);
        model.sendMessage(new ToServerStrokeMessage(nextLocalIndex, drawable, "", whiteboardID));
        nextLocalIndex++;
    }

    public void addDrawableFromServer(int id, Drawable drawable) {
        serverDrawables.put(id, drawable);
    }
}
