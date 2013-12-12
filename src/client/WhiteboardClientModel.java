package client;

import java.awt.Color;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import message.JSONable;
import message.NewWhiteboardMessage;
import message.SetUsernameMessage;
import message.SwitchWhiteboardMessage;
import client.tools.DrawingTool;
import client.tools.Pen;

/**
 * Model for a user's DrawingGUI.
 */
public class WhiteboardClientModel {
    /**
     * The current color of the drawing tool.
     */
    private Color color = Color.BLACK;

    /**
     * The current brush width of the drawing tool (if applicable)
     */
    private int brushWidth = 5;

    /**
     * The current drawing tool being used.
     */
    private DrawingTool tool;

    /**
     * All the pending messages to be sent to the server.
     */
    public final BlockingQueue<String> outgoing = new LinkedBlockingQueue<>();

    /**
     * The whiteboard currently connected to (may be null if no whiteboard is connected to).
     */
    public Whiteboard whiteboard;

    /**
     * The username the user goes by now.
     */
    private String username;

    /**
     * constructs a new WhiteboardClientModel, should be called upon connection to a server
     */
    public WhiteboardClientModel() {
        setTool(Pen.class); // set default tool as Pen
        setUsername(getGuestUsername());
    }

    /**
     * Sets the currently-active tool.
     * 
     * @param toolClass
     *            the class of the DrawingTool to set
     */
    public void setTool(Class<? extends DrawingTool> toolClass) {
        try {
            tool = toolClass.newInstance().createFromModel(this);
        } catch (InstantiationException e) {
            // this should never happen if there are no bugs, hence exit
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalAccessException e) {
            // this should never happen if there are no bugs, hence exit
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Changes the color used for future drawing action.
     * 
     * @param color
     *            a Color object
     */
    public void setColor(Color color) {
        this.color = color;
        setTool(this.tool.getClass());
    }

    /**
     * @return the current drawing color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Changes the brush width used for future drawing action.
     * 
     * @param brushWidth
     *            a positive integer
     */
    public void setBrushWidth(int brushWidth) {
        this.brushWidth = brushWidth;
        setTool(this.tool.getClass());
    }

    /**
     * @return the current brush width
     */
    public int getBrushWidth() {
        return brushWidth;
    }

    /**
     * @return the currently-active drawing tool
     */
    public DrawingTool getTool() {
        return tool;
    }

    /**
     * queues up a message to be sent to the server
     * 
     * @param message
     *            a String
     */
    private void sendMessage(String message) {
        outgoing.offer(message);
    }

    /**
     * Queues up a message to be sent to the server.
     * 
     * @param message
     *            a JSONable object (for example, a SwitchWhiteboardMessage)
     */
    public void sendMessage(JSONable message) {
        sendMessage(message.toJSON().toJSONString());
    }

    /**
     * @return the current username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Changes your username.
     * 
     * If provided an empty string, a random guest username is chosen instead.
     * 
     * @param username
     *            a String
     */
    public void setUsername(String username) {
        if (username.equals(""))
            username = getGuestUsername();
        this.username = username;
        sendMessage(new SetUsernameMessage(username));
    }

    /**
     * Switch which whiteboard you're currently connected to. (Disconnect from the current
     * whiteboard if necessary.)
     * 
     * If an empty string is given as the whiteboard ID, then a new, randomly-named whiteboard is
     * created.
     * 
     * @param whiteboardID
     *            a String
     */
    public void switchWhiteboard(String whiteboardID) {
        if (whiteboardID.equals(""))
            sendMessage(new NewWhiteboardMessage());
        else
            sendMessage(new SwitchWhiteboardMessage(whiteboardID));
    }

    /**
     * Strips out annoying characters.
     * 
     * @param s
     *            a String
     * @return s but with all non-whitelisted-characters stripped out
     */
    public static String sanitizeString(String s) {
        return s.replaceAll("[^a-zA-Z0-9 '!?.,\\-\\(\\)]", "");
    }

    /**
     * Generates a (not necessarily unique) guest username.
     * 
     * @return a randomly-chosen String
     */
    private static String getGuestUsername() {
        return String.format("guest-%04d", (int) (Math.random() * 1000));
    }
}
