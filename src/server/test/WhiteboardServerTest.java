package server.test;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

import junit.framework.TestCase;
import message.FromServerStrokeMessage;
import message.NewWhiteboardMessage;
import message.SetUsernameMessage;
import message.StrokeMessage;
import message.SwitchWhiteboardMessage;
import message.ToServerStrokeMessage;

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
        TestUtil.startServer();
        // Avoid race where we try to connect to server too early
        Thread.sleep(100);
        try {
            Socket sock = TestUtil.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            String s = new NewWhiteboardMessage().toJSON().toJSONString();
            out.println(s);
            String wb = SwitchWhiteboardMessage.STATIC.fromJSON(TestUtil.nextNonEmptyLine(in)).whiteboardID;

            System.out.println(wb);

            System.out.println(TestUtil.nextNonEmptyLine(in));

            SetUsernameMessage username = new SetUsernameMessage("fred");
            out.println(username.toJSON().toJSONString());
            assertEquals(username.toJSON().toJSONString(), TestUtil.nextNonEmptyLine(in));

            StrokeMessage s1 = new ToServerStrokeMessage(0, new DrawableSegment(0, 0, 5, 10, Color.RED, 5), "fred", wb);
            out.println(s1.toJSON().toJSONString());
            s = TestUtil.nextNonEmptyLine(in);
            System.out.print(s);
            assertEquals(s1, FromServerStrokeMessage.STATIC.fromJSON(s));
            sock.close();
        } catch (SocketTimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
