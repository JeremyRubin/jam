package message;

import org.json.simple.JSONObject;

public interface JSONable<T extends JSONable<T>> {
	public T fromJSON(String jsonString);

	public T fromJSON(JSONObject j);

	public JSONObject toJSON();

}
