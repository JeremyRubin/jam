package server.test;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.LinkedList;

import junit.framework.TestCase;
import message.FromServerStrokeMessage;
import message.NewWhiteboardMessage;
import message.SetUsernameMessage;
import message.StrokeMessage;
import message.SwitchWhiteboardMessage;
import message.ToServerStrokeMessage;
import message.UserListMessage;

import org.junit.Test;

import drawable.DrawableSegment;

public class WhiteboardServerTest extends TestCase {

    /**
     * WhiteboardServer test, "the most complete test"
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    @Test(timeout = 1000000)
    public void test() throws IOException, InterruptedException {
        TestUtil.startServer("4445");
        // Avoid race where we try to connect to server too early
        Thread.sleep(100);
        try {
            Socket sock = TestUtil.connect(4445);
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);

            String setup = new NewWhiteboardMessage().toJSON().toJSONString();

            out.println(setup);
            String wbData = TestUtil.nextNonEmptyLine(in);
            String oldUserList = TestUtil.nextNonEmptyLine(in);
            String wb = SwitchWhiteboardMessage.STATIC.fromJSON(wbData).whiteboardID;

            SetUsernameMessage username = new SetUsernameMessage("fred");
            out.println(username.toJSON().toJSONString());
            String newUserList = TestUtil.nextNonEmptyLine(in);
            assertEquals(new UserListMessage(wb, new LinkedList<String>(Arrays.asList(new String[] { "fred" })))
                    .toJSON().toJSONString(), newUserList);

            // make sure we manually manage ID
            StrokeMessage stroke = new ToServerStrokeMessage(0, new DrawableSegment(0, 0, 5, 10, Color.RED, 5), "fred",
                    wb);
            out.println(stroke.toJSON().toJSONString());
            String strokeResponse = TestUtil.nextNonEmptyLine(in);
            assertEquals(stroke, FromServerStrokeMessage.STATIC.fromJSON(strokeResponse));
            sock.close();
        } catch (SocketTimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
