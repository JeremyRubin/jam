package drawable;

import java.awt.Color;
import java.awt.Graphics2D;

import message.JSONable;

public interface Drawable<T extends Drawable<T>> extends JSONable<T> {
	public void draw(Graphics2D g);

	public Color getColor();

	public int getBrushWidth();
}
