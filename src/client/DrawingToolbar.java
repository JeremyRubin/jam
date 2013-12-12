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

/**
 * Creates drawing toolbar GUI.
 * 
 * Includes: pen/eraser chooser, color picker, change username button.
 * 
 * DrawingToolbar Testing Strategy:
 * 
 * 1) Clicking pen radio button: only pen radio button selected, user should be
 * able to draw black lines 2) Clicking eraser radio button: only eraser radio
 * button selected, user should be able to erase by drawing thick white lines 3)
 * Clicking color button: should bring up a color selector window. After user
 * updates the color, the button should be the selected color, and the user
 * should be drawing in the selected color. 4) Clicking username button: should
 * bring up a window to input a new username. Should allow any username that's
 * not an empty string to be selected. Change should be reflected in
 * "current user" list at the bottom of the DrawingGUI.
 */
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
                if (username == null)
                    return;
                username = WhiteboardClientModel.sanitizeString(username);
                DrawingToolbar.this.model.setUsername(username);
            }
        });
        this.add(changeUsernameButton);

        JButton switchWhiteboardButton = new JButton("Switch whiteboard...");
        switchWhiteboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String whiteboardID = JOptionPane.showInputDialog(DrawingToolbar.this,
                        "Switch to whiteboard: (leave blank for a new randomly-named board)",
                        DrawingToolbar.this.model.whiteboard == null ? ""
                                : DrawingToolbar.this.model.whiteboard.whiteboardID);
                if (whiteboardID == null)
                    return;
                whiteboardID = WhiteboardClientModel.sanitizeString(whiteboardID);
                DrawingToolbar.this.model.switchWhiteboard(whiteboardID);
            }
        });
        this.add(switchWhiteboardButton);
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
