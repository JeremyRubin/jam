package message;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class SwitchWhiteboardMessage implements
		JSONable<SwitchWhiteboardMessage> {

	@Override
	public SwitchWhiteboardMessage fromJSON(String jsonString) {
		return fromJSON((JSONObject) JSONValue.parse(jsonString));
	}

	@Override
	public SwitchWhiteboardMessage fromJSON(JSONObject j) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

}
