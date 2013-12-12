package message.test;

import junit.framework.TestCase;
import message.DeleteStrokeMessage;

import org.junit.Test;

/**
 * @category no_didit
 * 
 */
public class DeleteStrokeMessageTest extends TestCase {
    @Test
    public void testEncodeDecodeEquality() {
        DeleteStrokeMessage test = new DeleteStrokeMessage(2, "id", "hello");
        assertTrue(test.equals(DeleteStrokeMessage.STATIC.fromJSON(test.toJSON().toJSONString())));
    }

    @Test
    public void testEquality() {
        DeleteStrokeMessage test = new DeleteStrokeMessage(2, "id", "hello");
        DeleteStrokeMessage test2 = new DeleteStrokeMessage(2, "id", "hello");
        assertTrue(test.equals(test2));
        DeleteStrokeMessage test3 = new DeleteStrokeMessage(3, "id", "hello");
        DeleteStrokeMessage test4 = new DeleteStrokeMessage(2, "notid", "hello");
        DeleteStrokeMessage test5 = new DeleteStrokeMessage(2, "id", "nothello");

        assertFalse(test.equals(test3));
        assertFalse(test.equals(test4));
        assertFalse(test.equals(test5));

    }
}
