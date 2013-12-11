package message;

import org.json.simple.JSONObject;

/**
 * Interface that allows JSON serialization and de-serialization.
 * 
 */
public interface JSONable<T extends JSONable<T>> {

    /**
     * Convert a string to the original object type. (passes to
     * fromJSON(JSONObject j). Caller must check that the String can be
     * de-serialzed to this type.
     * 
     * @param jsonString
     *            serialized object as a string.
     * @return a variable of the <T> object type
     */
    public T fromJSON(String jsonString);

    /**
     * Convert a JSONObject to the <T> object type. Caller must check that the
     * String can be de-serialzed to this type.
     * 
     * @param j
     *            A JSONObject
     * @return a <T> of the original object type
     */
    public T fromJSON(JSONObject j);

    /**
     * Convert the original object into a JSONObject.
     * 
     * @return a JSONObject representing the original object
     */
    public JSONObject toJSON();

}
