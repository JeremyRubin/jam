package message;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class SwitchWhiteboardMessage implements JSONable<SwitchWhiteboardMessage> {
    // unique id for the switch action generated sequentially by user from the
    // same pool as StrokeMessage (which is
    // where they store that in their buffer)
    // client username that drew the Drawable
    public final String whiteboardID;

    public SwitchWhiteboardMessage(String whiteboardID) {
        this.whiteboardID = whiteboardID;
    }

    public SwitchWhiteboardMessage() {
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
        j.put("usid", this.userSeqId);
        j.put(Messages.type, this.getClass().getSimpleName());

        return j;
    }

}
