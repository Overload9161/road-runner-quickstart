package org.firstinspires.ftc.teamcode.util.FileManager.FM2;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.io.File;

/**
 * This file manager is a work in progress
 * DO NOT USE unless this warning is gone
 * Use {@link org.firstinspires.ftc.teamcode.util.FileManager.FileManager this one} until this is done
 *
 * This will, instead of a text file, create a JSON file for ease of use down the road.
 * TODO: Make the JSON file system working
 * TODO: Make the JSON Easy to use
 */
@SuppressWarnings(value="unused")
public class FileManager2 {
	String Type, fileName = null;
	
	File file, directory;
	OpMode opMode;
	
	/**
	 * Init the file manager
	 */
	public FileManager2(String Type, OpMode opMode){
		this.Type = Type;
		this.opMode = opMode;
	}
	
	// This will be what makes the file and the JSON template
	private void init(){
	
	}
	
	/**
	 * Use this to write to the JSON file
	 * @param Name The name of your item
	 * @param time The time this is running
	 * @param values The values that you want saved <br>
	 *               Use this format:
	 *               ... new String[]{%name%, %value%}, new String[]{%name%, %value%}, ...
	 */
	public void writeToJSON(String Name, int time, String[]... values){
	
	}
	
}