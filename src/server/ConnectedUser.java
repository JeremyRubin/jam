package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import whiteboard.User;

/**
 * Connected User
 * 
 * This class serves as a controller for the User Object which writes out and
 * reads in from the client.
 */
public class ConnectedUser implements Runnable {
    private final Socket socket;
    public final WhiteboardServer server;
    private final User user;

    public ConnectedUser(Socket socket, WhiteboardServer server) {
        this.socket = socket;
        this.server = server;
        this.user = new User(this);
    }

    /**
     * This runnable reads lines from the server and feeds them into the User's
     * input queue.
     * 
     */
    class ReaderThread implements Runnable {
        private final Socket socket;
        private final User user;

        public ReaderThread(Socket socket, WhiteboardServer server, User user) {
            this.socket = socket;
            this.user = user;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                try {
                    for (String line = in.readLine(); line != null; line = in.readLine()) {
                        this.user.input(line);
                    }
                } catch (IOException e) {
                } finally {
                    in.close();
                    this.socket.close();
                    this.user.input(User.KILL_MSG);
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * 
     * This runnable writes lines out to the client from the Users output queue
     */
    class WriterThread implements Runnable {
        private final Socket socket;
        private final User user;

        public WriterThread(Socket socket, WhiteboardServer server, User user) {
            this.socket = socket;
            this.user = user;
        }

        @Override
        public void run() {
            try {
                PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
                try {
                    // does this ever stop if socket closes?
                    while (true) {
                        out.println(this.user.outQueue.take());
                    }

                } catch (InterruptedException e) {
                } finally {
                    out.close();
                    this.socket.close();
                    this.user.input(User.KILL_MSG);
                }
            } catch (IOException e) {
            }
        }

    }

    @Override
    public void run() {
        new Thread(new WriterThread(this.socket, this.server, this.user)).start();
        new Thread(new ReaderThread(this.socket, this.server, this.user)).start();
        new Thread(this.user).start();

    }
}
