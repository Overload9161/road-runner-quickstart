package com.rr.meepmeep;

import com.acmerobotics.roadrunner.followers.HolonomicPIDVAFollower;
import com.acmerobotics.roadrunner.geometry.*;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueLight;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedLight;
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
		Pose2d Blue2 = new Pose2d(-35, 61, Math.toRadians(90));   // Second location on the Blue side
		Pose2d Blue3 = new Pose2d(12, 61, Math.toRadians(90));   // Third location on the Blue side
		Pose2d Blue4 = new Pose2d(12, 61, Math.toRadians(90));   // Fourth location on the Blue side
		Pose2d Red1  = new Pose2d(-35,-61, Math.toRadians(270)); // First location on the Red side
		Pose2d Red2  = new Pose2d(-35,-61, Math.toRadians(270)); // Second location on the Red side
		Pose2d Red3  = new Pose2d(12, -61, Math.toRadians(270)); // Third location on the Red side
		Pose2d Red4  = new Pose2d(12, -61, Math.toRadians(270)); // Fourth location on the Red side
		
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
		RoadRunnerBotEntity blue1 = new DefaultBotBuilder(meepMeep)
											.setConstraints(RobotConstraints)
											.setColorScheme(new ColorSchemeBlueLight())
											.followTrajectorySequence(drive ->
																				drive.trajectorySequenceBuilder(Blue1)
																						//start editing here
																						.setTangent(270)
																						.splineToLinearHeading(new Pose2d(-59, Blue1.getY(), Math.toRadians(270)), Math.toRadians(90))
																						.lineToLinearHeading(new Pose2d(-32, 23, Math.toRadians(0)))
																						.lineToLinearHeading(new Pose2d(-59, 35, Math.toRadians(0)))
																						.build()
											);

		// For the first blue location
		// TODO: This
		RoadRunnerBotEntity blue2 = new DefaultBotBuilder(meepMeep)
											.setConstraints(RobotConstraints)
											.setColorScheme(new ColorSchemeBlueLight())
											.followTrajectorySequence(drive ->
																				drive.trajectorySequenceBuilder(Blue2)
																					//start editing here
																						// .lineToLinearHeading(new Pose2d(7.75, 23, Math.toRadians(180)))
																						.lineToLinearHeading(new Pose2d(-32, 23, Math.toRadians(0)))
																						.setTangent(180)
																						.splineToSplineHeading(new Pose2d(-15, 0, Math.toRadians(0)), Math.toRadians(0))
																						.splineToSplineHeading(new Pose2d(12, 9, Math.toRadians(0)), Math.toRadians(0))
																						.strafeLeft(30)
																						.forward(30)
																						.build()
											);

		// For the second blue location
		// TODO: This
		RoadRunnerBotEntity blue3 = new DefaultBotBuilder(meepMeep)
											.setConstraints(RobotConstraints)
											.setColorScheme(new ColorSchemeBlueLight())
											.followTrajectorySequence(drive ->
																			  drive.trajectorySequenceBuilder(Blue3)
																					  // Start editing here
																					  .splineToSplineHeading(new Pose2d(7, 23, Math.toRadians(180)), Math.toRadians(270))
																					  .strafeRight(19)
																					  .lineToLinearHeading(new Pose2d(7, 43, Math.toRadians(0)))
																					  .forward(35)

																					  .build()
											);

		RoadRunnerBotEntity blue4 = new DefaultBotBuilder(meepMeep)
											.setConstraints(RobotConstraints)
											.setColorScheme(new ColorSchemeRedLight())
											.followTrajectorySequence(drive ->
																				drive.trajectorySequenceBuilder(Blue4)
																						.lineToLinearHeading(new Pose2d(7, 23, Math.toRadians(180)))
																						.lineToLinearHeading(new Pose2d(5, 0, Math.toRadians(180)))
																						.setTangent(180)
																						.splineToSplineHeading(new Pose2d(-32, 23, Math.toRadians(180)), Math.toRadians(90))
																						.splineToSplineHeading(new Pose2d(-58, 35, Math.toRadians(0)), Math.toRadians(90))

																						.build()
											);

		// For the first red location
		// TODO: This
		RoadRunnerBotEntity red1 = new DefaultBotBuilder(meepMeep)
											.setConstraints(RobotConstraints)
										   .setColorScheme(new ColorSchemeRedDark())
											.followTrajectorySequence(drive ->
																			  drive.trajectorySequenceBuilder(Red1)
																					  // Start editing here
																					  .setTangent(270)
																					  .splineToLinearHeading(new Pose2d(-59, Red1.getY(), Math.toRadians(90)), Math.toRadians(270))
																					  .lineToLinearHeading(new Pose2d(-32, -23, Math.toRadians(0)))
																					  .setTangent(180)
																					  .splineToSplineHeading(new Pose2d(-59, -35, Math.toRadians(0)), Math.toRadians(0))





																					  .build()
											);

		// For the second red location
		// TODO: This
		RoadRunnerBotEntity red2 = new DefaultBotBuilder(meepMeep)
											.setConstraints(RobotConstraints)
										   .setColorScheme(new ColorSchemeRedLight())
											.followTrajectorySequence(drive ->
																			  drive.trajectorySequenceBuilder(Red2)
																					  // Start editing here
																					  .lineToLinearHeading(new Pose2d(-32, -23, Math.toRadians(0)))
																					  .setTangent(90)
																					  .splineToLinearHeading(new Pose2d(-20, 0, Math.toRadians(0)), Math.toRadians(270))
																					  .setTangent(0)
																					  .splineToLinearHeading(new Pose2d(12, -11, Math.toRadians(0)), Math.toRadians(270))
																					  .strafeRight(30)
																					  .forward(30)
																						.build()

											);

		RoadRunnerBotEntity red3 = new DefaultBotBuilder(meepMeep)
											.setConstraints(RobotConstraints)
											.setColorScheme(new ColorSchemeRedLight())
											.followTrajectorySequence(drive ->
																				drive.trajectorySequenceBuilder(Red3)
																						.lineToLinearHeading(new Pose2d(8, -23, Math.toRadians(180)))
																						.strafeLeft(19)
																						.lineToLinearHeading(new Pose2d(8, -43, Math.toRadians(0)))
																						.forward(30)
																						.build()
											);

		RoadRunnerBotEntity red4 = new DefaultBotBuilder(meepMeep)
											.setConstraints(RobotConstraints)
											.setColorScheme(new ColorSchemeRedLight())
											.followTrajectorySequence(drive ->
																				drive.trajectorySequenceBuilder(Red4)
																						.lineToLinearHeading(new Pose2d(10, -23, Math.toRadians(180)))
																						.setTangent(90)
																						.splineToLinearHeading(new Pose2d(11, 0, Math.toRadians(180)), Math.toRadians(0))
																						.forward(50)
																						.strafeLeft(35)
																						.lineToLinearHeading(new Pose2d(-60, -35, Math.toRadians(0)))
																						.build());

		
		meepMeep.setBackground(MeepMeep.Background.FIELD_FREIGHTFRENZY_ADI_DARK)
				.setDarkMode(true)
				.setBackgroundAlpha(0.95f)
				// Change this one to the name of your variable
//				.addEntity(example)
				.addEntity(blue1)
				.addEntity(blue2)
				.addEntity(blue3)
				.addEntity(blue4)
				.addEntity(red1)
				.addEntity(red2)
				.addEntity(red3)
				.addEntity(red4)
				.start();
	}
}