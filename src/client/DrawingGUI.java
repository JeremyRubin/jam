package client;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import whiteboard.WhiteboardClientModel;

public class DrawingGUI {
    private Canvas canvas;
    private DrawingToolbar toolbar;
    private WhiteboardClientModel model;

    private JFrame frame;

    private ClientGUI gui;

    public DrawingGUI(ClientGUI gui) {
        frame = new JFrame("Whiteboard");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        model = new WhiteboardClientModel();
        canvas = new Canvas(model);
        frame.add(canvas, BorderLayout.CENTER);
        toolbar = new DrawingToolbar(model);
        frame.add(toolbar, BorderLayout.PAGE_START);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
