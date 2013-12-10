package message.test;

import junit.framework.TestCase;
import message.FromServerStrokeMessage;

import org.junit.Test;

import drawable.DrawableSegment;

public class FromServerStrokeMessageTest extends TestCase {

    @Test
    public void testEncodeDecodeEquality() {
        FromServerStrokeMessage test = new FromServerStrokeMessage(1, 2, DrawableSegment.STATIC, "id", "hello");
        assertTrue(test.equals(FromServerStrokeMessage.STATIC.fromJSON(test.toJSON().toJSONString())));
    }

    @Test
    public void testEquality() {
        FromServerStrokeMessage test = new FromServerStrokeMessage(1, 2, DrawableSegment.STATIC, "id", "hello");
        FromServerStrokeMessage test2 = new FromServerStrokeMessage(1, 2, DrawableSegment.STATIC, "id", "hello");
        assertTrue(test.equals(test2));
        FromServerStrokeMessage test3 = new FromServerStrokeMessage(0, 2, DrawableSegment.STATIC, "id", "hello");
        FromServerStrokeMessage test4 = new FromServerStrokeMessage(1, 1, DrawableSegment.STATIC, "id", "hello");
        FromServerStrokeMessage test5 = new FromServerStrokeMessage(1, 2, DrawableSegment.STATIC, "id", "hello");
        FromServerStrokeMessage test6 = new FromServerStrokeMessage(1, 2, DrawableSegment.STATIC, "asa", "hello");
        FromServerStrokeMessage test7 = new FromServerStrokeMessage(1, 2, DrawableSegment.STATIC, "id", "as");
        System.out.println(test7.getClass().getSimpleName());

        assertFalse(test.equals(test3));
        assertFalse(test.equals(test4));
        assertFalse(test.equals(test6));
        assertFalse(test.equals(test7));
        assertFalse(test.equals(test5));

    }

}
