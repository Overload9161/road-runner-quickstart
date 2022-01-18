package org.firstinspires.ftc.teamcode.util.logger;

import android.util.Log;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.logger.fileManager.Executable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings(value = "unused")
public class FileManager {
	File file, directory;
	
	DcMotor[] motor = null;
	OpMode op;
	
	List<String> buffer = new ArrayList<>();
	
	FileOutputStream fos;
	
	ElapsedTime time;
	
	List<LynxModule> allHubs;
	
	private String name;
	
	List<Executable> executables = new ArrayList<>();
	
	/**
	 * Init the file manager
	 */
	public FileManager(OpMode op){
		this.op = op;
	}
	
	/**
	 * Init FileManager
	 * @param Type Weather its TeleOp or Autonomous
	 */
	public void init(String Type){
		try {
			directory = new File(op.hardwareMap.appContext.getCacheDir()+"/Logs/"+Type);
			if(!directory.exists())
				directory.mkdirs();
			name = "Log"+Type+Objects.requireNonNull(directory.listFiles()).length;
			file = new File(directory, name+".txt");
			fos = new FileOutputStream(file);
			Log.d("File Location", file.getAbsolutePath());
			Log.d("File", "Working");
		} catch (IOException e){
			Log.wtf("File", "Failed");
			e.printStackTrace();
		}
		
		allHubs = op.hardwareMap.getAll(LynxModule.class);
		
		for (LynxModule module : allHubs) {
			module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
		}
		executables.add(gamepadLog);
		executables.add(motorsLog);
	}
	
	/**
	 * Start the file writer for teleop. This will log all data from the gamepads along with everything else
	 * @param time The timer you want to use
	 */
	public void StartTeleOp(ElapsedTime time){
		this.time = time;
		timer.schedule(new calling(time, this), 0, 1);
	}
	
	public String getName(){
		return this.name;
	}
	
	/**
	 * Start the file writer for autonomous. This will only log sensor data.
	 * @param time The timer you want to use
	 */
	public void StartAuto(ElapsedTime time){
		this.time = time;
		timer.schedule(new calling(time, this), 0, 25);
	}
	
	/**
	 * Point this file writer to the motors on your bot
	 * @param motors The array of all your motors
	 */
	public void initMotors(DcMotor[] motors){
		this.motor = motors;
	}
	
	Timer timer = new Timer();
	
	/**
	 * Write everything in the buffer to the file, this runs periodically
	 */
	public void writeToFile(){
		for(int i=0;i<buffer.size();i++){
			try {
				fos.write(buffer.get(i).getBytes());
				fos.write("\n".getBytes());
				fos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		buffer.clear();
	}
	
	Executable gamepadLog = (time) -> {
		// Gamepad 1
		if(op.gamepad1.getGamepadId() > 0) {
			// Sticks
			writeFile("Gamepad1-LStick", new float[]{op.gamepad1.left_stick_x, op.gamepad1.left_stick_y}, time);
			writeFile("Gamepad1-RStick", new float[]{op.gamepad1.right_stick_x, op.gamepad1.right_stick_y}, time);
			// YBAX
			writeFile("Gamepad1-YBAX", new boolean[]{op.gamepad1.y, op.gamepad1.b, op.gamepad1.a, op.gamepad1.x}, time);
			// Triggers
			writeFile("Gamepad1-LT", new float[]{op.gamepad1.left_trigger, op.gamepad1.right_trigger}, time);
			// Bumpers
			writeFile("Gamepad1-B", new boolean[]{op.gamepad1.left_bumper, op.gamepad1.right_bumper}, time);
			// Dpad
			writeFile("Gamepad1-DP", new boolean[]{op.gamepad1.dpad_up, op.gamepad1.dpad_right, op.gamepad1.dpad_down, op.gamepad1.dpad_left},time);
			// Random
			writeFile("Gamepad1-R", new boolean[]{op.gamepad1.start, op.gamepad1.back, op.gamepad1.left_stick_button, op.gamepad1.right_stick_button}, time);
			
		}
		// Gamepad 2
		if(op.gamepad2.getGamepadId() > 0){
			// Sticks
			writeFile("Gamepad2-LStick", new float[]{op.gamepad2.left_stick_x, op.gamepad2.left_stick_y}, time);
			writeFile("Gamepad2-RStick", new float[]{op.gamepad2.right_stick_x, op.gamepad2.right_stick_y}, time);
			// YBAX
			writeFile("Gamepad2-YBAX", new boolean[]{op.gamepad2.y, op.gamepad2.b, op.gamepad2.a, op.gamepad2.x}, time);
			// Triggers
			writeFile("Gamepad2-LT", new float[]{op.gamepad2.left_trigger, op.gamepad2.right_trigger}, time);
			// Bumpers
			writeFile("Gamepad2-B", new boolean[]{op.gamepad2.left_bumper, op.gamepad2.right_bumper}, time);
			// Dpad
			writeFile("Gamepad2-DP", new boolean[]{op.gamepad2.dpad_up, op.gamepad2.dpad_right, op.gamepad2.dpad_down, op.gamepad2.dpad_left}, time);
			// Random
			writeFile("Gamepad2-R", new boolean[]{op.gamepad2.start, op.gamepad2.back, op.gamepad2.left_stick_button, op.gamepad2.right_stick_button}, time);
		}
		for(VoltageSensor sensor : op.hardwareMap.voltageSensor){
			double voltage = sensor.getVoltage();
			if(voltage > 0)
				writeFile("Voltage", voltage, time);
		}
	};
	
	Executable motorsLog = (time) -> {
		if(motor != null){
			for(DcMotor dcMotor : motor){
				writeFile(String.valueOf(op.hardwareMap.getNamesOf(dcMotor)), new double[]{dcMotor.getPower(), dcMotor.getTargetPosition(), dcMotor.getCurrentPosition()}, time);
			}
		}
	};
	
	/**
	 * White data from the motors to the buffer
	 * @param time When you are logging this
	 */
	public void writeMotors(double time){
		if(motor != null){
			for(DcMotor dcMotor : motor){
				writeFile(String.valueOf(op.hardwareMap.getNamesOf(dcMotor)), new double[]{dcMotor.getPower(), dcMotor.getTargetPosition(), dcMotor.getCurrentPosition()}, time);
			}
		}
	}
	
	// The max amount of char in a Break line
	int maxLength = 100;
	int charLength;
	
	/**
	 * This method is made to add a bar across the file that outlines different things that happen
	 * @param name The label of the bar
	 */
	public void Break(String name){
		StringBuilder thing = new StringBuilder();
		char[] Name = name.toCharArray();
		charLength = maxLength - Name.length;
		for(int i = 0; i<charLength; i++){
			if(i==(charLength /2)){
				thing.append(name);
			}
			thing.append("=");
		}
		Log.d("Thing", thing.toString());
		buffer.add(thing.toString());
	}
	
	/**
	 * Close the file manager
	 * <p>
	 * You won't be able to save anything else to the file
	 */
	public void close(){
		try {
			Break("End of File");
			timer.cancel();
			writeToFile();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write to the file buffer
	 * @param caption The name of the things that you are logging
	 * @param LogThing The thing you want to log
	 * @param time When you logged this thing
	 */
	public FileManager writeFile(String caption, Object LogThing, double time){
		buffer.add(caption+"/"+(int)time+":"+LogThing);
		return this;
	}
	
	public FileManager writeFile(String caption, double[] LogThing, double time){
		buffer.add(caption+"/"+(int)time+":"+ Arrays.toString(LogThing));
		return this;
	}
	
	public FileManager writeFile(String caption, float[] LogThing, double time){
		buffer.add(caption+"/"+(int)time+":"+ Arrays.toString(LogThing));
		return this;
	}
	
	public FileManager writeFile(String caption, boolean[] LogThing, double time){
		buffer.add(caption+"/"+(int)time+":"+ Arrays.toString(LogThing));
		return this;
	}

//	Executable[] sample;

//	ArrayList<Executable> sample;
	
	
	Executable sample;
	/**
	 * Set the code that you want to run
	 * @param code {@link Executable}
	 */
	public void setExecutable(Executable code){
		executables.add(code);
	}
	
	void customRun(double time){
		for(Executable executable : executables){
			executable.run(time);
		}
	}
	
}

class calling extends TimerTask{
	
	FileManager f;
	ElapsedTime time;
	public calling(ElapsedTime time, FileManager f){
		this.time = time;
		this.f = f;
	}
	
	public void run(){
		double t = time.milliseconds();
//		f.writeGamepad(t);
//		f.writeMotors(t);
		f.customRun(t);
		f.writeToFile();
	}
}