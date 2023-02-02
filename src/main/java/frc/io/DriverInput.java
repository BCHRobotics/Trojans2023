package frc.io;

import frc.robot.Constants;
import frc.util.devices.Controller;
import frc.util.devices.Controller.Axis;
import frc.util.devices.Controller.Side;

public class DriverInput {
	private static DriverInput instance;

	private Controller driver;
	private Controller operator;

	/**
	 * Get the instance of the DriverInput, if none create a new instance
	 * 
	 * @return instance of the DriverInput
	 */
	public static DriverInput getInstance() {
		if (instance == null) {
			instance = new DriverInput();
		}
		return instance;
	}

	private DriverInput() {
		this.driver = new Controller(0);
		this.operator = new Controller(1);
	}

	/**
	 * Get the driver controller
	 * 
	 * @return Driver controller
	 */
	public Controller getDriverController() {
		return this.driver;
	}

	/**
	 * Get the operator controller
	 * 
	 * @return Operator Controller
	 */
	public Controller getOperatorController() {
		return this.operator;
	}

	/**
	 * Get the maximum drive speed in percent decimal 0 --> 1
	 * 
	 * @return Max drive speed
	 */
	public double getDriveMaxSpeed() {
		return Constants.MAX_OUTPUT - (this.driver.getLeftTriggerAxis() * Constants.MAX_INTERVAL)
				+ (this.driver.getRightTriggerAxis() * Constants.MAX_INTERVAL);
	}

	/**
	 * Get the drive speed forward and backward in percent decimal 0 --> 1
	 * 
	 * @return Drive forward or backward speed
	 */
	public double getDriveFrwd() {
		return this.driver.getJoystick(Side.LEFT, Axis.Y) * this.getDriveMaxSpeed();
	}

	/**
	 * Get the drive speed for turning in percent decimal 0 --> 1
	 * 
	 * @return Drive turning speed
	 */
	public double getDriveTurn() {
		return this.driver.getJoystick(Side.RIGHT, Axis.X) * this.getDriveMaxSpeed();
	}

	/**
	 * Get the brake state boolean
	 * 
	 * @return Brake state
	 */
	public boolean getDriveBrakes() {
		return this.driver.getLeftBumper();
	}

	/**
	 * Get whether or not the drive should be in balance mode
	 * 
	 * @return Balance mode
	 */
	public boolean getBalanceMode() {
		return this.driver.getYButton();
	}

	/**
	 * Get drivers D-Pad input for testing
	 * 
	 * @return Driver D-Pad input
	 */
	public int getDpadInput() {
		return this.driver.getPOV();
	}

}
