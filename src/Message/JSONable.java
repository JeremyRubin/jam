package Message;

import org.json.simple.JSONObject;

public interface JSONable<T extends JSONable<T>> {
	public T fromJSON(String jsonString);
	public JSONObject toJSON();

}
