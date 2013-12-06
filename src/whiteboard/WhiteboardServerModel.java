package whiteboard;

import java.util.ArrayList;
import java.util.Map;

import drawable.Drawable;


public class WhiteboardServerModel {
	// unique ID per Whiteboard instance generated from the random combination
	// of 2 or more words from a large dictionary (i.e., correct horse battery
	// staple).
	public final String id;

	// sequence of Drawables
	// smaller indices indicate that the element was drawn earlier
	private ArrayList<Drawable> drawablesList;

	// should keep track of its clients, assigning them their own ID’s and
	// letting them set usernames
	private Map<String, String> clients; // ???

	// the currently connected clients should be maintained here
	private CurrentUsers users;

	public WhiteboardServerModel() {
		this.id = this.randomWordSequenceGenerator();
	};

	private String randomWordSequenceGenerator() {
		// TODO
		return "hello-world-bye";
	}
}
