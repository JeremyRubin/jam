package whiteboard;

import java.util.ArrayList;

import message.StrokeMessage;
import server.WhiteboardServer;
import drawable.Drawable;

public class WhiteboardServerModel {
    // unique ID per Whiteboard instance generated from the random combination
    // of 2 or more words from a large dictionary (i.e., correct horse battery
    // staple).
    public final String id;

    // sequence of Drawables
    // smaller indices indicate that the element was drawn earlier
    private ArrayList<Drawable> drawablesList;

    // should keep track of its clients, assigning them their �own ID�s and
    // letting them set usernames
    // private Map<String, String> clients; // ???

    // the currently connected clients should be maintained here
    private CurrentUsers users;

    public WhiteboardServerModel(WhiteboardServer server) {
        synchronized (server.openWhiteboards) {
            this.users = new CurrentUsers(this.id);
            this.id = this.randomWordSequenceGenerator();
        }
    };

    public WhiteboardServerModel(WhiteboardServer server, String s) {
        // TODO thread safety
        synchronized (server.openWhiteboards) {

            if (server.openWhiteboards.containsKey(s)) {
                this.id = this.randomWordSequenceGenerator();
            } else {
                this.id = s;
                server.openWhiteboards.put(s, this);
            }

        }
    };

    private String randomWordSequenceGenerator() {
        return String.valueOf((int)(Math.random()*1000000));
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
        synchronized (users) {
            users.addUser(user);
            users.broadcastSelf();
        }
    }

    public void removeClient(User user) {
        synchronized (users) {
            users.removeUser(user);
            users.broadcastSelf();
        }
    }
}
