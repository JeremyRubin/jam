package message;

import drawable.Drawable;

/**
 * 
 * Sub-class of StrokeMessage used so that client implementors can create
 * StrokeMessages with only the appropriate fields, as well as more convenient
 * naming.
 */
public class ToServerStrokeMessage extends StrokeMessage {
    public static final ToServerStrokeMessage STATIC = new ToServerStrokeMessage(1, null, null, null);

    public ToServerStrokeMessage(int userSeqId, Drawable drawable, String username, String whiteboardID) {
        super(0, userSeqId, drawable, username, whiteboardID);
    }

}
