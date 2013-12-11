package message;

//try to fix
/**
 * 
 * The Messages Class serves as a kind of "enum", which has name strings for
 * each message.
 */
public final class Messages {
    public final static String fromServerStroke = FromServerStrokeMessage.STATIC.getClass().getSimpleName();
    public final static String toServerStroke = ToServerStrokeMessage.STATIC.getClass().getSimpleName();
    public final static String newWhiteboard = NewWhiteboardMessage.STATIC.getClass().getSimpleName();
    public final static String switchWhiteboard = SwitchWhiteboardMessage.STATIC.getClass().getSimpleName();
    public final static String whiteboardCreated = WhiteboardCreatedMessage.STATIC.getClass().getSimpleName();
    public final static String setUsernameMessage = SetUsernameMessage.STATIC.getClass().getSimpleName();
    public final static String currentUsers = UserListMessage.STATIC.getClass().getSimpleName();
    public final static String deleteStrokeMessage = DeleteStrokeMessage.STATIC.getClass().getSimpleName();
    public final static String type = "type";
}
