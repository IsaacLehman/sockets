import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Client{  
	public static void main(String args[]){  
		try {
			//A string containing the message we send to the Server and a string to put data in	
			String writeStr="Hello Server!",readStr="";  
			//create a socket that connects to the localhost server running on port 4242
			Socket mySocket=new Socket("localhost",4242);  
			//Connect an input stream so we can read from the socket
			DataInputStream inStream=new DataInputStream(mySocket.getInputStream());  
			//Connect an output stream so we can write to the socket
			DataOutputStream outStream=new DataOutputStream(mySocket.getOutputStream());  
		
			//Write the message to the socket
			outStream.writeUTF(writeStr);  
			//flush the stream to make sure the data has been written 
			outStream.flush();  
			//Start by reading a message from the server
			readStr=inStream.readUTF();  
			//Print the message to standard output
			System.out.println("Server says: "+readStr);  
  
			//close everything we opened so far
			inStream.close();  
			outStream.close();
			mySocket.close();  
			  
		}
		catch (Exception exp) {
			exp.printStackTrace();
		}
		
	}
}  