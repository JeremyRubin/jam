package whiteboard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import drawable.Drawable;

public class WhiteboardServerModel {
    // unique ID per Whiteboard instance generated from the random combination
    // of 2 or more words from a large dictionary (i.e., correct horse battery
    // staple).
    public final String id;
    public static final Set<String> whiteboards = new HashSet<String>();
    // sequence of Drawables
    // smaller indices indicate that the element was drawn earlier
    private ArrayList<Drawable> drawablesList;

    // should keep track of its clients, assigning them their own ID’s and
    // letting them set usernames
    private Map<String, String> clients; // ???

    // the currently connected clients should be maintained here
    private CurrentUsers users;

    public WhiteboardServerModel() {
        synchronized (whiteboards) {
            this.id = this.randomWordSequenceGenerator();
            whiteboards.add(this.id);
        }
    };

    public WhiteboardServerModel(String s) {
        // TODO thread safety
        synchronized (whiteboards) {

            if (whiteboards.contains(s))
                this.id = this.randomWordSequenceGenerator();
            else {
                this.id = s;
                whiteboards.add(s);
            }

        }
    };

    private String randomWordSequenceGenerator() {
        // TODO
        return "hello-world-bye";
    }
}
