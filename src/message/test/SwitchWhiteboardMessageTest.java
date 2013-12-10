package message.test;

import junit.framework.TestCase;
import message.SwitchWhiteboardMessage;

import org.junit.Test;

/**
 * @category no_didit
 * 
 */
public class SwitchWhiteboardMessageTest extends TestCase {

    @Test
    public void test() {
        SwitchWhiteboardMessage test = new SwitchWhiteboardMessage("ocelot");
        assertTrue(test.equals(SwitchWhiteboardMessage.STATIC.fromJSON(test.toJSON().toJSONString())));
    }
}
