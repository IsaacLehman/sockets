package server;

import helper.Socket_helper;
import helper.Command_helper;
import helper.File_helper;


/** 
 * Author:  Isaac Lehman
 * Course:  COMP 342 Data Communications and Networking
 * Date:    11 April 2021
 * Description:   Server-Client socket program
 */

public class Server {

	public final static String[] CMDS = { "LIST", "RETR", "STOR", "PWD", "QUIT" }; // valid commands
	public static String currentDirectory = System.getProperty("user.dir");

	public static void main(String args[]) {

		try {
			// ==================================================================
			// SETUP
			// ==================================================================
			// VARIABLES:
			String cmd = ""; // the command entered
			String res = ""; // the server response
			String file = ""; // file name
			String body = ""; // file body
			String server_dir = "server-folder"; // servers directory for ftp usage
			String bad_cmd = "ERROR: Invalid command. Valid -> {LIST, RETR file-name, STOR file-name, PWD, QUIT}";

			Socket_helper.open_socket(9001); // open a server socket connection

			// ==================================================================
			// RUNNER
			// ==================================================================

			while (Command_helper.keep_running(cmd)) {
				// reset the response
				res = "";

				// WAIT FOR, THEN READ CLIENT'S MSG
				cmd = Socket_helper.read_msg();

				// CHECK IF THERE WAS A FILE NAME OR BODY AND GET IT
				try {
					String[] split_commands = cmd.split(" ", 3);
					cmd = split_commands[0];
					file = split_commands[1];

					try {
						body = split_commands[2];
					} catch (Exception e) {
						body = "";
					}

				} catch (Exception e) {
					file = "";
				}

				// RUN CLIENT'S COMMAND
				// -------------------------------------------------------------
				if (cmd.equals(CMDS[0])) { // LIST
					res = Command_helper.execute_command("ls", server_dir);
				} else if (cmd.equals(CMDS[1])) { // RETR
					res = File_helper.read_file(file, server_dir);
				} else if (cmd.equals(CMDS[2])) { // STOR
					File_helper.write_file(file, body, server_dir);
				} else if (cmd.equals(CMDS[3])) { // PWD
					res = Command_helper.execute_command("pwd", server_dir);
				} else if (cmd.equals(CMDS[4])) { // QUIT
					res = "Server has closed connection.";
					System.out.println("Connection was closed by the client.");
				} else { // INVALID
					System.out.println("Client Sent: ( Invalid Command: " + cmd + " )");
					Socket_helper.write_msg(bad_cmd);
					continue;
				}
				// -------------------------------------------------------------

				// LOG AND SEND RESPONSE TO CLIENT
				System.out.println("Client Sent: ( Command: " + cmd + "\tFile: " + file + " )");
				Socket_helper.write_msg(res);
			}

			// ==================================================================
			// CLEAN UP
			// ==================================================================
			Socket_helper.close_server_socket();
		} catch (Exception exp) {
			exp.printStackTrace();
		}

	}

}
