package drawable.test;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import drawable.DrawableSegment;

/**
 * Test suite for methods in DrawableSegment.
 * 
 * We unit test the constructors, equals, toJSON, fromJSON methods. We will
 * manually test the draw method.
 * 
 * @author Merry
 * 
 */
public class DrawableSegmentTest {

    private static final DrawableSegment d = new DrawableSegment(0, 0, 0, 0, Color.BLACK, 1);
    private static final DrawableSegment d1 = new DrawableSegment(0, 0, 0, 0, Color.BLACK, 1);
    private static final DrawableSegment d2 = new DrawableSegment(0, 0, 0, 0);
    private static final DrawableSegment d3 = new DrawableSegment(1, 9, 2, 10, Color.RED, 2);
    private static final DrawableSegment d4 = new DrawableSegment(1, 9, 2, 10);

    @Test
    public void equalsTest() {
        // test the equals and constructor methods
        assertTrue(d.equals(d1));
        assertTrue(d1.equals(d));
        assertTrue(d.equals(d2));
        assertTrue(d1.equals(d2));
        assertTrue(!d.equals(d3));
        assertTrue(!d4.equals(d3));
    }

    @Test
    public void toJSONFromJSONEqualsTest() {
        // test the toJSON, fromJSON methods        
        assertTrue(d.equals(DrawableSegment.STATIC.fromJSON(d.toJSON().toJSONString())));
        assertTrue(d2.equals(DrawableSegment.STATIC.fromJSON(d.toJSON().toJSONString())));
        assertTrue(d.equals(DrawableSegment.STATIC.fromJSON(d2.toJSON().toJSONString())));
        assertTrue(!d3.equals(DrawableSegment.STATIC.fromJSON(d.toJSON().toJSONString())));
        assertTrue(!d.equals(DrawableSegment.STATIC.fromJSON(d3.toJSON().toJSONString())));
        assertTrue(!d1.equals(DrawableSegment.STATIC.fromJSON(d3.toJSON().toJSONString())));
        assertTrue(!d2.equals(DrawableSegment.STATIC.fromJSON(d3.toJSON().toJSONString())));
        assertTrue(!d.equals(DrawableSegment.STATIC.fromJSON(d4.toJSON().toJSONString())));
        assertTrue(!d3.equals(DrawableSegment.STATIC.fromJSON(d4.toJSON().toJSONString())));
        assertTrue(!d4.equals(DrawableSegment.STATIC.fromJSON(d3.toJSON().toJSONString())));
    }
}
