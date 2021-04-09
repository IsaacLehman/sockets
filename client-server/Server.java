import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Server{  
	public static void main(String args[]){  
		try {
			//A string containing the message we send to the client and a string to put data in	
			String writeStr="Hello Client!",readStr="";  
			
			//create a ServerSocket object connected to port 4242
			ServerSocket srverSocket=new ServerSocket(4242);  
			
			//create a Socket object so we can read and write to it
			//This socket is only created when a client connects to this server
			Socket mySocket=srverSocket.accept();  
			
			//Connect an input stream so we can read from the socket
			DataInputStream inStream=new DataInputStream(mySocket.getInputStream());  
			//Connect an output stream so we can write to the socket
			DataOutputStream outStream=new DataOutputStream(mySocket.getOutputStream());  
	
			//Start by reading a message from the client
			readStr=inStream.readUTF();  
			//Print the message to standard output
			System.out.println("client says: "+readStr);  
			//Write the message to the socket
			outStream.writeUTF(writeStr);  
			//flush the stream to make sure the data has been written 
			outStream.flush();  
			
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
}  