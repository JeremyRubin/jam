package Drawable;

import java.awt.Graphics2D;

import Message.JSONable;

public interface Drawable<T extends Drawable<T>> extends JSONable<T> {
	public void draw(Graphics2D g);
}
