package Drawable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import org.json.simple.JSONObject;

import Message.JSONable;

public class DrawableSegment implements Drawable, JSONable<DrawablePath> {
	private final int x1;
	private final int y1;
	private final int x2;
	private final int y2;

	public DrawableSegment(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	};

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
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(1.0f));
		g.drawLine(x1, y1, x2, y2);

		// need to deal with this - IMPORTANT! every time we draw on the internal drawing buffer, we
		// have to notify Swing to repaint this component on the screen.
		// JPanel.repaint();
	};
}