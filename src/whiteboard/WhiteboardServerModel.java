package whiteboard;

import java.util.ArrayList;

import message.StrokeMessage;
import server.WhiteboardServer;
import utils.SequentialIDGenerator;
import drawable.Drawable;

public class WhiteboardServerModel {
    // unique ID per Whiteboard instance generated from the random combination
    // of 2 or more words from a large dictionary (i.e., correct horse battery
    // staple).
    public final String id;

    // sequence of Drawables
    // smaller indices indicate that the element was drawn earlier
    private ArrayList<Drawable> drawablesList;

    // the currently connected clients should be maintained here
    private CurrentUsers users;

    private SequentialIDGenerator sequentialIDGenerator = new SequentialIDGenerator();

    public WhiteboardServerModel(WhiteboardServer server) {
        synchronized (server.openWhiteboards) {
            this.users = new CurrentUsers(this.id);
            this.id = this.randomStringGenerator();
        }
    };

    public WhiteboardServerModel(WhiteboardServer server, String s) {
        // TODO thread safety
        synchronized (server.openWhiteboards) {

            if (server.openWhiteboards.containsKey(s)) {
                this.id = this.randomStringGenerator();
            } else {
                this.id = s;
                server.openWhiteboards.put(s, this);
            }

        }
    };

    private String randomStringGenerator() {
        return String.valueOf((int) (Math.random() * 1000000));
    }

    public int getServerID() {
        return sequentialIDGenerator.getID();
    }

    /**
     * append drawable to end of drawablesList and then create a strokeMessage
     * to sync clients with.
     * 
     * @param drawable
     */
    public synchronized void handleDrawable(StrokeMessage s) {
        drawablesList.add(s.drawable());
        this.broadcastStroke(s);
    }

    /**
     * send all clients the StrokeMessage
     * 
     * @param m
     */
    public void broadcastStroke(StrokeMessage m) {
        users.broadcast(m.toJSON().toJSONString());
    }

    /**
     * add a client and then send them all the data needed
     * 
     * @param user
     */
    public void addClient(User user) {
        users.addUser(user);
    }

    public void removeClient(User user) {
        users.removeUser(user);
    }
}
