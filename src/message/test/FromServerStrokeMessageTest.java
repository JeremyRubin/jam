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
        FromServerStrokeMessage test = new FromServerStrokeMessage(1, DrawableSegment.STATIC, "hello");
        assertTrue(test.equals(FromServerStrokeMessage.STATIC.fromJSON(test.toJSON().toJSONString())));
    }

    @Test
    public void testEquality() {
        FromServerStrokeMessage test = new FromServerStrokeMessage(1, new DrawableSegment(1, 9, 2, 10, Color.RED, 2),
                "hello");
        FromServerStrokeMessage test2 = new FromServerStrokeMessage(1, new DrawableSegment(1, 9, 2, 10, Color.RED, 2),
                "hello");
        assertTrue(test.equals(test2));
        FromServerStrokeMessage test3 = new FromServerStrokeMessage(2, new DrawableSegment(1, 9, 2, 10, Color.BLUE, 2),
                "hello");
        FromServerStrokeMessage test4 = new FromServerStrokeMessage(1, new DrawableSegment(1, 9, 2, 10, Color.BLUE, 2),
                "hello");
        FromServerStrokeMessage test7 = new FromServerStrokeMessage(1, new DrawableSegment(1, 9, 2, 10, Color.RED, 2),
                "as");

        assertFalse(test.equals(test3));
        assertFalse(test.equals(test4));
        assertFalse(test.equals(test7));
    }
}
