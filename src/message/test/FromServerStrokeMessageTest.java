package message.test;

import java.awt.Color;

import junit.framework.TestCase;
import message.FromServerStrokeMessage;

import org.junit.Test;

import drawable.DrawableSegment;

/**
 * @category no_didit
 * 
 */
public class FromServerStrokeMessageTest extends TestCase {

    @Test
    public void testEncodeDecodeEquality() {
        FromServerStrokeMessage test = new FromServerStrokeMessage(1, 2, DrawableSegment.STATIC, "id", "hello");
        assertTrue(test.equals(FromServerStrokeMessage.STATIC.fromJSON(test.toJSON().toJSONString())));
    }

    @Test
    public void testEquality() {
        FromServerStrokeMessage test = new FromServerStrokeMessage(1, 2,
                new DrawableSegment(1, 9, 2, 10, Color.RED, 2), "id", "hello");
        FromServerStrokeMessage test2 = new FromServerStrokeMessage(1, 2,
                new DrawableSegment(1, 9, 2, 10, Color.RED, 2), "id", "hello");
        assertTrue(test.equals(test2));
        FromServerStrokeMessage test3 = new FromServerStrokeMessage(2, 2, new DrawableSegment(1, 9, 2, 10, Color.BLUE,
                2), "id", "hello");
        FromServerStrokeMessage test4 = new FromServerStrokeMessage(1, 2, new DrawableSegment(1, 9, 2, 10, Color.BLUE,
                2), "id", "hello");
        FromServerStrokeMessage test5 = new FromServerStrokeMessage(1, 1,
                new DrawableSegment(1, 9, 2, 10, Color.RED, 2), "id", "hello");
        FromServerStrokeMessage test6 = new FromServerStrokeMessage(1, 2,
                new DrawableSegment(1, 9, 2, 10, Color.RED, 2), "asa", "hello");
        FromServerStrokeMessage test7 = new FromServerStrokeMessage(1, 2,
                new DrawableSegment(1, 9, 2, 10, Color.RED, 2), "id", "as");

        assertFalse(test.equals(test3));
        assertFalse(test.equals(test4));
        assertFalse(test.equals(test5));
        assertFalse(test.equals(test6));
        assertFalse(test.equals(test7));
    }
}
