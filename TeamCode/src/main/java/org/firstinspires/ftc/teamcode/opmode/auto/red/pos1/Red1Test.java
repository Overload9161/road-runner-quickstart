package org.firstinspires.ftc.teamcode.opmode.auto.red.pos1;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.util.Coords;
import org.firstinspires.ftc.teamcode.util.hardware.Hard_Auto;
import org.firstinspires.ftc.teamcode.util.images.Camera;

@Autonomous(name = "Red1Test", group = "Auto", preselectTeleOp = "Test")
public class Red1Test extends LinearOpMode {
	Hard_Auto r = new Hard_Auto();
	Camera cam;
	
	SampleMecanumDrive drive;
	
	@Override
	public void runOpMode() throws InterruptedException {
		r.initRobot(this);
		r.initAuto();
		
		r.homeRobot();
		
		drive = new SampleMecanumDrive(this, r);
		
		
		cam = new Camera(this);
		cam.openCamera();
		cam.startCamera();
		
		Coords loc = new Coords();
		
		Trajectory trajectory = drive.trajectoryBuilder(loc.RED1_START)
//										.setTangent(Math.toRadians(180))
										.lineToLinearHeading(new Pose2d(loc.RED_TOWER_L1, Math.toRadians(-15)))
										.addDisplacementMarker(() ->{
											// This will run when we get to the first coordinate
											// after the splineToLinearHeading() and we will
											// move the arm up to position and outtake the
											// preloaded block
					
										})
//										.lineToLinearHeading(new Pose2d(-60, 35, Math.toRadians(0)))
										.build();
		
		Trajectory trajectory1 = drive.trajectoryBuilder(loc.RED1_START)
//										 .lineToLinearHeading(new Pose2d(loc.RED_WAREHOUSE, Math.toRadians(90)))
										 .lineTo(loc.RED_WAREHOUSE)
										 .build();
		
		waitForStart();
		
//		cam.takePhoto("Test");
//
//		ReadImage rm = cam.analyzePhoto();
//
//		drive.followTrajectory(trajectory);
//
//		switch (rm.getLocation()){
//			case 0:
//				r.lift.setTargetPosition(Hardware.first);
//				break;
//			case 1:
//				r.lift.setTargetPosition(Hardware.second);
//				break;
//			case 2:
//				r.lift.setTargetPosition(Hardware.third);
//				break;
//			default:
//				// Oops
//		}
//
//		while (r.lift.getCurrentPosition() < r.lift.getTargetPosition())
//			r.lift.setPower(0.5);
//
//		r.lift.setPower(0);
//
//		r.inout.setPower(0.6);
//
//		r.waiter(500);
//
//		r.inout.setPower(0);
		
		drive.followTrajectory(trajectory1);
		
		cam.closeCamera();
	}
}