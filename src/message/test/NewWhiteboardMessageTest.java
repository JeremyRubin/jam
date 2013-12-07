package message.test;

import junit.framework.TestCase;
import message.NewWhiteboardMessage;

import org.junit.Test;

/**
 * @category no_didit
 * 
 */
public class NewWhiteboardMessageTest extends TestCase {

    @Test
    public void test() {
        NewWhiteboardMessage test = new NewWhiteboardMessage();
        assertTrue(test.equals(new NewWhiteboardMessage().fromJSON(test.toJSON().toJSONString())));
    }

}
