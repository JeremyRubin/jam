package message;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class NewWhiteboardMessage implements JSONable<NewWhiteboardMessage> {

    @Override
    public NewWhiteboardMessage fromJSON(String jsonString) {
        return fromJSON((JSONObject) JSONValue.parse(jsonString));

    }

    @Override
    public NewWhiteboardMessage fromJSON(JSONObject j) {
        return new NewWhiteboardMessage();
    }

    @Override
    public JSONObject toJSON() {
        return new JSONObject();
    }
}
