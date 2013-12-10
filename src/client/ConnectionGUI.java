package client;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ConnectionGUI {
    private JTextField ipAddressTextField = new JTextField(20);
    private JTextField portTextField = new JTextField(20);

    private ClientGUI gui;

    private JFrame frame;

    public ConnectionGUI(ClientGUI gui) {
        this.gui = gui;

        frame = new JFrame("Connect to a server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        frame.add(new JLabel("IP address: "));
        ipAddressTextField = new JTextField("127.0.0.1", 20);
        frame.add(ipAddressTextField);

        frame.add(new JLabel("Port: "));
        portTextField = new JTextField("4444", 20);
        frame.add(portTextField);

        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
                ConnectionGUI.this.gui.connect(ipAddressTextField.getText(), portTextField.getText());
            }
        });
        frame.add(connectButton);

        frame.pack();
        frame.setVisible(true);
    }
}
