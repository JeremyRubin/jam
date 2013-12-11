package whiteboard;

import java.util.ArrayList;
import java.util.List;

import message.JSONable;
import message.UserListMessage;

import org.json.simple.JSONObject;

/**
 * Represents the collection of current users connected to a certain whiteboard.
 * 
 */
public class CurrentUsers implements JSONable<UserListMessage> {
    // list of all currently logged in users
    // FIXME this seems really bad
    private List<User> users = new ArrayList<User>();

    // whiteboardID corresponding to the whiteboard CurrentUsers is connected
    // to.
    private final String whiteboardID;

    /**
     * Constructor that takes a whiteboard ID.
     * 
     * @param whiteboardID
     *            of the whiteboard.
     */
    public CurrentUsers(String whiteboardID) {
        this.whiteboardID = whiteboardID;
    }

    /**
     * Constructor that takes a whiteboard ID and existing list of users.
     * 
     * @param whiteboardID
     *            of the whiteboard.
     * @param users
     *            list of User objects currently connected.
     */
    public CurrentUsers(String whiteboardID, List<User> users) {
        this.whiteboardID = whiteboardID;
        this.users = users;
    }

    /**
     * Send a message to all users in this CurrentUsers object.
     * 
     * @param msg
     *            string message to be sent.
     */
    public void broadcast(String msg) {
        for (User user : users) {
            user.output(msg);
        }
    };

    /**
     * Update users to include otherUser. We allow duplicate usernames in a
     * single CurrentUsers object.
     * 
     * Broadcast a USersListMessage to all users in the CurrentUsers object.
     * 
     * @param otherUser
     *            user to be added.
     */
    public synchronized void addUser(User otherUser) {
        users.add(otherUser);
        this.broadcastSelf();
    };

    /**
     * Remove user from users if it exists. Broadcast a USersListMessage to all
     * users in the CurrentUsers object.
     * 
     * @param user
     *            user to be removed.
     */
    public synchronized void removeUser(User user) {
        users.remove(user);
        this.broadcastSelf();
    };

    /**
     * Send all users a UsersListMessage after add/remove user.
     */
    public synchronized void broadcastSelf() {
        String msg = this.toJSON().toJSONString();
        for (User user : users) {
            user.output(msg);
        }
    };

    /**
     * See JSONable Interface for specs.
     * 
     * Note that CurrentUsers.toJSON() creates a UserListMessage that contains
     * only a list of the String usernames of the users.
     */
    @Override
    public JSONObject toJSON() {
        List<String> usersList = new ArrayList<String>();
        synchronized (this.users) {
            for (User user : this.users) {
                usersList.add(user.getName());
            }
        }
        return new UserListMessage(this.whiteboardID, usersList).toJSON();
    }

    /**
     * See JSONable Interface for specs.
     */
    @Override
    public UserListMessage fromJSON(String jsonString) {
        return UserListMessage.STATIC.fromJSON(jsonString);
    }

    /**
     * See JSONable Interface for specs.
     */
    @Override
    public UserListMessage fromJSON(JSONObject j) {
        return UserListMessage.STATIC.fromJSON(j);
    }

}
