package org.firstinspires.ftc.teamcode.util.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Hard_Thread extends Thread{
	
	Hardware r;
	
	public Hard_Thread(Hardware r){
		this.r = r;
		this.start();
	}
	
	@Override
	public synchronized void start() {
		super.start();
	}
	
	@Override
	public void run() {
		super.run();
		while (!r.lift_endstop.isPressed())
			r.lift.setPower(0.3);
		r.lift.setPower(0);
		r.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		r.lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
	}
	
	@Override
	public void interrupt() {
		super.interrupt();
	}
}