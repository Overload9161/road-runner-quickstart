package org.firstinspires.ftc.teamcode.opmode.auto.blue.pos1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.util.hardware.Hard_Thread;
import org.firstinspires.ftc.teamcode.util.hardware.Hardware;

@Autonomous(name = "Blue1_FullSend", group = "Blue", preselectTeleOp = "Test")
public class Blue1_FullSend extends LinearOpMode {
	@Override
	public void runOpMode() throws InterruptedException {
		Hardware r = new Hardware();
		r.initRobot(this);
		r.initAuto();
		
		Hard_Thread a = new Hard_Thread(r);
		
		waitForStart();
		
		r.lift.setPower(-0.6);
		r.waiter(500);
		r.lift.setPower(0);
		
		r.setDriveMotorMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		
		for(DcMotorEx motor : r.drive){
			motor.setPower(0.6);
		}
		
		r.waiter(5000);
		
		for(DcMotorEx motor : r.drive){
			motor.setPower(0);
		}
		
	}
}