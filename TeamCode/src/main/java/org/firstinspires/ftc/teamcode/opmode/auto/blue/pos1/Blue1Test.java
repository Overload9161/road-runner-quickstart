package org.firstinspires.ftc.teamcode.opmode.auto.blue.pos1;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.util.Coords;
import org.firstinspires.ftc.teamcode.util.hardware.Hard_Auto;
import org.firstinspires.ftc.teamcode.util.images.Camera;

@Autonomous(name = "Blue1Test", group = "Test", preselectTeleOp = "Test")
//@Disabled
public class Blue1Test extends LinearOpMode {
	
	Hard_Auto r = new Hard_Auto();
//	FileManager fileManager;
	Camera cam;
//	ElapsedTime time = new ElapsedTime();
	
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
		
		Trajectory trajectory = drive.trajectoryBuilder(loc.BLUE1_START)
//										.setTangent(Math.toRadians(180))
										.lineToLinearHeading(new Pose2d(loc.BLUE_TOWER_L1, Math.toRadians(-15)))
										.addDisplacementMarker(() ->{
											// This will run when we get to the first coordinate
											// after the splineToLinearHeading() and we will
											// move the arm up to position and outtake the
											// preloaded block
											
										})
//										.lineToLinearHeading(new Pose2d(-60, 35, Math.toRadians(0)))
										.build();
		
		Trajectory trajectory1 = drive.trajectoryBuilder(new Pose2d(loc.BLUE_TOWER_L1, Math.toRadians(0)))
										.lineToLinearHeading(new Pose2d(loc.BLUE_WAREHOUSE, Math.toRadians(0)))
										.build();
		
		waitForStart();
		
//		cam.takePhoto("Test");
//
//		ReadImage rm = cam.analyzePhoto();

//		r.waiter(3000);
		
		drive.followTrajectory(trajectory);
		
		// By this point we should have completely analyzed the photo
		// and have gotten the correct location
		
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