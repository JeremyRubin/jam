package message;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import whiteboard.User;

public class UserListMessage implements JSONable<UserListMessage> {
    private final List<String> users;
    public final String whiteboardID;

    public UserListMessage(String whiteboardID, List<String> linkedList) {
        this.users = linkedList;
        this.whiteboardID = whiteboardID;
    }

    public UserListMessage() {
        this.users = null;
        this.whiteboardID = null;
    }

    
    public List<String> getUsers() {
        return new ArrayList<String>(this.users);
    }
    
    @Override
    public UserListMessage fromJSON(String jsonString) {
        return fromJSON((JSONObject) JSONValue.parse(jsonString));

    }

    @Override
    public UserListMessage fromJSON(JSONObject j) {
        return new UserListMessage((String) j.get("whiteboardID"), (List<String>) j.get("values"));
    }

    @Override
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        j.put(Messages.type, this.getClass().getSimpleName());
        j.put("values", this.users);
        j.put("whiteboardID", this.whiteboardID);
        return j;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UserListMessage))
            return false;
        UserListMessage other = (UserListMessage) obj;
        if (other.users.equals(this.users) && this.whiteboardID.equals(other.whiteboardID))
            return true;
        else
            return false;
    }
}
