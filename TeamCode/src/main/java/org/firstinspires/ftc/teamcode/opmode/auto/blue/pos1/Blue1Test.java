package org.firstinspires.ftc.teamcode.opmode.auto.blue.pos1;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

@Autonomous(name = "Blue1Test", group = "Test")
//@Disabled
public class Blue1Test extends LinearOpMode {
	
	@Override
	public void runOpMode() throws InterruptedException {
		SampleMecanumDrive drive = new SampleMecanumDrive(this);
		
		Trajectory trajectory = drive.trajectoryBuilder(new Pose2d())
				.build();
		
		waitForStart();
	}
}