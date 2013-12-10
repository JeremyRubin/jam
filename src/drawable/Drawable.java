package drawable;

import java.awt.Graphics2D;

import message.JSONable;

/**
 * 
 * @author Merry
 *
 * @param <T>
 */
public interface Drawable<T extends Drawable<T>> extends JSONable<T> {
    public void draw(Graphics2D g);
}
