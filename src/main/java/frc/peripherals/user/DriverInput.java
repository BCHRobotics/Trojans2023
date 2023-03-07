package frc.peripherals.user;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import frc.peripherals.user.Controller.Axis;
import frc.peripherals.user.Controller.Side;
import frc.robot.Constants.Chassis;
import frc.robot.Constants.Misc;

public final class DriverInput {

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
		return Chassis.MAX_OUTPUT + ((driver.getRightTriggerAxis() * Chassis.MAX_INTERVAL)
				- (driver.getLeftTriggerAxis() * Chassis.MAX_INTERVAL));
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
		return (driver.getJoystick(Side.RIGHT, Axis.X) * (getDriveMaxSpeed() - 0.15));
	}

	/**
	 * Get the brake state boolean
	 * 
	 * @return Brake state
	 */
	public static boolean getBrakeMode() {
		return driver.getLeftBumper() || driver.getRightBumper();
	}

	/**
	 * Get the Automatic braking system
	 * 
	 * @return ABS state
	 */
	public static boolean getABS() {
		return driver.getLeftBumper();
	}

	/**
	 * Get whether or not the drive should be in balance mode
	 * 
	 * @return Balance mode
	 */
	public static boolean getBalanceMode() {
		return driver.getYButton();
	}

	/**
	 * Get whether or not the drive should be aligning the robot with the target
	 * 
	 * @return Align Mode
	 */
	public static boolean getTurnAlignPressed() {
		return driver.getAButtonPressed();
	}

	/**
	 * Get whether or not the drive should be aligning the robot with the target
	 * 
	 * @return Align Mode
	 */
	public static boolean getTurnAlign() {
		return driver.getAButton();
	}

	/**
	 * Get the Driver trun left boolean
	 * 
	 * @return Driver X button
	 */
	public static boolean getTurnLeftPressed() {
		return driver.getXButtonPressed();
	}

	/**
	 * Get the Driver trun left boolean
	 * 
	 * @return Driver X button
	 */
	public static boolean getTurnLeft() {
		return driver.getXButton();
	}

	/**
	 * Get the Driver trun right boolean
	 * 
	 * @return Driver B button
	 */
	public static boolean getTurnRightPressed() {
		return driver.getBButtonPressed();
	}

	/**
	 * Get the Driver trun right boolean
	 * 
	 * @return Driver B button
	 */
	public static boolean getTurnRight() {
		return driver.getBButton();
	}

	/**
	 * Get the Driver limelight test
	 * 
	 * @return Driver d pad
	 */
	public static int getDPad(){
		return driver.getPOV();
	}

	/**
	 * Set driver controller rumble mode
	 * 
	 * @param type      Left, Right, Both
	 * @param intensity 0 --> 1
	 */
	public static void setRumble(RumbleType type, double intensity) {
		driver.setRumble(type, intensity);
	}

}
