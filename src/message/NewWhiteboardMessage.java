package message;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * NewWhiteboardMessage is sent only from Client to Server, when the client
 * would like to connect to a new whiteboard without regard for the whiteboard
 * name.
 * 
 * The server should respond with a SwitchWhiteboardMessage.
 * 
 * This or SwitchWhiteboardMessage should be sent by client before sending any
 * StrokeMessages.
 * 
 */
public class NewWhiteboardMessage implements JSONable<NewWhiteboardMessage> {

    public static final NewWhiteboardMessage STATIC = new NewWhiteboardMessage();

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
        JSONObject j = new JSONObject();
        j.put(Messages.type, this.getClass().getSimpleName());
        return j;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NewWhiteboardMessage))
            return false;
        else
            return true;
    }
}
