package message;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * 
 * A message to send when a new whiteboard gets created. (should be treated
 * similarly to a SwitchWhiteboardMessage on client)
 */
public class WhiteboardCreatedMessage implements JSONable<WhiteboardCreatedMessage> {
    public final static WhiteboardCreatedMessage STATIC = new WhiteboardCreatedMessage();

    public final String whiteboardID;

    public WhiteboardCreatedMessage(String whiteboardID) {
        this.whiteboardID = whiteboardID;
    }

    /**
     * Constructor to access static methods
     */
    private WhiteboardCreatedMessage() {
        this.whiteboardID = null;
    }

    @Override
    public WhiteboardCreatedMessage fromJSON(String jsonString) {
        return fromJSON((JSONObject) JSONValue.parse(jsonString));
    }

    @Override
    public WhiteboardCreatedMessage fromJSON(JSONObject j) {
        return new WhiteboardCreatedMessage((String) j.get("name"));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof WhiteboardCreatedMessage))
            return false;
        WhiteboardCreatedMessage other = (WhiteboardCreatedMessage) obj;
        if (this.whiteboardID.equals(other.whiteboardID))
            return true;
        else
            return false;

    }

    @Override
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        j.put("name", this.whiteboardID);
        j.put(Messages.type, this.getClass().getSimpleName());

        return j;
    }
}
