package message;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class WhiteboardCreatedMessage implements
		JSONable<WhiteboardCreatedMessage> {
	private final String whiteboardID;

	public WhiteboardCreatedMessage(String whiteboardID) {
		this.whiteboardID = whiteboardID;
	}

	@Override
	public WhiteboardCreatedMessage fromJSON(String jsonString) {
		return fromJSON((JSONObject) JSONValue.parse(jsonString));
	}

	@Override
	public WhiteboardCreatedMessage fromJSON(JSONObject j) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

}
