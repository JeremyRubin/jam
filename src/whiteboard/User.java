package whiteboard;

import java.io.IOException;
import java.net.Socket;
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

import server.WhiteboardServer;

/**
 * Represents a user.
 * 
 */
public class User implements JSONable<User>, Runnable {
    private final String username;
    private final Socket socket;
    private BlockingQueue<String> queue;
    private final WhiteboardServer server;
    private WhiteboardServerModel wb;

    public User(Socket socket, WhiteboardServer server) {
        this.server = server;
        this.socket = socket;
    }

    public User(String name, Socket socket, WhiteboardServer server) {
        this.username = name;
        this.socket = socket;
        this.server = server;

    }

    public String getName() {
        return this.username;
    }

    /**
     * Add a msg to the queue to be sent out.
     * 
     * @param msg
     */
    public void add(String msg) {
        queue.add(msg);
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
            while (output != "quit") {
                String message = queue.take();
                output = handleRequest(message);
                // what to do with output??
            }
        } catch (Exception e) {
        } finally {
            try {
                this.wb.removeClient(this); // remove user from users
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String handleRequest(String input) {
        JSONObject data = (JSONObject) JSONValue.parse(input);
        String action = (String) data.get(Messages.type);
        if (action.equals(Messages.newWhiteboard)) {
            // TODO

            NewWhiteboardMessage s = new NewWhiteboardMessage().fromJSON(data);
            wb = server.createWhiteboard();
            return new SwitchWhiteboardMessage(wb.id, 10).toJSON().toJSONString();
        } else if (action.equals(Messages.stroke)) {
            StrokeMessage s = new StrokeMessage().fromJSON(data);
            // TODO what should be done with s?

        } else if (action.equals(Messages.switchWhiteboard)) {
            // TODO

            SwitchWhiteboardMessage s = new SwitchWhiteboardMessage().fromJSON(data);
            if (server.openWhiteboards.containsKey(s.whiteboardID))
                wb = server.openWhiteboards.get(s.whiteboardID);
            else {

                wb = server.createWhiteboard(s.whiteboardID);
                return new SwitchWhiteboardMessage(wb.id, 10).toJSON().toJSONString();
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
