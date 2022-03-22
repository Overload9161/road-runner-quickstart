package org.firstinspires.ftc.teamcode.util.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Hard_Auto extends Hardware{
	
	Thread thread;
	
	/**
	 * Home the lift on the robot
	 */
	public void homeRobot(){
		thread = new Thread();
		while (!lift_endstop.isPressed())
			lift.setPower(0.3);
		lift.setPower(0);
		lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	}
	
}