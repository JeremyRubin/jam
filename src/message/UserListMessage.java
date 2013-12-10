package message;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class UserListMessage implements JSONable<UserListMessage> {
    private final List<String> users;
    private final String wb;

    public UserListMessage(String wb, List<String> users) {
        this.users = users;
        this.wb = wb;
    }

    public UserListMessage() {
        this.users = null;
        this.wb = null;
    }

    @Override
    public UserListMessage fromJSON(String jsonString) {
        return fromJSON((JSONObject) JSONValue.parse(jsonString));

    }

    @Override
    public UserListMessage fromJSON(JSONObject j) {
        return new UserListMessage((String) j.get("wb"), (List<String>) j.get("values"));
    }

    @Override
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        j.put(Messages.type, this.getClass().getSimpleName());
        j.put("values", this.users);
        j.put("wb", this.wb);
        return j;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UserListMessage))
            return false;
        UserListMessage other = (UserListMessage) obj;
        if (other.users.equals(this.users))
            return true;
        else
            return false;
    }
}
