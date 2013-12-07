package message;

import java.math.BigDecimal;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class SwitchWhiteboardMessage implements JSONable<SwitchWhiteboardMessage> {
    private final int userSeqId;
    // unique id for the switch action generated sequentially by user from the
    // same pool as StrokeMessage (which is
    // where they store that in their buffer)
    // client username that drew the Drawable
    private final String whiteboardID;

    public SwitchWhiteboardMessage(String whiteboardID, int userSeqId) {
        this.userSeqId = userSeqId;
        this.whiteboardID = whiteboardID;
    }

    public SwitchWhiteboardMessage() {
        this.userSeqId = 0;
        this.whiteboardID = null;
    }

    @Override
    public SwitchWhiteboardMessage fromJSON(String jsonString) {
        return fromJSON((JSONObject) JSONValue.parse(jsonString));
    }

    @Override
    public SwitchWhiteboardMessage fromJSON(JSONObject j) {
        // TODO Auto-generated method stub
        return new SwitchWhiteboardMessage((String) j.get("id"), new BigDecimal((Long) j.get("usid")).intValue());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SwitchWhiteboardMessage))
            return false;
        SwitchWhiteboardMessage other = (SwitchWhiteboardMessage) obj;
        if (this.whiteboardID.equals(other.whiteboardID) && this.userSeqId == other.userSeqId)
            return true;
        else
            return false;

    }

    @Override
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        j.put("id", this.whiteboardID);
        j.put("usid", this.userSeqId);
        return j;
    }

}
