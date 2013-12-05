package Drawable;

import java.awt.Graphics2D;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import Message.JSONable;

public class DrawablePath implements Drawable, JSONable<DrawablePath> {
	private final ArrayList<DrawableSegment> segmentsList;
	
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
	};
}