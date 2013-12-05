package whiteboard;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import org.json.simple.JSONObject;

import Message.JSONable;

/**
 * Represents a user.
 * 
 */
public class User implements JSONable<User> {

	private String username;	
	private final Socket socket;
	private BlockingQueue<JSONable> queue;

	public User(String name, Socket socket) {
		this.username = name;
		this.socket = socket;
	}

	public String getName() {
		return this.username;
	}

	public void changeName() {
		//TODO: figure out specs
	}
	
	/**
	 * Add a msg to the queue to be sent out
	 * 
	 * @param msg
	 */
	public void add(String msg) {
		// need to convert msg to JSONable - how do i know which type of JSONable it is?
		//queue.add()
	}

	/**
	 * start the User thread
	 */
	public void start() {
		// start a thread?
		// what should the run method be?
		// how does this function work w/ WhiteboardServer?
		// ????
	}

	@Override
	public User fromJSON(String jsonString) {
		// TODO Auto-generated method stub
		return null;
	};

	@Override
	public JSONObject toJSON() {
		// TODO Auto-generated method stub
		return null;
	};
}