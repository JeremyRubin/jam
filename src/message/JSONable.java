package message;

import org.json.simple.JSONObject;

/**
 * Interface that allows JSON serialization and deserialization.
 * 
 */
public interface JSONable<T extends JSONable<T>> {

    /**
     * Convert a string to the original object type.
     * 
     * @param jsonString
     *            serialized object as a string.
     * @return a variable of the original object type
     */
    public T fromJSON(String jsonString);

    /**
     * Convert a JSONObject to the original object type.
     * 
     * @param j
     *            A JSONObject
     * @return a variable of the original object type
     */
    public T fromJSON(JSONObject j);

    /**
     * Convert the original object into a JSONObject
     * 
     * @return a JSONObject representing the original object
     */
    public JSONObject toJSON();

}
