package client;

import java.io.IOException;
import java.net.Socket;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Run this class to start the client.
 * 
 */
public class ClientGUI {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ConnectionGUI(new ClientGUI());
            }
        });
    }

    /**
     * Open a whiteboard.
     * 
     * Precondition: ip and port must be valid IP addresses
     * and port numbers, respectively.
     * 
     * @param ip
     *            a String representing an IP address
     * @param port
     *            a String representing a port number
     */
    public void connect(String ip, String port) {
        try {
            Socket socket = new Socket(ip, Integer.valueOf(port));
            new DrawingGUI(this, socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
