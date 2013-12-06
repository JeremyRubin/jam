package whiteboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import message.JSONable;
import message.Messages;
import message.NewWhiteboardMessage;
import message.StrokeMessage;
import message.SwitchWhiteboardMessage;
import message.WhiteboardCreatedMessage;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import server.WhiteboardServer;

/**
 * Represents a user.
 * 
 */
public class User implements JSONable<User>, Runnable {

	private String username;
	private final Socket socket;
	private BlockingQueue<JSONable> queue;
	private final WhiteboardServer server;

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

	public void changeName() {
		// TODO: figure out specs
	}

	/**
	 * Add a msg to the queue to be sent out
	 * 
	 * @param msg
	 */
	public void add(String msg) {
		// need to convert msg to JSONable - how do i know which type of
		// JSONable it is?
		// queue.add()
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
			BufferedReader in = new BufferedReader(new InputStreamReader(
					this.socket.getInputStream()));
			PrintWriter out = new PrintWriter(this.socket.getOutputStream(),
					true);
			try {
				for (String line = in.readLine(); line != null; line = in
						.readLine()) {
					String output = handleRequest(line);
					if (output != null) {
						out.println(output);
					}
				}
			} catch (IOException e) {
			} finally {
				out.close();
				in.close();
				this.socket.close();
			}
		} catch (IOException e) {
		}
	}

	private String handleRequest(String input) {
		JSONObject j = (JSONObject) JSONValue.parse(input);
		String action = (String) j.get("action");
		JSONObject data = (JSONObject) j.get("data");
		if (action.equals(Messages.newWhiteboard)) {
			NewWhiteboardMessage s = new NewWhiteboardMessage().fromJSON(data);
			// TODO what should be done with s?
		} else if (action.equals(Messages.stroke)) {
			StrokeMessage s = new StrokeMessage().fromJSON(data);
			// TODO what should be done with s?

		} else if (action.equals(Messages.switchWhiteboard)) {
			SwitchWhiteboardMessage s = new SwitchWhiteboardMessage()
					.fromJSON(data);
			// TODO what should be done with s?

		} else if (action.equals(Messages.whiteboardCreated)) {
			throw new RuntimeException(
					"Server shouldn't recieve WhiteboardCreatedMessage");

		}
		// Should never get here--make sure to return in each of the valid cases
		// above.
		throw new UnsupportedOperationException();
	}
}
