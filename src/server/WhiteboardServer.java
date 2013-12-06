package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;

import whiteboard.User;
import whiteboard.WhiteboardServerModel;

/**
 * 
 * @author jeremyrubin
 * 
 *         The Whiteboard server is thread safe.
 * 
 *         For each client, it opens a new thread to handle it.
 * 
 *         These client threads only ever call two methods in the server,
 *         addConnection and removeConnection and never directly access fields.
 *         These methods are both synchronized, and therefore are immune to
 *         blocking the client handling threads individually.
 * 
 *         A deadlock *could* arise if several Connection threads try to obtain
 *         both a server lock and a board lock for a single operation, however
 *         these operations are independent and don't ever attempt to acquire
 *         both locks at the same time.
 * 
 *         If serve gets run in multiple threads, it won't be an issue as serve
 *         opens up its own new threads
 * 
 *         The other public methods are static and do not modify any class
 *         variables and are therefore also threadsafe
 */

public class WhiteboardServer {
	private final ServerSocket serverSocket;

	public final Map<String, WhiteboardServerModel> openWhiteboards;

	/**
	 * Make a WhiteboardServer that listens for connections on port.
	 * 
	 * @param port
	 *            port number, requires 0 <= port <= 65535
	 */
	public WhiteboardServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		this.openWhiteboards = new HashMap<String, WhiteboardServerModel>();
	}

	/**
	 * Run the server, listening for client connections and handling them. Never
	 * returns unless an exception is thrown.
	 * 
	 * @throws IOException
	 *             if the main server socket is broken (IOExceptions from
	 *             individual clients do *not* terminate serve())
	 */
	public void serve() throws IOException {
		while (true) {
			// block until a client connects
			Socket socket = serverSocket.accept();

			// handle the client
			try {
				handleConnection(socket);

			} catch (IOException e) {
				e.printStackTrace(); // but don't terminate serve()
			}
		}
	}

	/**
	 * Handle a single client connection. Returns when client disconnects.
	 * 
	 * @param socket
	 *            socket where the client is connected
	 * @throws IOException
	 *             if connection has an error or terminates unexpectedly
	 */
	private void handleConnection(Socket socket) throws IOException {
		(new Thread(new User(socket, this))).start();
	}

	/**
	 * Start a WhiteboardServer using the given arguments.
	 * 
	 * Usage: WhiteboardServer [--port PORT]
	 * 
	 * PORT is an optional integer in the range 0 to 65535 inclusive, specifying
	 * the port the server should be listening on for incoming connections. E.g.
	 * "WhiteboardrServer --port 1234" starts the server listening on port 1234.
	 * 
	 */
	public static void main(String[] args) {
		// Command-line argument parsing
		int port = 4444; // default port
		Queue<String> arguments = new LinkedList<String>(Arrays.asList(args));
		try {
			while (!arguments.isEmpty()) {
				String flag = arguments.remove();
				try {
					if (flag.equals("--port")) {
						port = Integer.parseInt(arguments.remove());
						if (port < 0 || port > 65535) {
							throw new IllegalArgumentException("port " + port
									+ " out of range");
						}
					} else {
						throw new IllegalArgumentException("unknown option: \""
								+ flag + "\"");
					}
				} catch (NoSuchElementException nsee) {
					throw new IllegalArgumentException("missing argument for "
							+ flag);
				} catch (NumberFormatException nfe) {
					throw new IllegalArgumentException(
							"unable to parse number for " + flag);
				}
			}
		} catch (IllegalArgumentException iae) {
			System.err.println(iae.getMessage());
			System.err
					.println("usage: MinesweeperServer [--debug] [--port PORT] [--size SIZE | --file FILE]");
			return;
		}

		try {
			runWhiteboardServer(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Start a WhiteboardServer running on the specified port
	 * 
	 * @param port
	 *            The network port on which the server should listen.
	 */
	public static void runWhiteboardServer(int port) throws IOException {

		WhiteboardServer server = new WhiteboardServer(port);
		server.serve();

	}
}
