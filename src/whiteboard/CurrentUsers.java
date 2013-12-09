package whiteboard;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import message.StrokeMessage;

import org.json.simple.JSONObject;

/**
 * Represents the collection of current users connected to a certain whiteboard.
 * 
 */
public class CurrentUsers {
	// all currently logged in users
	private final List<User> users = new ArrayList<User>();

	// a monotonically increasing field in case msgs arrive out of order
	// private String timestamp; // ????

	private final String whiteboardID;

	public CurrentUsers(String whiteboardID) {
		this.whiteboardID = whiteboardID;
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
			user.add(msg);
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
			user.add(this.toJSON().toJSONString());
		}
	};

	/**
	 * Only handles names of users
	 */
	public JSONObject toJSON() {
		Map m = new LinkedHashMap();
		JSONObject j = new JSONObject();
		m.put("wb", this.whiteboardID);

		LinkedList usersListJSON = new LinkedList();
		for (User user : this.users) {
			usersListJSON.add(user);
		}
		m.put("users", usersListJSON);
		j.putAll(m);
		return j;
	}

}
