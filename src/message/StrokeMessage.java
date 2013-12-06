package message;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import drawable.Drawable;
import drawable.DrawablePath;

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

	/**
	 * Should only be used to access the fromJSON methods
	 */
	public StrokeMessage() {
		this.id = 0;
		this.userSeqId = 0;
		this.drawable = null;
		this.username = null;
		this.whiteboardID = null;
		this.color = null;
		this.brushWidth = 0;
	}

	public StrokeMessage(int id, int userSeqId, Drawable drawable,
			String username, String whiteboardID, Color color, int brushWidth) {
		this.id = id;
		this.userSeqId = userSeqId;
		this.drawable = drawable;
		this.username = username;
		this.whiteboardID = whiteboardID;
		this.color = color;
		this.brushWidth = brushWidth;
	}

	@Override
	public StrokeMessage fromJSON(String jsonString) {
		return fromJSON((JSONObject) JSONValue.parse(jsonString));
	}

	@Override
	public JSONObject toJSON() {
		Map m = new LinkedHashMap();
		JSONObject j = new JSONObject();
		m.put("id", this.id);
		m.put("userSeqId", this.userSeqId);
		m.put("drawable", this.drawable.toJSON());
		m.put("username", this.username);
		m.put("wb", this.whiteboardID);
		m.put("r", this.color.getRed());
		m.put("g", this.color.getGreen());
		m.put("b", this.color.getBlue());
		m.put("a", this.color.getAlpha());
		m.put("width", this.brushWidth);
		j.putAll(m);
		return j;
	}

	@Override
	public StrokeMessage fromJSON(JSONObject j) {
		Color c = new Color((Integer) j.get("r"), (Integer) j.get("g"),
				(Integer) j.get("b"), (Integer) j.get("a"));
		return new StrokeMessage(((Integer) j.get("id")).intValue(),
				((Integer) j.get("userSeqId")).intValue(),
				new DrawablePath().fromJSON((JSONObject) j.get("drawable")),
				(String) j.get("username"), (String) j.get("wb"), c,
				((Integer) j.get("width")).intValue()

		);
	}

}
