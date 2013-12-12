package message;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import drawable.Drawable;
import drawable.DrawableSegment;

/**
 * This message should be used to communicate when something is drawn. It
 * shouldn't really be constructed directly, although it can. (better to use
 * FromServerStrokeMessage and ToServerStrokeMessage)
 * 
 * This message type contains all the information needed to integrate a message
 * on the server or client. Information not needed (ie, id on incoming message)
 * may not be accurate).
 * 
 * Use getDeleteMessage to create a DeleteStrokeMessage corresponding to this
 * message.
 * 
 * Use withServerID to return a StrokeMessage with the appropriately set
 * serverID.
 * 
 * 
 * 
 */
public class StrokeMessage implements JSONable<StrokeMessage> {
    public final static StrokeMessage STATIC = new StrokeMessage();
    // unique id for the StrokeMessage generated sequentially by server
    public final int id;
    // unique id for the StrokeMessage generated sequentially by user (which is
    // where they store that in their buffer)
    public final int userSeqId;
    public final Drawable drawable;
    // client username that drew the Drawable
    public final String username;
    // id of WhiteboardModel
    public final String whiteboardID;

    /**
     * Should only be used to access the fromJSON methods
     */
    private StrokeMessage() {
        this.id = 0;
        this.userSeqId = 0;
        this.drawable = null;
        this.username = null;
        this.whiteboardID = null;
    }

    public StrokeMessage(int id, int userSeqId, Drawable drawable, String username, String whiteboardID) {
        this.id = id;
        this.userSeqId = userSeqId;
        this.drawable = drawable;
        this.username = username;
        this.whiteboardID = whiteboardID;
    }

    public Drawable drawable() {
        return this.drawable;
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
        m.put(Messages.type, this.getClass().getSimpleName());
        j.putAll(m);

        return j;
    }

    /**
     * Useful for generating the StrokeMessage to be broadcasted out.
     * 
     * @param id
     *            the order ID to return with
     * @return a StrokeMessage with a different server ID message.
     */
    public FromServerStrokeMessage withServerID(int id) {
        return new FromServerStrokeMessage(id, this.userSeqId, this.drawable, this.username, this.whiteboardID);
    }

    @Override
    public StrokeMessage fromJSON(JSONObject j) {
        return new StrokeMessage((new BigDecimal((Long) j.get("id"))).intValue(), (new BigDecimal(
                (Long) j.get("userSeqId"))).intValue(),
                DrawableSegment.STATIC.fromJSON((JSONObject) j.get("drawable")), (String) j.get("username"),
                (String) j.get("wb"));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StrokeMessage))
            return false;
        StrokeMessage other = (StrokeMessage) obj;
        if (this.id == other.id && this.userSeqId == other.userSeqId && this.drawable.equals(other.drawable)
                && this.username.equals(other.username) && this.whiteboardID.equals(other.whiteboardID))
            return true;
        else
            return false;
    }

}
