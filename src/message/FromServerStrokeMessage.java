package message;

import drawable.Drawable;

public class FromServerStrokeMessage extends StrokeMessage {
    public static final FromServerStrokeMessage STATIC = new FromServerStrokeMessage(1, 1, null, null, null);

    public FromServerStrokeMessage(int id, int userSeqId, Drawable drawable, String username, String whiteboardID) {
        super(id, userSeqId, drawable, username, whiteboardID);
    }

}
