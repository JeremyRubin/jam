package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import whiteboard.WhiteboardClientModel;
import client.tools.DrawingTool;
import client.tools.Eraser;
import client.tools.Pen;

public class DrawingToolbar extends JToolBar {
    private WhiteboardClientModel model;
    private JButton button;

    public DrawingToolbar(WhiteboardClientModel model) {
        this.model = model;
        this.setFloatable(false);

        ButtonGroup toolButtons = new ButtonGroup();
        boolean first = true;
        for (Class<? extends DrawingTool> toolClass : new Class[] { Pen.class, Eraser.class }) {
            ToolSelectionButton button = new ToolSelectionButton(toolClass);
            toolButtons.add(button);
            this.add(button);

            // the first tool in the list is the one selected by default
            if (first) {
                button.doClick();
                first = false;
            }
        }

        this.addSeparator();

        this.add(new JLabel("Current color: "));
        button = new JButton("             "); // makes it bigger
        button.setBackground(this.model.color);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(DrawingToolbar.this, "Pick color",
                        DrawingToolbar.this.model.color);
                if (color != null) {
                    button.setBackground(color);
                    DrawingToolbar.this.model.setColor(color);
                }
            }
        });
        this.add(button);
    }

    private class ToolSelectionButton extends JRadioButton {
        private Class<? extends DrawingTool> toolClass;

        public ToolSelectionButton(Class<? extends DrawingTool> toolClass) {
            this.toolClass = toolClass;
            this.setText(toolClass.getSimpleName());
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DrawingToolbar.this.model.setTool(ToolSelectionButton.this.toolClass);
                }
            });
        }
    }
}
