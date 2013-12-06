package whiteboard;

import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONObject;

/**
 * Represents the collection of current users connected to a certain whiteboard.
 * 
 */
public class CurrentUsers {
	// all currently logged in users
	// why does this need to be a hashmap? just store a set of Users?
	// FIXME: Users aren't hashable/behavior not well defined. 
	// set vs list - does order of connecting matter??
	private final Set<User> users = new HashSet<User>();

	// a monotonically increasing field in case msgs arrive out of order
	private String timestamp;

	private String whiteboardID;

	/**
	 * Update users accordingly if name is not already in users. Else don’t do
	 * anything.
	 */
	public void addUser(User otherUser) {
	};

	/**
	 * Send messages to all CurrentUsers.
	 */
	public void broadcast(String msg) {
	};

	/**
	 * Update users accordingly if name is in users. Else don’t do anything.
	 */
	public void removeUser(String name) {
	};

	/**
	 * Send all users a CurrentUsersMessage after add/remove user Sync on users
	 */
	public void broadcastSelf() {
	};

	/**
	 * Only handles names of users
	 */
	public JSONObject toJSON() {
		// TODO Auto-generated method stub		
		return null;

	}

}
