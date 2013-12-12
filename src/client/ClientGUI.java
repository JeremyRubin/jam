package client;

import java.io.IOException;
import java.net.Socket;

import javax.swing.SwingUtilities;

/**
 * Run this class to start the client.
 * 
 */
public class ClientGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ConnectionGUI(new ClientGUI());
            }
        });
    }

    /**
     * Open a whiteboard. Precondition: ip and port must be valid IP addresses
     * and port numbers, respectively.
     * 
     * @param ip
     * @param port
     */
    public void connect(String ip, String port) {
        try {
            // TODO consider catching input errors here
            Socket socket = new Socket(ip, Integer.valueOf(port));
            new DrawingGUI(this, socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
