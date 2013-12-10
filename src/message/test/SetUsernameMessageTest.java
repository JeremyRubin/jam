package message.test;

import junit.framework.TestCase;
import message.SetUsernameMessage;

import org.junit.Test;

public class SetUsernameMessageTest extends TestCase {

    @Test
    public void test() {
        SetUsernameMessage test = new SetUsernameMessage("ocelot");
        SetUsernameMessage test2 = new SetUsernameMessage("tiger");
        assertTrue(test.equals(SetUsernameMessage.STATIC.fromJSON(test.toJSON().toJSONString())));
        assertFalse(test.equals(test2));
    }
}
