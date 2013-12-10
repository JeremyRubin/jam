package client;

import javax.swing.SwingUtilities;

public class ClientGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DrawingGUI();
            }
        });
    }
}
