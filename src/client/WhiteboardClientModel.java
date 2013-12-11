package client;

import java.awt.Color;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import message.JSONable;
import message.SetUsernameMessage;
import client.tools.DrawingTool;
import client.tools.Pen;

public class WhiteboardClientModel {
    private Color color = Color.BLACK;
    private int brushWidth = 5;
    private DrawingTool tool;

    public BlockingQueue<String> outgoing = new LinkedBlockingQueue<>();
    public BlockingQueue<String> incoming = new LinkedBlockingQueue<>();

    public Whiteboard whiteboard;
    private String username;

    public WhiteboardClientModel() {
        setTool(Pen.class);
        setUsername(String.format("guest-%04d", (int) (Math.random() * 1000)));
    }

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

    public void setColor(Color color) {
        this.color = color;
        setTool(this.tool.getClass());
    }

    public Color getColor() {
        return color;
    }

    public int getBrushWidth() {
        return brushWidth;
    }

    public DrawingTool getTool() {
        return tool;
    }

    private void sendMessage(String message) {
        outgoing.offer(message);
    }

    public void sendMessage(JSONable message) {
        sendMessage(message.toJSON().toJSONString());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        sendMessage(new SetUsernameMessage(username));
    }
}
