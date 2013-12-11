package message;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * SwitchWhiteboardMessage is sent both from Client and Server.
 * 
 * When the client would like to connect to a specific whiteboard or create a
 * new one with that whiteboard name, this message should be used.
 * 
 * When the server connects a user to a specific whiteboard, this should be sent
 * to the client with the new name.
 * 
 * This or NewWhiteboardMessage should be sent by client before sending any
 * StrokeMessages.
 * 
 */
public class SwitchWhiteboardMessage implements JSONable<SwitchWhiteboardMessage> {
    public final static SwitchWhiteboardMessage STATIC = new SwitchWhiteboardMessage();
    public final String whiteboardID;

    public SwitchWhiteboardMessage(String whiteboardID) {
        this.whiteboardID = whiteboardID;
    }

    private SwitchWhiteboardMessage() {
        this.whiteboardID = null;
    }

    @Override
    public SwitchWhiteboardMessage fromJSON(String jsonString) {
        return fromJSON((JSONObject) JSONValue.parse(jsonString));
    }

    @Override
    public SwitchWhiteboardMessage fromJSON(JSONObject j) {
        // TODO Auto-generated method stub
        return new SwitchWhiteboardMessage((String) j.get("id"));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SwitchWhiteboardMessage))
            return false;
        SwitchWhiteboardMessage other = (SwitchWhiteboardMessage) obj;
        if (this.whiteboardID.equals(other.whiteboardID))
            return true;
        else
            return false;

    }

    @Override
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        j.put("id", this.whiteboardID);
        j.put(Messages.type, this.getClass().getSimpleName());

        return j;
    }

}
