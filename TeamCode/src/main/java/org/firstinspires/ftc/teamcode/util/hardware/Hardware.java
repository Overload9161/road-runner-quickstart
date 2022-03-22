package org.firstinspires.ftc.teamcode.util.hardware;

import android.app.Application;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@SuppressWarnings(value = "unused")
public class Hardware extends Application {
	
	HardwareMap hwMap;
	Telemetry   telemetry;
	OpMode      opMode;
	
//	public final int first = 0;
//	public int second = 1;
//	public int third = 2;
	
	public static final int first = -506;
	public static final int second = -2324;
	public static final int third = -3489;
	
	private final ElapsedTime Timer = new ElapsedTime();
	public ElapsedTime time = new ElapsedTime();
	
	// Drive motors - DO NOT CHANGE
	public DcMotorEx frontLeft;
	public DcMotorEx frontRight;
	public DcMotorEx backLeft;
	public DcMotorEx backRight;
	/** All Drive Motors */
	public DcMotorEx[] drive;
	
	// Peripherals
	public DcMotorEx lift, inout, spin;
	
	/** This is all of our motors in an array for ease of use */
	public DcMotorEx[] allMotors;
	
	public AnalogInput potentiometer;
	public TouchSensor lift_endstop;
	
	public Servo S1;
	
	public Servo[] servo;
	
	JSONObject jsonObject,
			hardware,
			motors,
			driveMotors,
			peripherals,
			sensors,
			servos;
	
	/**
	 * Use this to initiate the robot
	 * @param opMode Just use "this"
	 */
	public void initRobot(OpMode opMode){
		this.opMode = opMode;
		this.hwMap = opMode.hardwareMap;
		this.telemetry = opMode.telemetry;
		try {
			// Change "Robot.json" to the file that you are using
			jsonObject = new JSONObject(Objects.requireNonNull(loadJSONFromAsset("Robot.json")));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		initHardware();
	}
	
	/**
	 * Use this to initiate the robot
	 * @param opMode Just use "this"
	 * @param JSON The name of you JSON file in your
	 */
	public void initRobot(OpMode opMode, String JSON){
		this.opMode = opMode;
		this.hwMap = opMode.hardwareMap;
		this.telemetry = opMode.telemetry;
		try {
			// Change "Robot.json" to the file that you are using
			jsonObject = new JSONObject(Objects.requireNonNull(loadJSONFromAsset(JSON)));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		initHardware();
	}
	
	void initHardware() {
		Log.d("Hardware-init", "Hardware init starting");
		try {
			// Do not edit this
			hardware = jsonObject.getJSONObject("Hardware");
			motors 	 = hardware.getJSONObject("DcMotors");
			peripherals = motors.getJSONObject("Peripherals");
			sensors  = hardware.getJSONObject("Sensors");
			servos   = hardware.getJSONObject("Servos");
			
			// Do not edit this
			driveMotors = motors.getJSONObject("Drive");
			frontLeft   = hwMap.get(DcMotorEx.class, String.valueOf(driveMotors.getJSONObject("Front Left Motor").get("Name")));
			frontRight  = hwMap.get(DcMotorEx.class, String.valueOf(driveMotors.getJSONObject("Front Right Motor").get("Name")));
			backLeft    = hwMap.get(DcMotorEx.class, String.valueOf(driveMotors.getJSONObject("Back Left Motor").get("Name")));
			backRight   = hwMap.get(DcMotorEx.class, String.valueOf(driveMotors.getJSONObject("Back Right Motor").get("Name")));
			drive 		= new DcMotorEx[]{frontLeft, frontRight, backLeft, backRight};
			
			{
				if (String.valueOf(driveMotors.getJSONObject("Front Left Motor").get("BrakeType")).equals("float")) {
					frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
				} else {
					frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
				}
				
				if (String.valueOf(driveMotors.getJSONObject("Front Right Motor").get("BrakeType")).equals("float")) {
					frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
				} else {
					frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
				}
				
				if (String.valueOf(driveMotors.getJSONObject("Back Left Motor").get("BrakeType")).equals("float")) {
					backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
				} else {
					backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
				}
				
				if (String.valueOf(driveMotors.getJSONObject("Back Right Motor").get("BrakeType")).equals("float")) {
					backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
				} else {
					backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
				}
				
				
				if ((Boolean) driveMotors.getJSONObject("Front Left Motor").get("Reverse"))
					frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
				if ((Boolean) driveMotors.getJSONObject("Front Right Motor").get("Reverse"))
					frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
				if ((Boolean) driveMotors.getJSONObject("Back Left Motor").get("Reverse"))
					backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
				if ((Boolean) driveMotors.getJSONObject("Back Right Motor").get("Reverse"))
					backRight.setDirection(DcMotorSimple.Direction.REVERSE);
			}
			try {
				lift = hwMap.get(DcMotorEx.class, String.valueOf(peripherals.getJSONObject("Lift").get("Name")));
				inout = hwMap.get(DcMotorEx.class, String.valueOf(peripherals.getJSONObject("InOut").get("Name")));
				spin = hwMap.get(DcMotorEx.class, String.valueOf(peripherals.getJSONObject("Spinner").get("Name")));
				
				lift.setDirection(DcMotorSimple.Direction.REVERSE);
				
				if ((Boolean) peripherals.getJSONObject("Lift").get("Reverse"))
					lift.setDirection(DcMotorSimple.Direction.REVERSE);
				if ((Boolean) peripherals.getJSONObject("InOut").get("Reverse"))
					inout.setDirection(DcMotorSimple.Direction.REVERSE);
				if((Boolean) peripherals.getJSONObject("Spinner").get("Reverse"))
					spin.setDirection(DcMotorSimple.Direction.REVERSE);
				
				if (String.valueOf(peripherals.getJSONObject("Lift").get("BrakeType")).equals("brake"))
					lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
				if (String.valueOf(peripherals.getJSONObject("InOut").get("BrakeType")).equals("brake"))
					inout.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
				if(String.valueOf(peripherals.getJSONObject("Spinner").get("BrakeType")).equals("brake"))
					spin.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
			} catch (IllegalArgumentException e){
				Log.d("Tried", "We tried");
				Log.d("Tried", String.valueOf(e));
			}
			
			// Add your own
			
			//S1 = hwMap.servo.get(String.valueOf(hardware.getJSONObject("Servo").getJSONObject("Test").get("name")));
			
			//servo = new Servo[]{S1};
			
			lift_endstop = hwMap.get(TouchSensor.class, (String) sensors.getJSONObject("Touch").getJSONObject("Lift").get("Name"));
			
			
			allMotors = new DcMotorEx[]{frontLeft, frontRight, backLeft, backRight, lift, inout};
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Use this method to init the robot for autonomous
	 */
	public void initAuto(){
		setDriveMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		//for(Servo servo : servo) servo.setPosition(0);
		waiter(500);
	}
	
	/**
	 * Set the motor mode for the drive motors
	 * @param mode {@link DcMotor.RunMode}
	 */
	public void setDriveMotorMode(DcMotor.RunMode mode){
		for(DcMotor dcMotor : drive) dcMotor.setMode(mode);
	}
	
	/**
	 * Use this method to home everything on the robot
	 */
	public void zeroRobot(){
		// TODO: Make this method
		// HELP: Check what angles are what voltages
	}

	/*=====================================================
	  || Constants, DO NOT EDIT please!
	  =====================================================*/
	
	private String loadJSONFromAsset(String fileName) {
		String json;
		try {
//            InputStream is = getApplicationContext().getAssets().open("Field.json");
			
			InputStream is = hwMap.appContext.getAssets().open(fileName);
			
			int size = is.available();
			
			byte[] buffer = new byte[size];
			
			int a = is.read(buffer);
			
			is.close();
			
			json = new String(buffer, StandardCharsets.UTF_8);
			
			
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return json;
	}
	
	/**
	 * Wait for a specified amount of time
	 * @param time in milliseconds
	 */
	
	public void waiter(int time) {
		Timer.reset();
		while (true) if (!(Timer.milliseconds() < time)) break;
	}
	
	public enum locations{
		FIRST(100),
		SECOND(200),
		THIRD(300);
		
		private int value;
		
		locations(int value){
			this.value = value;
		}
		
		public int toInt() {
			return value;
		}
	}
	
}