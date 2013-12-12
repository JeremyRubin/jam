package message;

import java.math.BigDecimal;

import org.json.simple.JSONObject;

import drawable.Drawable;
import drawable.DrawableSegment;

/**
 * 
 * Sub-class of StrokeMessage used so that client implementors can create
 * StrokeMessages for sending to the Client with only the appropriate fields, as
 * well as more convenient naming.
 */
public class FromServerStrokeMessage extends StrokeMessage {
    public static final FromServerStrokeMessage STATIC = new FromServerStrokeMessage(1, null, null);

    public FromServerStrokeMessage(int id, Drawable drawable, String whiteboardID) {
        super(id, drawable, whiteboardID);
    }

    @Override
    public FromServerStrokeMessage fromJSON(JSONObject j) {
        return new FromServerStrokeMessage((new BigDecimal((Long) j.get("id"))).intValue(),
                DrawableSegment.STATIC.fromJSON((JSONObject) j.get("drawable")), (String) j.get("wb"));
    }
}
