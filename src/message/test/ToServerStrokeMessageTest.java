package message.test;

import junit.framework.TestCase;
import message.ToServerStrokeMessage;

import org.junit.Test;

import drawable.DrawableSegment;

public class ToServerStrokeMessageTest extends TestCase {

    @Test
    public void testEncodeDecodeEquality() {
        ToServerStrokeMessage test = new ToServerStrokeMessage(2, DrawableSegment.STATIC, "id", "hello");
        assertTrue(test.equals(ToServerStrokeMessage.STATIC.fromJSON(test.toJSON().toJSONString())));
    }

    @Test
    public void testEquality() {
        ToServerStrokeMessage test = new ToServerStrokeMessage(2, DrawableSegment.STATIC, "id", "hello");
        ToServerStrokeMessage test2 = new ToServerStrokeMessage(2, DrawableSegment.STATIC, "id", "hello");
        assertTrue(test.equals(test2));
        ToServerStrokeMessage test3 = new ToServerStrokeMessage(2, DrawableSegment.STATIC, "id", "hello");
        ToServerStrokeMessage test4 = new ToServerStrokeMessage(1, DrawableSegment.STATIC, "id", "hello");
        ToServerStrokeMessage test5 = new ToServerStrokeMessage(2, DrawableSegment.STATIC, "asa", "hello");
        ToServerStrokeMessage test6 = new ToServerStrokeMessage(2, DrawableSegment.STATIC, "id", "as");

        assertFalse(test.equals(test3));
        assertFalse(test.equals(test4));
        assertFalse(test.equals(test5));
        assertFalse(test.equals(test6));
    }

}
