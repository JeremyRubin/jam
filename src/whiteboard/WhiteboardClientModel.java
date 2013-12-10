package whiteboard;

import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import message.StrokeMessage;
import drawable.Drawable;

public class WhiteboardClientModel {

	public WhiteboardServerModel serverModel;

	// Drawables that have been acknowledged by the server
	private Map<Integer, Drawable> syncedState = new HashMap<Integer, Drawable>();

	// Drawables that have not been acknowledged by the server yet
	// using a HashMap
	private Map<Integer, Drawable> localState = new HashMap<Integer, Drawable>();

		
	public WhiteboardClientModel(WhiteboardServerModel model) {
		this.serverModel= model;
	}

	/**
	 * After the user draws onto the GUI, handleDraw adds the Drawable to the
	 * localState, then sends a StrokeMessage to the WhiteboardServerModel.
	 * 
	 * @param d
	 *            Drawable that user drew
	 */
	public void handleDrawable(int id, int userSeqId, Drawable d, String username,
			String whiteboardID) {
		StrokeMessage s = new StrokeMessage(id, userSeqId, d, username,
				whiteboardID);
		localState.put(localState.size(), d); // what should the key be?
		serverModel.handleDrawable(s);
	}

	/**
	 * return the current contents of the buffer (to be called after all objects
	 * in the buffer have been drawn) as an Image
	 * 
	 * @return
	 */
	public Image displayBuffer() {
		// TODO
		return null;
	}

	/**
	 * render what the buffer should (not will immediately) show in background
	 * Synchronized on something so that they are done in proper order process
	 * synced then local state
	 */
	public void drawBuffer() {
		// TODO
	}

	/**
	 * The global state has assigned a certain index for the board, so add it to
	 * syncedState and remove from localState
	 * 
	 * @param syncedTo
	 * @param local
	 * @param drawable
	 */
	public void addDrawableAt(int syncedTo, int local, Drawable drawable) {

	}
}
