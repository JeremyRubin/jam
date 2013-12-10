package drawable;

import java.awt.Graphics2D;

import message.JSONable;

/**
 * Drawable interface that represents a drawn shape.
 */
public interface Drawable<T extends Drawable<T>> extends JSONable<T> {

    /**
     * Draw the shape in a Graphics2D object.
     * 
     * @param g
     *            a Graphics2D object to be drawn in.
     */
    public void draw(Graphics2D g);
}
