package message;


//try to fix
/**
 * 
 * Essentially just an enum for some method names
 */
public final class Messages {
    public final static String stroke = new StrokeMessage().getClass().getSimpleName();
    public final static String newWhiteboard = new NewWhiteboardMessage().getClass().getSimpleName();
    public final static String switchWhiteboard = new SwitchWhiteboardMessage().getClass().getSimpleName();
    public final static String whiteboardCreated = new WhiteboardCreatedMessage().getClass().getSimpleName();
    public final static String setUsernameMessage = new SetUsernameMessage().getClass().getSimpleName();
    public final static String currentUsers = new UserListMessage().getClass().getSimpleName();

    public final static String type = "type";
}
