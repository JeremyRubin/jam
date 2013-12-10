package client;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import whiteboard.WhiteboardClientModel;

public class DrawingGUI extends JFrame {
    private Canvas canvas;
    private DrawingToolbar toolbar;
    private WhiteboardClientModel model;

    public DrawingGUI() {
        JFrame window = new JFrame("Whiteboard");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLayout(new BorderLayout());

        model = new WhiteboardClientModel();
        canvas = new Canvas(model);
        window.add(canvas, BorderLayout.CENTER);
        toolbar = new DrawingToolbar(model);
        window.add(toolbar, BorderLayout.PAGE_START);
        window.pack();
        window.setVisible(true);
    }
}
