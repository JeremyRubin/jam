package whiteboard;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

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
public class User implements JSONable<User>, Runnable {
    private String username;
    private BlockingQueue<String> inQueue;
    public BlockingQueue<String> outQueue;

    private WhiteboardServerModel wb;
    private ConnectedUser connection;
    private boolean connected;

    public User(ConnectedUser connection) {
        this.connection = connection;
        this.username = "guest";
    }

    public String getName() {
        return this.username;
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

    @Override
    public User fromJSON(String jsonString) {
        // TODO Auto-generated method stub
        return null;
    };

    /**
     * Partially serialize User object.
     */
    @Override
    public JSONObject toJSON() {
        Map m = new LinkedHashMap();
        JSONObject j = new JSONObject();
        m.put("username", this.username);
        j.putAll(m);
        return j;
    }

    @Override
    public User fromJSON(JSONObject j) {
        // TODO Auto-generated method stub
        return null;
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

    public void disconnectUser() {
        this.connected = false;
    }

    public String handleRequest(String input) {
        JSONObject data = (JSONObject) JSONValue.parse(input);
        String action = (String) data.get(Messages.type);
        if (action.equals(Messages.newWhiteboard)) {
            // TODO
            NewWhiteboardMessage s = new NewWhiteboardMessage().fromJSON(data);
            wb = this.connection.server.createWhiteboard();
            return new SwitchWhiteboardMessage(wb.id).toJSON().toJSONString();
        } else if (action.equals(Messages.stroke)) {
            StrokeMessage s = new StrokeMessage().fromJSON(data);
            // TODO what should be done with s?

        } else if (action.equals(Messages.switchWhiteboard)) {
            // TODO

            SwitchWhiteboardMessage s = new SwitchWhiteboardMessage().fromJSON(data);
            if (this.connection.server.openWhiteboards.containsKey(s.whiteboardID))
                wb = this.connection.server.openWhiteboards.get(s.whiteboardID);
            else {

                wb = this.connection.server.createWhiteboard(s.whiteboardID);
                return new SwitchWhiteboardMessage(wb.id).toJSON().toJSONString();
            }
        } else if (action.equals(Messages.setUsernameMessage)) {
            // TODO
            SetUsernameMessage s = new SetUsernameMessage().fromJSON(data);
        } else if (action.equals(Messages.whiteboardCreated)) {

            throw new RuntimeException("Server shouldn't recieve WhiteboardCreatedMessage");

        }
        // Should never get here--make sure to return in each of the valid cases
        // above.
        throw new UnsupportedOperationException();
    }

}
