package message;

import drawable.Drawable;

public class FromServerStrokeMessage extends StrokeMessage {
    public static final FromServerStrokeMessage STATIC = new FromServerStrokeMessage();

    public FromServerStrokeMessage(int id, int userSeqId, Drawable drawable, String username, String whiteboardID) {
        super(id, userSeqId, drawable, username, whiteboardID);
    }

    private FromServerStrokeMessage() {

    }
}
