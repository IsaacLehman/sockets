import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;

public class Server{

	public final static String[] CMDS =  {"LIST", "RETR", "STOR", "PWD", "QUIT"}; // valid commands

	public static void main(String args[]){
		try {
			//==================================================================
			// SETUP
			//==================================================================
			//create a ServerSocket object connected to port 9001
			ServerSocket srverSocket=new ServerSocket(9001);

			//create a Socket object so we can read and write to it
			//This socket is only created when a client connects to this server
			Socket mySocket=srverSocket.accept();

			//Connect an input stream so we can read from the socket
			DataInputStream inStream=new DataInputStream(mySocket.getInputStream());
			//Connect an output stream so we can write to the socket
			DataOutputStream outStream=new DataOutputStream(mySocket.getOutputStream());

			//==================================================================
			// RUNNER
			//==================================================================
			// VARIABLES:
			String cmd    = ""; // the command entered
			String res    = ""; // the server response
			String file   = ""; // file name
			String prompt = "Command: "; // prompt for a command
			String bad_cmd = "ERROR: Invalid command. Valid -> {LIST, RETR file-name, STOR file-name, PWD, QUIT}";
			Scanner input = new Scanner(System.in); // reads in clients commands

			while(keep_running(cmd)) {
				//Start by reading a message from the client
				cmd=inStream.readUTF();

				if(!valid_cmd(cmd)) { // if not valid, tell client
					System.out.println("Client Sent: ( Invalid Command: "+cmd+" )");
					outStream.writeUTF(bad_cmd);
					outStream.flush();
					continue;
				}

				// CHECK IF THERE WAS A FILE NAME AND GET IT
				try {
					String[] split_commands = cmd.split(" ", 2);
					cmd = split_commands[0];
					file = split_commands[1];
				} catch(Exception e) {
					 file = "";
				}

				//Print the message to standard output
				System.out.println("Client Sent: ( Command: "+cmd+"\tFile: "+file+" )");
				//Write the message to the socket
				outStream.writeUTF(res);
				//flush the stream to make sure the data has been written
				outStream.flush();
			}

			//==================================================================
			// CLEAN UP
			//==================================================================
			//close everything we opened so far
			inStream.close();
			outStream.close();
			mySocket.close();
			srverSocket.close();
			}
		catch (Exception exp) {
			exp.printStackTrace();
		}

	}

	/**
	* returns true if a cmd is valid
	*/
	public static boolean valid_cmd(String cmd) {
		try {
			String[] split_commands = cmd.split(" ", 2);
			cmd = split_commands[0];
			String file = split_commands[1];
		} catch(Exception e) {
			// do nothing
		}

		for (String val_cmd: CMDS) {
			if(val_cmd.equals(cmd)) {
				return true;
			}
		}

		return false;
	}


	/**
	* returns true if cmd is not "QUIT"
	*/
	public static boolean keep_running(String cmd) {
		return !cmd.equals("QUIT");
	}
}
