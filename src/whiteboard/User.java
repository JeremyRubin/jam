package whiteboard;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import message.JSONable;
import message.Messages;
import message.NewWhiteboardMessage;
import message.SetUsernameMessage;
import message.StrokeMessage;
import message.SwitchWhiteboardMessage;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import server.ConnectedUser;

/**
 * Represents a user.
 * 
 */
public class User implements Runnable {
    public static final String KILL_MSG = "";
    private static final String DO_NOTHING = "Donot";
    private String username;
    private final BlockingQueue<String> inQueue;
    public final BlockingQueue<String> outQueue;

    private WhiteboardServerModel wb;
    private final ConnectedUser connection;

    /**
     * Constructor for User.
     * 
     * @param connection
     *            ConnectedUser
     */
    public User(ConnectedUser connection) {
        this.connection = connection;
        this.username = "new-guest";
        this.inQueue = new LinkedBlockingQueue<String>();
        this.outQueue = new LinkedBlockingQueue<String>();
    }

    /**
     * Get instance's username.
     * 
     * @return username
     */
    public String getName() {
        return this.username;
    }

    /**
     * Set instance's username.
     * 
     */
    public void setName(String newName) {
        this.username = newName;
    }

    /**
     * Add a control message to be processed by the user in User's run method.
     * 
     * @param msg
     */
    public void input(String msg) {
        this.inQueue.add(msg);
    }

    /**
     * Add a control message to be processed by the server in ConnectedUser's
     * WriterQueue.
     * 
     * @param msg
     */
    public void output(String msg) {
        this.outQueue.add(msg);
    }

    public void output(JSONable message) {
        this.output(message.toJSON().toJSONString());
    }

    /**
     * Start the User thread.
     */
    @Override
    public void run() {
        try {
            while (true) {
                String message = this.inQueue.take();
                if (message.equals(User.KILL_MSG)) {
                    break;
                } else if (message.equals(User.DO_NOTHING)) {
                    continue;
                }
                handleRequest(message);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (this.wb != null) {
                this.wb.removeClient(this); // remove user from users
            }
        }
    }

    /**
     * Handle the message depending on its type.
     * 
     * @param input
     *            String representation of the message
     * @return output String message
     */
    public void handleRequest(String input) {
        JSONObject data = (JSONObject) JSONValue.parse(input);
        String action = (String) data.get(Messages.type);
        if (action.equals(Messages.newWhiteboard)) {
            if (wb != null) {
                this.wb.removeClient(this);
            }
            NewWhiteboardMessage s = new NewWhiteboardMessage().fromJSON(data);
            wb = this.connection.server.createWhiteboard();
            output(new SwitchWhiteboardMessage(wb.id));
            this.wb.addClient(this);
        } else if (action.equals(Messages.switchWhiteboard)) {
            SwitchWhiteboardMessage s = SwitchWhiteboardMessage.STATIC.fromJSON(data);
            if (wb != null) {
                this.wb.removeClient(this);
            }
            synchronized (this.connection.server.openWhiteboards) {
                if (this.connection.server.openWhiteboards.containsKey(s.whiteboardID))
                    // if requested whiteboard exists, switch to it
                    wb = this.connection.server.openWhiteboards.get(s.whiteboardID);
                else {
                    // if requested whiteboard doesn't exist, create it and
                    // switch to it
                    wb = this.connection.server.createWhiteboard(s.whiteboardID);
                }
            }
            this.output(new SwitchWhiteboardMessage(wb.id).toJSON().toJSONString());
            this.wb.addClient(this);
        } else if (action.equals(Messages.toServerStroke) && this.wb != null) {
            StrokeMessage s = StrokeMessage.STATIC.fromJSON(data);
            wb.handleDrawable(s);
            /**
             * HEY ANAND LOOK HERE!!!!
             * 
             * TODO TODO TODO TODO
             */
            output(s.getDeleteMessage());
        } else if (action.equals(Messages.setUsernameMessage)) {
            SetUsernameMessage s = SetUsernameMessage.STATIC.fromJSON(data);
            this.username = s.username;
            output(new SetUsernameMessage(this.username));
        } else if (action.equals(Messages.fromServerStroke)) {
            throw new RuntimeException("Server shouldn't recieve fromServerStrokeMessage");
        } else if (action.equals(Messages.currentUsers)) {
            throw new RuntimeException("Server shouldn't recieve UserListMessage");
        } else if (action.equals(Messages.whiteboardCreated)) {
            throw new RuntimeException("Server shouldn't recieve WhiteboardCreatedMessage");
        } else {
            // Should never get here
            throw new UnsupportedOperationException();
        }
    }
}
