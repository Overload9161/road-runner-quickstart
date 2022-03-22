package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.util.hardware.Hard_Auto;
import org.firstinspires.ftc.teamcode.util.hardware.Hard_Thread;

@TeleOp(name = "Test", group = "Test")
public class TeleOp_Basic extends OpMode {
	
//	Hardware r = new Hardware();
	Hard_Auto r = new Hard_Auto();
	SampleMecanumDrive drive;
	
	Hard_Thread a;
	
	@Override
	public void init() {
		r.initRobot(this);
		
		drive = new SampleMecanumDrive(this, r);
		
		drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		
		a = new Hard_Thread(r);
	
	}
	
	@Override
	public void loop() {
		double deflator;
		
		deflator = gamepad1.right_bumper ? .4 : .9;
		
		drive.setWeightedDrivePower(
				new Pose2d(
						-gamepad1.left_stick_y * deflator,
						-gamepad1.left_stick_x * deflator,
						-gamepad1.right_stick_x * deflator
				)
		);
		
		// Gamepad 2
		r.inout.setPower(gamepad2.left_stick_y * 0.7);
		r.lift.setPower(gamepad2.right_stick_y*0.7);
		
		r.spin.setPower((gamepad2.right_trigger + -gamepad2.left_trigger)*0.4);
	
	}
	
	@Override
	public void stop() {
		super.stop();
		a.interrupt();
	}
}