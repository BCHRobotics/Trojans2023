package frc.io;

import frc.robot.Constants.Misc;
import frc.robot.Constants.Chassis;
import frc.util.devices.Controller;
import frc.util.devices.Controller.Axis;
import frc.util.devices.Controller.Side;

public class DriverInput {

	private static Controller driver = new Controller(Misc.DRIVER_PORT);

	/**
	 * Get the driver controller
	 * 
	 * @return Driver controller
	 */
	public static Controller getController() {
		return driver;
	}

	/**
	 * Get the maximum drive speed in percent decimal 0 --> 1
	 * 
	 * @return Max drive speed
	 */
	public static double getDriveMaxSpeed() {
		return Chassis.MAX_OUTPUT - (driver.getLeftTriggerAxis() * Chassis.MAX_INTERVAL)
				+ (driver.getRightTriggerAxis() * Chassis.MAX_INTERVAL);
	}

	/**
	 * Get the drive speed forward and backward in percent decimal -1 --> 1
	 * 
	 * @return Drive forward or backward speed
	 */
	public static double getDriveFrwd() {
		return driver.getJoystick(Side.LEFT, Axis.Y) * getDriveMaxSpeed();
	}

	/**
	 * Get the drive speed for turning in percent decimal -1 --> 1
	 * 
	 * @return Drive turning speed
	 */
	public static double getDriveTurn() {
		return driver.getJoystick(Side.RIGHT, Axis.X) * getDriveMaxSpeed();
	}

	/**
	 * Get the brake state boolean
	 * 
	 * @return Brake state
	 */
	public static boolean getDriveBrakes() {
		return driver.getLeftBumper() || driver.getRightBumper();
	}

	/**
	 * Get whether or not the drive should be in balance mode
	 * 
	 * @return Balance mode
	 */
	public static boolean getBalanceMode() {
		return driver.getYButton();
	}

}
