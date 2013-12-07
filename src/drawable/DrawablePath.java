package drawable;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class DrawablePath implements Drawable<DrawablePath> {
    private final ArrayList<DrawableSegment> segmentsList;

    /**
     * Only used to get empty obj for fromJSON
     */
    public DrawablePath() {
        this.segmentsList = null;
    }

    public DrawablePath(ArrayList<DrawableSegment> segmentsList) {
        this.segmentsList = segmentsList;
    }

    @Override
    public JSONObject toJSON() {
        Map m = new LinkedHashMap();
        JSONObject j = new JSONObject();
        m.put("segmentsList", this.segmentsList);
        j.putAll(m);
        return j;
    };

    @Override
    public DrawablePath fromJSON(String data) {
        return fromJSON((JSONObject) JSONValue.parse(data));
    };

    @Override
    public DrawablePath fromJSON(JSONObject j) {
        // TODO Properly deserialize
        return null;
    };

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DrawablePath))
            return false;
        // TODO proper equals function, assume true for now.
        return true;
    }

    @Override
    public void draw(Graphics2D g) {
        for (DrawableSegment segment : segmentsList) {
            segment.draw(g);
        }
    }

}