package client;

import java.util.Scanner;

import helper.Socket_helper;
import helper.Command_helper;
import helper.File_helper;

/** 
 * Author:  Isaac Lehman
 * Course:  COMP 342 Data Communications and Networking
 * Date:    11 April 2021
 * Description:   Server-Client socket program
 */
public class Client {

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
			String prompt = "Command: "; // prompt for a command
			String client_dir = "client-folder"; // clients directory for ftp usage
			Scanner input = new Scanner(System.in); // reads in clients commands

			Socket_helper.open_socket("localhost", 9001); // open a client socket connection

			// ==================================================================
			// RUNNER
			// ==================================================================

			while (Command_helper.keep_running(cmd)) {

				// GET INPUT
				System.out.print(prompt);
				cmd = input.nextLine();

				// CHECK IF THERE WAS A FILE NAME AND GET IT
				if (cmd.contains(" ")) {
					String[] split_commands = cmd.split(" ", 2);
					cmd = split_commands[0];
					file = split_commands[1];
				} else {
					file = "";
				}

				// Read file to send over socket and store on server
				if (cmd.equals("STOR")) {
					body = File_helper.read_file(file, client_dir);
				}

				// send request and wait/get response
				Socket_helper.write_msg(cmd + " " + file + " " + body);
				res = Socket_helper.read_msg();

				// Receive file over socket to store on client
				if (cmd.equals("RETR")) {
					File_helper.write_file(file, res, client_dir);
				}

				// Print the message to standard output
				System.out.println(res);
			}

			// ==================================================================
			// CLEAN UP
			// ==================================================================
			Socket_helper.close_client_socket();
			input.close();

		} catch (Exception exp) {
			exp.printStackTrace();
		}

	}

}
