package message;

import drawable.Drawable;

/**
 * 
 * Sub-class of StrokeMessage used so that client implementors can create
 * StrokeMessages with only the appropriate fields, as well as more convenient
 * naming.
 */
public class ToServerStrokeMessage extends StrokeMessage {
    public static final ToServerStrokeMessage STATIC = new ToServerStrokeMessage(null, null);

    public ToServerStrokeMessage(Drawable drawable, String whiteboardID) {
        super(0, drawable, whiteboardID);
    }
}
