package client;

import global.Constants;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import whiteboard.WhiteboardClientModel;
import drawable.Drawable;

/**
 * Canvas represents a drawing surface that allows the user to draw on it
 * freehand, with the mouse.
 */
public class Canvas extends JPanel {
    // image where the user's drawing is stored
    private Image drawingBuffer;

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
        g.drawImage(model.getImage(), 0, 0, null);
    }

    /*
     * Draw a line between two points (x1, y1) and (x2, y2), specified in pixels
     * relative to the upper-left corner of the drawing buffer.
     */
    private void drawSegment(int x1, int y1, int x2, int y2) {
        Drawable segment = model.tool.draw(x1, y1, x2, y2);
        model.draw(segment);
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
    private class MouseHandler implements MouseListener, MouseMotionListener {
        // store the coordinates of the last mouse event, so we can
        // draw a line segment from that last point to the point of the next
        // mouse event.
        private int lastX, lastY;

        /*
         * When mouse button is pressed down, start drawing.
         */
        @Override
        public void mousePressed(MouseEvent e) {
            lastX = e.getX();
            lastY = e.getY();
        }

        /*
         * When mouse moves while a button is pressed down, draw a line segment.
         */
        @Override
        public void mouseDragged(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            drawSegment(lastX, lastY, x, y);
            lastX = x;
            lastY = y;
        }

        // Ignore all these other mouse events.
        @Override
        public void mouseMoved(MouseEvent e) {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}
