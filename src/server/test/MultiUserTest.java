package server.test;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import junit.framework.TestCase;
import message.FromServerStrokeMessage;
import message.NewWhiteboardMessage;
import message.SetUsernameMessage;
import message.StrokeMessage;
import message.SwitchWhiteboardMessage;
import message.ToServerStrokeMessage;

import org.junit.Test;

import drawable.DrawableSegment;

public class MultiUserTest extends TestCase {

    /**
     * WhiteboardServer test, "the most complete test"
     * 
     * @throws IOException
     * @throws InterruptedException
     */

    private class UserTester implements Runnable {
        private Exception exception = null;
        private Error error = null;

        @Override
        public void run() {

            try {
                Socket sock = TestUtil.connect();
                BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                PrintWriter out = new PrintWriter(sock.getOutputStream(), true);

                String setup = new NewWhiteboardMessage().toJSON().toJSONString();

                out.println(setup);
                String clients = TestUtil.nextNonEmptyLine(in);
                String wbData = TestUtil.nextNonEmptyLine(in);
                String wb = SwitchWhiteboardMessage.STATIC.fromJSON(wbData).whiteboardID;

                SetUsernameMessage username = new SetUsernameMessage("fred");
                out.println(username.toJSON().toJSONString());
                String switchedName = TestUtil.nextNonEmptyLine(in);
                assertEquals(username.toJSON().toJSONString(), switchedName);

                // make sure we manually manage ID
                StrokeMessage stroke = new ToServerStrokeMessage(0, new DrawableSegment(0, 0, 5, 10, Color.RED, 5),
                        "fred", wb);
                out.println(stroke.toJSON().toJSONString());
                String strokeResponse = TestUtil.nextNonEmptyLine(in);
                assertEquals(stroke, FromServerStrokeMessage.STATIC.fromJSON(strokeResponse));
                sock.close();

            } catch (Exception e) {
                exception = e;
            } catch (Error e) {
                e.printStackTrace();

                error = e;
            }
        }

        public void test() throws Exception {
            if (exception != null) {
                throw exception;
            } else if (error != null) {
                throw error;
            }

        }
    }

    @Test(timeout = 1000000)
    public void test() throws IOException, InterruptedException {
        TestUtil.startServer();
        // Avoid race where we try to connect to server too early
        Thread.sleep(100);
        UserTester x = new UserTester();
        UserTester y = new UserTester();
        UserTester z = new UserTester();

        Thread a = new Thread(x);
        Thread b = new Thread(y);
        Thread c = new Thread(z);
        // c.start();
        Thread.sleep(10);
        a.start();
        Thread.sleep(10);

        // b.start();
        a.join();
        // b.join();
        // c.join();
        try {
            x.test();
            y.test();
            z.test();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (Error e) {
            e.printStackTrace();
            throw new RuntimeException();

        }
    }
}
