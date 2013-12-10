package whiteboard.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import server.ConnectedUser;
import server.WhiteboardServer;
import whiteboard.CurrentUsers;
import whiteboard.User;

/**
 * Test suite for methods in CurrentUsers.
 * 
 * 
 * @author Merry
 * 
 */
public class CurrentUsersTest {

    @Test
    public void equalsTest() throws IOException {
        // test the equals and constructor methods
        // assertTrue(d.equals(d1));

        // test the toJSON, fromJSON methods
        User u = new User(new ConnectedUser(new Socket(), new WhiteboardServer(4400)));
        u.setName("HIIIIII");
        List<User> userList = new ArrayList<User>();
        userList.add(u);
        CurrentUsers users = new CurrentUsers("id", userList);
        System.out.println(users);
        assertTrue(users.equals(users));

    }

    @Test
    public void toJSONFromJSONEqualsTest() throws IOException {
        // test the toJSON, fromJSON methods
        //TODO
    }
}
