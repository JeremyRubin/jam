package message.test;

import junit.framework.TestCase;
import message.StrokeMessage;
import message.WhiteboardCreatedMessage;

import org.junit.Test;

public class WhiteboardCreatedMessageTest extends TestCase {

    @Test
    public void testEquals() {
        WhiteboardCreatedMessage m = new WhiteboardCreatedMessage("test");
        assertTrue(m.equals(new WhiteboardCreatedMessage().fromJSON(m.toJSON().toJSONString())));
        assertFalse(m.equals(new WhiteboardCreatedMessage("bad")));
        assertFalse(m.equals(new StrokeMessage()));

    }
}
