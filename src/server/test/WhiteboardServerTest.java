package server.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

import junit.framework.TestCase;

import org.junit.Test;

public class WhiteboardServerTest extends TestCase {

    /**
     * WhiteboardServer test, "the most complete test"
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    @Test(timeout = 10000)
    public void firstTest() throws IOException, InterruptedException {
        TestUtil.startServer();
        // Avoid race where we try to connect to server too early
        Thread.sleep(100);
        try {
            Socket sock = TestUtil.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);

            // TODO Add test code
            out.println("What should we Print?");
            assertEquals("What should this equal?", TestUtil.nextNonEmptyLine(in));

            sock.close();
        } catch (SocketTimeoutException e) {
            throw new RuntimeException(e);
        }
    }

}
