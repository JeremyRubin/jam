package whiteboard;

import java.util.concurrent.BlockingQueue;

import message.FromServerStrokeMessage;
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
    private String username;
    private BlockingQueue<String> inQueue;
    public BlockingQueue<String> outQueue;

    private WhiteboardServerModel wb;
    private ConnectedUser connection;

    public User(ConnectedUser connection) {
        this.connection = connection;
        this.username = "guest";
    }

    public String getName() {
        return this.username;
    }

    public void setName(String newName) {
        this.username = newName;
    }

    /**
     * Add a control msg to be processed by the user
     * 
     * @param msg
     */
    public void input(String msg) {
        inQueue.add(msg);
    }

    public void output(String msg) {
        this.outQueue.add(msg);
    }

    /**
     * start the User thread
     */
    @Override
    public void run() {
        try {
            String output = "";
            while (true) {
                String message = inQueue.take();
                if (message == null)
                    break;
                output = handleRequest(message);
                this.output(output);
            }
        } catch (Exception e) {
        } finally {
            this.wb.removeClient(this); // remove user from users

        }
    }

    public String handleRequest(String input) {
        JSONObject data = (JSONObject) JSONValue.parse(input);
        String action = (String) data.get(Messages.type);
        if (action.equals(Messages.newWhiteboard)) {
            NewWhiteboardMessage s = new NewWhiteboardMessage().fromJSON(data);
            wb = this.connection.server.createWhiteboard();
            return new SwitchWhiteboardMessage(wb.id).toJSON().toJSONString();
        } else if (action.equals(Messages.toServerStroke)) {
            StrokeMessage s = new StrokeMessage().fromJSON(data);
            wb.handleDrawable(s);
            s.setID(wb.getServerID());
            return ((FromServerStrokeMessage) s).toJSON().toJSONString();
        } else if (action.equals(Messages.switchWhiteboard)) {
            SwitchWhiteboardMessage s = new SwitchWhiteboardMessage().fromJSON(data);
            if (this.connection.server.openWhiteboards.containsKey(s.whiteboardID))
                wb = this.connection.server.openWhiteboards.get(s.whiteboardID);
            else {
                wb = this.connection.server.createWhiteboard(s.whiteboardID);
                return new SwitchWhiteboardMessage(wb.id).toJSON().toJSONString();
            }
        } else if (action.equals(Messages.setUsernameMessage)) {
            SetUsernameMessage s = new SetUsernameMessage().fromJSON(data);
            this.username = s.username;
            return new SetUsernameMessage(this.username).toJSON().toJSONString();
        } else if (action.equals(Messages.fromServerStroke)) {
            throw new RuntimeException("Server shouldn't recieve fromServerStrokeMessage");
        } else if (action.equals(Messages.currentUsers)) {
            throw new RuntimeException("Server shouldn't recieve UserListMessage");
        } else if (action.equals(Messages.whiteboardCreated)) {
            throw new RuntimeException("Server shouldn't recieve WhiteboardCreatedMessage");
        }
        // Should never get here--make sure to return in each of the valid cases
        // above.
        throw new UnsupportedOperationException();
    }
}
