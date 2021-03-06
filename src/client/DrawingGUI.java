package client;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import message.FromServerStrokeMessage;
import message.Messages;
import message.SwitchWhiteboardMessage;
import message.UserListMessage;
import message.WhiteboardCreatedMessage;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Creates drawing canvas GUI that includes the canvas and the drawing tools.
 * 
 * DrawingGUI Testing Strategy:
 * 
 * 1) Test logging in to server running on several ports/various IP addresses.
 * 
 * 2) Test connecting to a whiteboard which already exists and one which does
 * not
 * 
 * 3) Test creating a new whiteboard
 * 
 * 4) Test Switching brush sizes
 * 
 * 5) Test lag with three open clients (how long to display)
 * 
 * 6) Test that names update appropriately with three clients
 * 
 * 7) Test Eraser works
 * 
 * 8) Test changing color
 * 
 * 9) Test changing color while on Eraser keeps it as an eraser.
 * 
 * 10) Test That userlist shrinks when a client disconnects/switches boards.
 * 
 * 11) Test changing usernames
 * 
 */
public class DrawingGUI {
    private Canvas canvas;
    private DrawingToolbar toolbar;
    private JLabel userList;

    private WhiteboardClientModel model;

    private JFrame frame;

    private Socket socket;

    private IncomingReader readerThread;
    private OutgoingWriter writerThread;

    public DrawingGUI(ClientGUI gui, Socket socket) {
        this.socket = socket;

        model = new WhiteboardClientModel();

        frame = new JFrame("Whiteboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        toolbar = new DrawingToolbar(model);
        frame.add(toolbar, BorderLayout.PAGE_START);

        // to make the window the right size
        addCanvas();
        frame.pack();
        removeCanvas();

        frame.setVisible(true);

        readerThread = new IncomingReader();
        writerThread = new OutgoingWriter();
        new Thread(readerThread).start();
        new Thread(writerThread).start();

        // automatically connects to a default whiteboard
        model.sendMessage(new SwitchWhiteboardMessage("default"));
    }

    /**
     * Read in incoming messages.
     * 
     */
    private class IncomingReader implements Runnable {
        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                for (String line = in.readLine(); in != null; line = in.readLine()) {
                    final String message = line;
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            handleMessage(message);
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                writerThread.terminate();
            }
        }
    }

    /**
     * Print out server's outgoing messages.
     * 
     */
    private class OutgoingWriter implements Runnable {
        private volatile boolean running = true;

        public void terminate() {
            running = false;
        }

        @Override
        public void run() {
            try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                while (running) {
                    try {
                        String message = model.outgoing.take();
                        out.println(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handle the message depending on its type.
     * 
     * @param message
     *            String representation of the message
     */
    private void handleMessage(String message) {
        JSONObject data = (JSONObject) JSONValue.parse(message);
        String action = (String) data.get(Messages.type);
        if (action.equals(Messages.switchWhiteboard)) {
            newCanvas();
            SwitchWhiteboardMessage m = SwitchWhiteboardMessage.STATIC.fromJSON(data);
            model.whiteboard = new Whiteboard(m.whiteboardID, model);
            frame.setTitle("Now connected to: " + m.whiteboardID);
            return;
        } else if (action.equals(Messages.whiteboardCreated)) {
            newCanvas();
            WhiteboardCreatedMessage m = WhiteboardCreatedMessage.STATIC.fromJSON(data);
            model.whiteboard = new Whiteboard(m.whiteboardID, model);
            frame.setTitle("Now connected to: " + m.whiteboardID);
            return;
        } else if (model.whiteboard != null) {
            if (action.equals(Messages.currentUsers)) {
                UserListMessage m = UserListMessage.STATIC.fromJSON(data);
                if (m.whiteboardID.equals(model.whiteboard.whiteboardID)) {
                    setCurrentUsers(m.getUsers());
                }
            } else if (action.equals(Messages.fromServerStroke)) {
                FromServerStrokeMessage m = FromServerStrokeMessage.STATIC.fromJSON(data);
                if (m.whiteboardID.equals(model.whiteboard.whiteboardID)) {
                    model.whiteboard.addDrawableFromServer(m.id, m.drawable);
                }
            }
        }
        frame.repaint();
    }

    /**
     * Update userList
     * 
     * @param users
     *            updated list of users
     */
    private void setCurrentUsers(List<String> users) {
        StringBuilder sb = new StringBuilder();
        sb.append("Currently connected users: ");
        for (String user : users) {
            sb.append(user);
            sb.append(", ");
        }
        if (sb.lastIndexOf(",") >= 0)
            sb.delete(sb.lastIndexOf(","), sb.length());
        userList.setText(sb.toString());
    }

    /**
     * Remove existing canvas (if it exists), then add new one.
     */
    private void newCanvas() {
        if (canvas != null)
            removeCanvas();
        addCanvas();
    }

    /**
     * Set new canvas variable, update GUI.
     */
    private void addCanvas() {
        canvas = new Canvas(model);
        frame.add(canvas, BorderLayout.CENTER);
        userList = new JLabel();
        setCurrentUsers(Collections.EMPTY_LIST);
        frame.add(userList, BorderLayout.PAGE_END);
    }

    /**
     * Set canvas variable to null, update GUI.
     */
    private void removeCanvas() {
        frame.remove(canvas);
        canvas = null;
        frame.remove(userList);
        userList = null;
    }
}
