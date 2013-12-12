package message.test;

import junit.framework.TestCase;
import message.StrokeMessage;

import org.junit.Test;

import drawable.DrawableSegment;

/**
 * @category no_didit
 * 
 */
public class StrokeMessageTest extends TestCase {

    @Test
    public void testEncodeDecodeEquality() {
        StrokeMessage test = new StrokeMessage(1, DrawableSegment.STATIC, "hello");
        assertTrue(test.equals(StrokeMessage.STATIC.fromJSON(test.toJSON().toJSONString())));
    }

    @Test
    public void testEquality() {
        StrokeMessage test = new StrokeMessage(1, DrawableSegment.STATIC, "hello");
        StrokeMessage test2 = new StrokeMessage(1, DrawableSegment.STATIC, "hello");
        assertTrue(test.equals(test2));
        StrokeMessage test3 = new StrokeMessage(0, DrawableSegment.STATIC, "hello");
        StrokeMessage test4 = new StrokeMessage(1, DrawableSegment.STATIC, "as");
        assertFalse(test.equals(test3));
        assertFalse(test.equals(test4));
    }

    @Test
    public void testUpdateId() {
        StrokeMessage test = new StrokeMessage(1, DrawableSegment.STATIC, "hello");
        StrokeMessage test2 = new StrokeMessage(10, DrawableSegment.STATIC, "hello");

        assertTrue(test.withServerID(10).equals(test2));
    }
}
