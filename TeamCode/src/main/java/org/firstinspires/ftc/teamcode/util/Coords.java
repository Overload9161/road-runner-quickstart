package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

/**
 * The locations of all the locations on the field
 */
public class Coords {
	// Starting Location
	public Pose2d BLUE1_START = new Pose2d(-35,61, Math.toRadians(90-15));
	public Pose2d BLUE2_START = new Pose2d(12, 61, Math.toRadians(90-15));
	public Pose2d RED1_START  = new Pose2d(-35,-61, Math.toRadians(270-15));
	public Pose2d RED2_START  = new Pose2d(12, -61, Math.toRadians(270-15));
	
	// Depos
	public Vector2d BLUE_WAREHOUSE = new Vector2d(-65, 18);
	public Vector2d RED_WAREHOUSE = new Vector2d(-65 , -30);
	
	// Tower
	public Vector2d BLUE_TOWER_L1 = new Vector2d(-25,22);
	public Vector2d BLUE_TOWER_L2;
	public Vector2d RED_TOWER_L1 = new Vector2d(-25, 22);
	public Vector2d RED_TOWER_L2;

}