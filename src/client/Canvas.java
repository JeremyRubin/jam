package client;

import global.Constants;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import drawable.Drawable;

/**
 * Canvas represents a drawing surface that allows the user to draw on it
 * freehand, with the mouse.
 */
public class Canvas extends JPanel {
    private final WhiteboardClientModel model;

    public Canvas(WhiteboardClientModel model) {
        this.model = model;
        this.setPreferredSize(new Dimension(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT));
        addMouseHandler();
    }

    /**
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    public void paintComponent(Graphics g) {
        if (model.whiteboard != null)
            g.drawImage(model.whiteboard.getImage(), 0, 0, null);
    }

    /*
     * Draw a line between two points (x1, y1) and (x2, y2), specified in pixels
     * relative to the upper-left corner of the drawing buffer.
     */
    private void drawSegment(int x1, int y1, int x2, int y2) {
        Drawable segment = model.getTool().draw(x1, y1, x2, y2);
        model.whiteboard.draw(segment);
        repaint();
    }

    /*
     * Add the mouse listener that supports the user's freehand drawing.
     */
    private void addMouseHandler() {
        MouseHandler controller = new MouseHandler();
        addMouseListener(controller);
        addMouseMotionListener(controller);
    }

    /*
     * DrawingController handles the user's freehand drawing.
     */
    private class MouseHandler extends MouseAdapter {
        private int lastX, lastY;

        @Override
        public void mousePressed(MouseEvent e) {
            lastX = e.getX();
            lastY = e.getY();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            updateLocation(e.getX(), e.getY());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            updateLocation(e.getX(), e.getY());
        }

        private void updateLocation(int x, int y) {
            drawSegment(lastX, lastY, x, y);
            lastX = x;
            lastY = y;
        }
    }
}
