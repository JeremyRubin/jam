package message.test;

import java.awt.Color;

import junit.framework.TestCase;
import message.ToServerStrokeMessage;

import org.junit.Test;

import drawable.DrawableSegment;

/**
 * @category no_didit
 * 
 */
public class ToServerStrokeMessageTest extends TestCase {

    @Test
    public void testEncodeDecodeEquality() {
        ToServerStrokeMessage test = new ToServerStrokeMessage(DrawableSegment.STATIC, "hello");
        assertTrue(test.equals(ToServerStrokeMessage.STATIC.fromJSON(test.toJSON().toJSONString())));
    }

    @Test
    public void testEquality() {
        ToServerStrokeMessage test = new ToServerStrokeMessage(new DrawableSegment(1, 9, 2, 10, Color.RED, 2), "hello");
        ToServerStrokeMessage test2 = new ToServerStrokeMessage(new DrawableSegment(1, 9, 2, 10, Color.RED, 2), "hello");
        assertTrue(test.equals(test2));
        ToServerStrokeMessage test3 = new ToServerStrokeMessage(new DrawableSegment(1, 9, 2, 10, Color.BLUE, 2),
                "hello");
        ToServerStrokeMessage test4 = new ToServerStrokeMessage(new DrawableSegment(1, 9, 2, 10, Color.RED, 2), "as");
        assertFalse(test.equals(test3));
        assertFalse(test.equals(test4));
    }
}
