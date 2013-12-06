package drawable;

import java.awt.Graphics2D;
import java.util.ArrayList;

import message.JSONable;

import org.json.simple.JSONObject;

public class DrawablePath implements Drawable<DrawablePath> {
	private final ArrayList<DrawableSegment> segmentsList;
	/**
	 * Only used to get empty obj for fromJSON
	 */
	public DrawablePath(){
		this.segmentsList = null;
	}
	public DrawablePath(ArrayList<DrawableSegment> segmentsList) {
		this.segmentsList = segmentsList;
	}
	
	@Override 
	public JSONObject toJSON() {
		// TODO Auto-generated method stub
		return null;
	};

	@Override 
	public DrawablePath fromJSON(String data) {
		// TODO Auto-generated method stub
		return null;
	};
	
	@Override 
	public void draw(Graphics2D g) {
		for (DrawableSegment segment : segmentsList) {
			segment.draw(g);
		}
	}

	@Override
	public DrawablePath fromJSON(JSONObject j) {
		// TODO Auto-generated method stub
		return null;
	};
}