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
        SwitchWhiteboardMessage test = new SwitchWhiteboardMessage("ocelot", 10);
        assertTrue(test.equals(new SwitchWhiteboardMessage().fromJSON(test.toJSON().toJSONString())));
    }

}
