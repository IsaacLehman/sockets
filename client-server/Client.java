import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;

public class Client{

	public static void main(String args[]){
		try {
			//==================================================================
			// SETUP
			//==================================================================
			//create a socket that connects to the localhost server running on port 9001
			Socket mySocket=new Socket("localhost",9001);
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
			Scanner input = new Scanner(System.in); // reads in clients commands


			while(keep_running(cmd)) {

				// GET INPUT
				System.out.print(prompt);
				cmd = input.nextLine();

				//Write the message to the socket
				outStream.writeUTF(cmd+file);
				//flush the stream to make sure the data has been written
				outStream.flush();
				//Start by reading a message from the server
				res=inStream.readUTF();
				//Print the message to standard output
				System.out.println("Server says: "+res);
			}


			//==================================================================
			// CLEAN UP
			//==================================================================
			//close everything we opened so far
			inStream.close();
			outStream.close();
			mySocket.close();

		}
		catch (Exception exp) {
			exp.printStackTrace();
		}

	}

	/**
	* returns true if cmd is not "QUIT"
	*/
	public static boolean keep_running(String cmd) {
		return !cmd.equals("QUIT");
	}
}
