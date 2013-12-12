package message.test;

import junit.framework.TestCase;
import message.DeleteStrokeMessage;
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
        StrokeMessage test = new StrokeMessage(1, 2, DrawableSegment.STATIC, "id", "hello");
        assertTrue(test.equals(StrokeMessage.STATIC.fromJSON(test.toJSON().toJSONString())));
    }

    @Test
    public void testEquality() {
        StrokeMessage test = new StrokeMessage(1, 2, DrawableSegment.STATIC, "id", "hello");
        StrokeMessage test2 = new StrokeMessage(1, 2, DrawableSegment.STATIC, "id", "hello");
        assertTrue(test.equals(test2));
        StrokeMessage test3 = new StrokeMessage(0, 2, DrawableSegment.STATIC, "id", "hello");
        StrokeMessage test4 = new StrokeMessage(1, 1, DrawableSegment.STATIC, "id", "hello");
        StrokeMessage test5 = new StrokeMessage(1, 2, DrawableSegment.STATIC, "id", "hello");
        StrokeMessage test6 = new StrokeMessage(1, 2, DrawableSegment.STATIC, "asa", "hello");
        StrokeMessage test7 = new StrokeMessage(1, 2, DrawableSegment.STATIC, "id", "as");
        assertFalse(test.equals(test3));
        assertFalse(test.equals(test4));
        assertFalse(test.equals(test6));
        assertFalse(test.equals(test7));
        assertTrue(test.equals(test5));

    }

    @Test
    public void testUpdateId() {
        StrokeMessage test = new StrokeMessage(1, 2, DrawableSegment.STATIC, "id", "hello");
        StrokeMessage test2 = new StrokeMessage(10, 2, DrawableSegment.STATIC, "id", "hello");

        assertTrue(test.withServerID(10).equals(test2));
    }

    @Test
    public void testDeleteMessageCreator() {
        StrokeMessage test = new StrokeMessage(1, 2, DrawableSegment.STATIC, "id", "hello");
        DeleteStrokeMessage test1 = new DeleteStrokeMessage(2, "id", "hello");

        assertTrue(test.getDeleteMessage().equals(test1));
    }
}
