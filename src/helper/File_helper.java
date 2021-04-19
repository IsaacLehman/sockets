package helper;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/** 
 * Author:  Isaac Lehman
 * Course:  COMP 342 Data Communications and Networking
 * Date:    11 April 2021
 * Description:   Server-Client socket program
 */
public class File_helper {
	
	/**
	 * Read from a file
	 * @param file_name
	 * @return
	 */
	public static String read_file(String file_name, String start_dir) {
		String file_body = "";
		try {
			File cur_file = new File(start_dir + "/" + file_name);
			Scanner myReader = new Scanner(cur_file);
			while (myReader.hasNextLine()) {
				file_body += myReader.nextLine()+"\n";
			}
			myReader.close();
		} catch(Exception e) {
			System.out.println("ERROR: bad file io.");
		}
		return file_body;
	}

	/**
	 * Write to a file
	 * @param file_name
	 * @param file_body
	 */
	public static void write_file(String file_name, String file_body, String start_dir) {
		try {
		  FileWriter myWriter = new FileWriter(start_dir + "/" + file_name);
		  myWriter.write(file_body);
		  myWriter.close();
		} catch (Exception e) {
		  System.out.println("ERROR: bad file io.");
		  e.printStackTrace();
		}
	}

}
