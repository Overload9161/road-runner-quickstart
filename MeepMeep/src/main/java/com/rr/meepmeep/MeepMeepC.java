package com.rr.meepmeep;

import com.acmerobotics.roadrunner.geometry.*;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.Constraints;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequence;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

public class MeepMeepC {
	public static void main(String[] args) {
		/* ==================================
		 * Do not edit any of these constants
		 * ================================*/
		MeepMeep meepMeep = new MeepMeep(800);
		// Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width DO NOT TOUCH!
		Constraints RobotConstraints = new Constraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15.5);
		
		// Starting positions, DO NOT EDIT
		Pose2d Blue1 = new Pose2d(-35,61, Math.toRadians(90));   // First location on the Blue side
		Pose2d Blue2 = new Pose2d(12, 61, Math.toRadians(90));   // Second location on the Blue side
		Pose2d Red1  = new Pose2d(-35,-61, Math.toRadians(270)); // First location on the Red side
		Pose2d Red2  = new Pose2d(12, -61, Math.toRadians(270)); // Second location on the Red side
		
		// This is an example trajectory showing how to use this program
		// GOTO: https://learnroadrunner.com/trajectorybuilder-functions.html
		// to get information on how to use the different methods
		// DO NOT TOUCH
		RoadRunnerBotEntity example = new DefaultBotBuilder(meepMeep)
											.setConstraints(RobotConstraints)
											.followTrajectorySequence(drive ->
																			  drive.trajectorySequenceBuilder(Blue1)
																					  .setTangent(Math.toRadians(180))
																					  .splineToLinearHeading(new Pose2d(-30,25,Math.toRadians(0)), Math.toRadians(0))
																					  .addDisplacementMarker(() ->{
																					  	  // This will run when we get to the first coordinate
																						  // after the splineToLinearHeading() and we will
																						  // move the arm up to position and outtake the
																						  // preloaded block
																					  })
																					  .lineToLinearHeading(new Pose2d(-60, 35, Math.toRadians(0)))
																					  .build()
											);
		
		/* =========================================================================================
		 * Start editing here!
		 * =======================================================================================*/
		
		// For the first blue location
		// TODO: This
//		RoadRunnerBotEntity blue1 = new DefaultBotBuilder(meepMeep)
//											.setConstraints(RobotConstraints)
//											.followTrajectorySequence(drive ->
//																			  drive.trajectorySequenceBuilder(Blue1)
//																					  // Start editing here
//																					  .build()
//											);
//
//		// For the second blue location
//		// TODO: This
//		RoadRunnerBotEntity blue2 = new DefaultBotBuilder(meepMeep)
//											.setConstraints(RobotConstraints)
//											.followTrajectorySequence(drive ->
//																			  drive.trajectorySequenceBuilder(Blue2)
//																					  // Start editing here
//																					  .build()
//											);
//
//		// For the first red location
//		// TODO: This
//		RoadRunnerBotEntity red1 = new DefaultBotBuilder(meepMeep)
//											.setConstraints(RobotConstraints)
//											.followTrajectorySequence(drive ->
//																			  drive.trajectorySequenceBuilder(Red1)
//																					  // Start editing here
//																					  .build()
//											);
//
//		// For the second red location
//		// TODO: This
//		RoadRunnerBotEntity red2 = new DefaultBotBuilder(meepMeep)
//											.setConstraints(RobotConstraints)
//											.followTrajectorySequence(drive ->
//																			  drive.trajectorySequenceBuilder(Red2)
//																					  // Start editing here
//																					  .build()
//											);
		
		meepMeep.setBackground(MeepMeep.Background.FIELD_FREIGHTFRENZY_ADI_DARK)
				.setDarkMode(true)
				.setBackgroundAlpha(0.95f)
				// Change this one to the name of your variable
				.addEntity(example)
				.start();
	}
}