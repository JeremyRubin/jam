package whiteboard;

import java.util.HashMap;
import java.util.Map;

import drawable.Drawable;

public class WhiteboardClientModel {
	// Drawables that have been acknowledged by the server
	private Map<Integer, Drawable> syncedState = new HashMap<Integer, Drawable>();

	// Drawables that have not been acknowledged by the server yet
	// using a HashMap
	private Map<Integer, Drawable> localState = new HashMap<Integer, Drawable>();

	public WhiteboardClientModel() {

	}

}
