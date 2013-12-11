package client;

import java.awt.Color;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import message.JSONable;
import client.tools.DrawingTool;
import client.tools.Pen;

public class WhiteboardClientModel {
    private Color color = Color.BLACK;
    private int brushWidth = 5;
    private DrawingTool tool;

    public BlockingQueue<String> outgoing = new LinkedBlockingQueue<>();
    public BlockingQueue<String> incoming = new LinkedBlockingQueue<>();

    public Whiteboard whiteboard;

    public WhiteboardClientModel() {
        setTool(Pen.class);
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
}
