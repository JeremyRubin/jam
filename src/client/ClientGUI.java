package client;

import java.io.IOException;
import java.net.Socket;

import javax.swing.SwingUtilities;

public class ClientGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ConnectionGUI(new ClientGUI());
            }
        });
    }

    public void connect(String ip, String port) {
        try {
            // TODO consider catching input errors here
            Socket socket = new Socket(ip, Integer.valueOf(port));
            new DrawingGUI(this, socket);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
