package whiteboard;

import java.util.ArrayList;
import java.util.List;

import message.FromServerStrokeMessage;
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
    private final List<Drawable> drawablesList;

    // the currently connected clients should be maintained here
    private CurrentUsers users;

    private SequentialIDGenerator sequentialIDGenerator = new SequentialIDGenerator();

    /**
     * Constructor that assigns a random string as the whiteboard ID.
     * 
     * @param server
     */
    public WhiteboardServerModel(WhiteboardServer server) {
        this(server, randomStringGenerator());
    };

    /**
     * Constructor that assigns the string s as the whiteboad ID if it's not
     * already taken. If it is already taken, generate a random String for the
     * ID.
     * 
     * @param server
     * @param s
     *            requested new whiteboardID
     */
    public WhiteboardServerModel(WhiteboardServer server, String s) {
        synchronized (server.openWhiteboards) {
            while (server.openWhiteboards.containsKey(s)) {
                s = randomStringGenerator();
            }
            this.id = s;
            this.users = new CurrentUsers(this.id);
            this.drawablesList = new ArrayList<Drawable>();
            server.openWhiteboards.put(this.id, this);
        }
    };

    /**
     * Generates random string for whiteboard IDs.
     * 
     * @return random string ID
     */
    private static String randomStringGenerator() {
        return String.valueOf((int) (Math.random() * 1000000));
    }

    /**
     * Generates sequential IDs for messages.
     * 
     * @return
     */
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
        s = s.withServerID(this.getServerID());
        synchronized (drawablesList) {
            drawablesList.add(s.drawable());
        }
        this.broadcastStroke(s);
    }

    /**
     * Send all clients the StrokeMessage.
     * 
     * @param m
     */
    public void broadcastStroke(StrokeMessage m) {
        users.broadcast(m.toJSON().toJSONString());
    }

    /**
     * Add a user to currentUsers users.
     * 
     * @param user
     */
    public void addClient(User user) {
        users.addUser(user);
        synchronized (drawablesList) {
            SequentialIDGenerator seq = new SequentialIDGenerator();
            for (Drawable d : drawablesList) {
                user.output(new FromServerStrokeMessage(seq.getID(), -1, d, "", id));
            }
        }
    }

    /**
     * Remove a user to currentUsers users if it exists.
     * 
     * @param user
     */
    public void removeClient(User user) {
        users.removeUser(user);
    }
}
