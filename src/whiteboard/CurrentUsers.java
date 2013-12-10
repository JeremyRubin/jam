package whiteboard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import message.JSONable;
import message.Messages;
import message.UserListMessage;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Represents the collection of current users connected to a certain whiteboard.
 * 
 */
public class CurrentUsers implements JSONable<UserListMessage> {
    // all currently logged in users
    // FIXME this seems really bad
    private List<User> users = new ArrayList<User>();

    // a monotonically increasing field in case msgs arrive out of order
    // private String timestamp; // ????

    private final String whiteboardID;

    /**
     * Use this constructor only in Messages
     */
    public CurrentUsers() {
        this.whiteboardID = null;
    }

    public CurrentUsers(String whiteboardID) {
        this.whiteboardID = whiteboardID;
    }

    public CurrentUsers(String whiteboardID, List<User> users) {
        this.whiteboardID = whiteboardID;
        this.users = users;
    }

    /**
     * Update users to include otherUser. We allow duplicate usernames in a
     * single CurrentUsers object.
     */
    public synchronized void addUser(User otherUser) {
        users.add(otherUser);
    };

    /**
     * Send messages to all CurrentUsers.
     */
    public void broadcast(String msg) {
        for (User user : users) {
            user.input(msg);
        }
    };

    /**
     * Remove user from users if it exists.
     */
    public synchronized void removeUser(User user) {
        users.remove(user);
    };

    /**
     * Send all users a CurrentUsersMessage after add/remove user
     */
    public synchronized void broadcastSelf() {
        for (User user : users) {
            user.input(this.toJSON().toJSONString());
        }
    };

    /**
     * Only handles names of users
     */
    @Override
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        j.put("wb", this.whiteboardID);

        LinkedList usersListJSON = new LinkedList();
        for (User user : this.users) {
            usersListJSON.add(user);
        }
        j.put("users", usersListJSON);
        j.put(Messages.type, this.getClass().getSimpleName());
        return j;
    }

    @Override
    public UserListMessage fromJSON(String jsonString) {
        return fromJSON((JSONObject) JSONValue.parse(jsonString));
    }

    @Override
    public UserListMessage fromJSON(JSONObject j) {
        return new UserListMessage((String) j.get("wb"), (LinkedList<User>) j.get("users"));
    }

}
