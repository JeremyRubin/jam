package message;

import java.math.BigDecimal;

import org.json.simple.JSONObject;

import drawable.Drawable;
import drawable.DrawableSegment;

public class FromServerStrokeMessage extends StrokeMessage {
    public static final FromServerStrokeMessage STATIC = new FromServerStrokeMessage(1, 1, null, null, null);

    public FromServerStrokeMessage(int id, int userSeqId, Drawable drawable, String username, String whiteboardID) {
        super(id, userSeqId, drawable, username, whiteboardID);
    }

    @Override
    public FromServerStrokeMessage fromJSON(JSONObject j) {
        return new FromServerStrokeMessage((new BigDecimal((Long) j.get("id"))).intValue(), (new BigDecimal(
                (Long) j.get("userSeqId"))).intValue(),
                DrawableSegment.STATIC.fromJSON((JSONObject) j.get("drawable")), (String) j.get("username"),
                (String) j.get("wb"));
    }
}
