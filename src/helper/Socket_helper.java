package helper;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/** 
 * Author:  Isaac Lehman
 * Course:  COMP 342 Data Communications and Networking
 * Date:    11 April 2021
 * Description:   Server-Client socket program
 */
public class Socket_helper {

	// CREATE SOCKET VARS
	public static ServerSocket srverSocket;
	public static Socket mySocket;
	public static DataInputStream inStream;
	public static DataOutputStream outStream;

	/**
	 * 
	 * Opens a socket for the server
	 * 
	 * @param port
	 * @return
	 */
	public static boolean open_socket(int port) {
		try {
			// create a ServerSocket object connected to port 9001
			srverSocket = new ServerSocket(port);

			// create a Socket object so we can read and write to it
			// This socket is only created when a client connects to this server
			mySocket = srverSocket.accept();

			// Connect an input stream so we can read from the socket
			inStream = new DataInputStream(mySocket.getInputStream());
			// Connect an output stream so we can write to the socket
			outStream = new DataOutputStream(mySocket.getOutputStream());

			// return success
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * Opens a socket for the client
	 * 
	 * @param host
	 * @param port
	 * @return
	 */
	public static boolean open_socket(String host, int port) {
		try {
			// create a socket that connects to the host server running on port
			mySocket = new Socket(host, port);
			// Connect an input stream so we can read from the socket
			inStream = new DataInputStream(mySocket.getInputStream());
			// Connect an output stream so we can write to the socket
			outStream = new DataOutputStream(mySocket.getOutputStream());

			// return success
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * Close Server socket
	 * 
	 * @return
	 */
	public static boolean close_server_socket() {
		try {
			// close everything we opened so far
			inStream.close();
			outStream.close();
			mySocket.close();
			srverSocket.close();

			// return success
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * Close Client socket
	 * 
	 * @return
	 */
	public static boolean close_client_socket() {
		try {
			// close everything we opened so far
			inStream.close();
			outStream.close();
			mySocket.close();

			// return success
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String read_msg() {
		try {
			return inStream.readUTF();
		} catch (Exception e) {
			return "ERROR reading msg.";
		}
	}

	public static void write_msg(String msg) {
		try {
			// Write the message to the socket
			outStream.writeUTF(msg);
			// flush the stream to make sure the data has been written
			outStream.flush();
		} catch (Exception e) {
			System.err.println("ERROR: writing msg.");
		}
	}
}
