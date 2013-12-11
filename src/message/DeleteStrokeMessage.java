package message;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * TODO WRITE TEST CASES PLS
 * 
 * @author jeremyrubin
 * 
 */

/**
 * A Message to send to the client which initially sent in a StrokeMessage, to
 * let it know that it can delete a message from its localState.
 * 
 */
public class DeleteStrokeMessage implements JSONable<DeleteStrokeMessage> {
    public final static DeleteStrokeMessage STATIC = new DeleteStrokeMessage();
    // unique id for the StrokeMessage generated sequentially by user (which is
    // where they store that in their buffer)
    private final int userSeqId;

    // client username that drew the Drawable
    private final String username;

    // id of WhiteboardModel
    private final String whiteboardID;

    /**
     * Should only be used to access the fromJSON methods
     */
    private DeleteStrokeMessage() {
        this.userSeqId = 0;
        this.username = null;
        this.whiteboardID = null;
    }

    public DeleteStrokeMessage(int userSeqId, String username, String whiteboardID) {
        this.userSeqId = userSeqId;
        this.username = username;
        this.whiteboardID = whiteboardID;
    }

    @Override
    public DeleteStrokeMessage fromJSON(String jsonString) {
        return fromJSON((JSONObject) JSONValue.parse(jsonString));
    }

    @Override
    public JSONObject toJSON() {
        Map m = new LinkedHashMap();
        JSONObject j = new JSONObject();
        m.put("userSeqId", this.userSeqId);
        m.put("username", this.username);
        m.put("wb", this.whiteboardID);
        m.put(Messages.type, this.getClass().getSimpleName());
        j.putAll(m);

        return j;
    }

    @Override
    public DeleteStrokeMessage fromJSON(JSONObject j) {
        return new DeleteStrokeMessage((new BigDecimal((Long) j.get("userSeqId"))).intValue(),
                (String) j.get("username"), (String) j.get("wb"));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DeleteStrokeMessage))
            return false;
        DeleteStrokeMessage other = (DeleteStrokeMessage) obj;
        if (this.userSeqId == other.userSeqId && this.username.equals(other.username)
                && this.whiteboardID.equals(other.whiteboardID))
            return true;
        else
            return false;
    }

}
