package Message;

import java.awt.Color;

import org.json.simple.JSONObject;

import Drawable.Drawable;

public class StrokeMessage implements JSONable<StrokeMessage> {

	private final int id;
	// unique id for the StrokeMessage generated sequentially by server
	private final int userSeqId;
	// unique id for the StrokeMessage generated sequentially by user (which is
	// where they store that in their buffer)
	private final Drawable drawable;
	private final String username;
	// client username that drew the Drawable
	private final String whiteboardID;
	// id of WhiteboardModel
	private final Color color;
	// from http://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
	private final int brushWidth;

	// represents thickness of brush strokes

	public StrokeMessage(int id, int userSeqId, Drawable drawable,
			String username, String whiteboardID, Color color, int brushWidth) {
		this.id =id;
		this.userSeqId = userSeqId;
		this.drawable = drawable;
		this.username = username;
		this.whiteboardID = whiteboardID;
		this.color = color;
		this.brushWidth = brushWidth;
	}

	public JSONObject j() {
		return new JSONObject();
	}

	@Override
	public StrokeMessage fromJSON(String jsonString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

}
