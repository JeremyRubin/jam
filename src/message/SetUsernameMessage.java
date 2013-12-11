package message;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * 
 * The client should send this Message to the server when it wants to update its
 * name.
 * 
 */
public class SetUsernameMessage implements JSONable<SetUsernameMessage> {
    public final static SetUsernameMessage STATIC = new SetUsernameMessage();
    public final String username;

    public SetUsernameMessage(String username) {
        this.username = username;
    }

    private SetUsernameMessage() {
        this.username = null;
    }

    @Override
    public SetUsernameMessage fromJSON(String jsonString) {
        return fromJSON((JSONObject) JSONValue.parse(jsonString));
    }

    @Override
    public SetUsernameMessage fromJSON(JSONObject j) {
        return new SetUsernameMessage((String) j.get("id"));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SetUsernameMessage))
            return false;
        SetUsernameMessage other = (SetUsernameMessage) obj;
        if (this.username.equals(other.username))
            return true;
        else
            return false;

    }

    @Override
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        j.put("id", this.username);
        j.put(Messages.type, this.getClass().getSimpleName());

        return j;
    }
}
