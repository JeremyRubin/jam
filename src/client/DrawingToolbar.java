package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import client.tools.DrawingTool;
import client.tools.Eraser;
import client.tools.Pen;

public class DrawingToolbar extends JToolBar {
    private WhiteboardClientModel model;
    private JButton colorPickerButton;

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
        colorPickerButton = new JButton("             "); // makes it bigger
        colorPickerButton.setBackground(this.model.getColor());
        colorPickerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(DrawingToolbar.this, "Pick color",
                        DrawingToolbar.this.model.getColor());
                if (color != null) {
                    colorPickerButton.setBackground(color);
                    DrawingToolbar.this.model.setColor(color);
                }
            }
        });
        this.add(colorPickerButton);

        this.addSeparator(new Dimension(40, 1));
        JButton changeUsernameButton = new JButton("Change username...");
        changeUsernameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog(DrawingToolbar.this, "Change username to",
                        DrawingToolbar.this.model.getUsername());
                DrawingToolbar.this.model.setUsername(username);
            }
        });
        this.add(changeUsernameButton);
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
