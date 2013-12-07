package message.test;

import junit.framework.TestCase;
import message.StrokeMessage;

import org.junit.Test;

import drawable.DrawablePath;
import drawable.DrawableSegment;

/**
 * @category no_didit
 * 
 */
public class StrokeMessageTest extends TestCase {

    @Test
    public void testEncodeDecodeEquality() {
        StrokeMessage test = new StrokeMessage(1, 2, new DrawablePath(), "id", "hello");
        assertTrue(test.equals(new StrokeMessage().fromJSON(test.toJSON().toJSONString())));
    }

    @Test
    public void testEquality() {
        StrokeMessage test = new StrokeMessage(1, 2, new DrawablePath(), "id", "hello");
        StrokeMessage test2 = new StrokeMessage(1, 2, new DrawablePath(), "id", "hello");
        assertTrue(test.equals(test2));
        StrokeMessage test3 = new StrokeMessage(0, 2, new DrawablePath(), "id", "hello");
        StrokeMessage test4 = new StrokeMessage(1, 1, new DrawablePath(), "id", "hello");
        StrokeMessage test5 = new StrokeMessage(1, 2, new DrawableSegment(), "id", "hello");
        StrokeMessage test6 = new StrokeMessage(1, 2, new DrawablePath(), "asa", "hello");
        StrokeMessage test7 = new StrokeMessage(1, 2, new DrawablePath(), "id", "as");

        assertFalse(test.equals(test3));
        assertFalse(test.equals(test4));
        assertFalse(test.equals(test6));
        assertFalse(test.equals(test7));
        assertFalse(test.equals(test5));

    }
}
