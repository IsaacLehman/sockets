package helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/** 
 * Author:  Isaac Lehman
 * Course:  COMP 342 Data Communications and Networking
 * Date:    11 April 2021
 * Description:   Server-Client socket program
 */
public class Command_helper {

	/**
	 * returns true if a cmd is valid
	 */
	public static boolean valid_cmd(String cmd, String[] valid_cmds) {
		try {
			cmd = cmd.split(" ", 2)[0];
		} catch (Exception e) {
		}

		for (String val_cmd : valid_cmds) {
			if (val_cmd.equals(cmd)) {
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

	/**
	 * returns the result of the command entered
	 */
	public static String execute_command(String command, String start_dir) {
		String output = "";
		Process process;
		try {
			// execute the command
			process = Runtime.getRuntime().exec("powershell.exe /c cd " + start_dir + "; " + command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			// read in the result of the command
			String line = "";
			while ((line = reader.readLine()) != null) {
				output += line + "\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

}
