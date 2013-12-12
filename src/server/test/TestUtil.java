package server.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

import server.WhiteboardServer;

/**
 * 
 * This testutil was adapted from the one written by staff for Pset 3
 */
public class TestUtil {

    public static void startServer() {
        startServer("--port", "4444");
    }

    public static Socket connect() throws IOException {
        Socket ret = null;
        final int MAX_ATTEMPTS = 50;
        int attempts = 0;
        do {
            try {
                ret = new Socket("127.0.0.1", 4444);
            } catch (ConnectException ce) {
                try {
                    if (++attempts > MAX_ATTEMPTS)
                        throw new IOException("Exceeded max connection attempts", ce);
                    Thread.sleep(300);
                } catch (InterruptedException ie) {
                    throw new IOException("Unexpected InterruptedException", ie);
                }
            }
        } while (ret == null);
        ret.setSoTimeout(3000);
        return ret;
    }

    private static void startServer(final String... args) {
        final String myArgs[] = args;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    WhiteboardServer.main(myArgs);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }).start();
    }

    public static String nextNonEmptyLine(BufferedReader in) throws IOException {
        while (true) {
            String ret = in.readLine();
            System.out.println(ret);
            if (ret == null || !ret.equals(""))
                return ret;
        }
    }
}
