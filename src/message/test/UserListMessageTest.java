package message.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import message.UserListMessage;

import org.junit.Test;

/**
 * @category no_didit
 * 
 */
public class UserListMessageTest extends TestCase {

    @Test
    public void test() {
        List<String> l = new ArrayList<String>();
        l.add("hello");
        l.add("bye");
        UserListMessage u = new UserListMessage("w", l);
        assertTrue(u.equals(UserListMessage.STATIC.fromJSON(u.toJSON().toJSONString())));
    }
}
