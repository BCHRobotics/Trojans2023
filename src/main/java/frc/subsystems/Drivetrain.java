package frc.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.io.subsystems.DriveIO;
import frc.robot.Constants;
import frc.util.control.PID;
import frc.util.devices.Gyro;

public class Drivetrain implements Subsystem {

    private static Drivetrain instance;

    private boolean enabled = Constants.DRIVE_ENABLED;
    private boolean gyroEnabled = Constants.GYRO_ENABLED;

    private enum DriveState {
        OUTPUT,
        POSITION,
        BALANCE,
    }

    private DriveIO driveIO;

    // Objects for balancing
    private PID gyroPid;
    private Gyro gyro;

    // states
    private DriveState currentState = DriveState.POSITION;

    private double leftOut;
    private double rightOut;

    private double posLeft;
    private double posRight;

    private boolean brakeMode;

    /**
     * Get the instance of the Drive, if none create a new instance
     * 
     * @return instance of the Drive
     */
    public static Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
        }
        return instance;
    }

    protected Drivetrain() {
        if (!enabled)
            return;

        this.firstCycle();
    }

    @Override
    public void firstCycle() {
        if (!enabled)
            return;

        this.driveIO = DriveIO.getInstance();
        if (gyroEnabled) {
            // Objects for balancing
            this.gyroPid = new PID(Constants.GYRO_CONSTANTS);
            this.gyro = new Gyro(SerialPort.Port.kUSB);
            this.gyro.resetGyroPosition();
        }
        this.resetEncoderPosition();
    }

    @Override
    public void run() {
        if (!enabled)
            return;

        SmartDashboard.putString("DRIVE_STATE", this.currentState.toString());

        switch (currentState) {
            case OUTPUT:
                this.driveIO.setDriveLeft(this.leftOut);
                this.driveIO.setDriveRight(this.rightOut);
                break;
            case POSITION:
                this.driveIO.setDriveLeftPos(this.posLeft);
                this.driveIO.setDriveRightPos(this.posRight);
                break;
            case BALANCE:
                this.driveIO.setDriveLeftPos(this.leftOut);
                this.driveIO.setDriveRightPos(this.rightOut);
                break;
            default:
                this.disable();
                break;
        }

        this.driveIO.brakeMode(this.currentState != DriveState.OUTPUT ? true : this.brakeMode);
    }

    @Override
    public void disable() {
        if (!enabled)
            return;

        this.driveIO.stopAllOutputs();
    }

    /**
     * Reset encoders to zero position
     */
    public void resetEncoderPosition() {
        if (!enabled)
            return;

        this.driveIO.resetInputs();
    }

    @Deprecated
    /**
     * Set drive mode:
     * 
     * @param state
     */
    public void setDriveMode(DriveState state) {
        if (!enabled)
            return;

        this.currentState = state;
    }

    /**
     * @return Drive mode
     *         <ul>
     *         <li>Output Mode</li>
     *         <li>Position Mode</li>
     *         <li>Balance Mode</li>
     *         </ul>
     */
    public DriveState getDriveMode() {
        if (!enabled)
            return null;

        return this.currentState;
    }

    /**
     * Sets output to drive
     * 
     * @param frwd percent output [-1 to 1] for forward/backward movement
     * @param turn percent output [-1 to 1] for turn movement
     */
    public void setOutput(double frwd, double turn) {
        if (!enabled)
            return;

        this.currentState = DriveState.OUTPUT;

        this.leftOut = (frwd + turn);
        this.rightOut = (frwd - turn);
    }

    /**
     * Sets chasis position using relative encoders
     * 
     * @param left  relative position in inches
     * @param right relative position in inches
     */
    public void setPosition(double left, double right) {
        if (!enabled)
            return;

        this.currentState = DriveState.POSITION;

        this.posLeft = left;
        this.posRight = right;
    }

    /**
     * Gets chasis position using relative encoders
     * 
     * @return left relative position in inches
     */
    public double getLeftPosition() {
        if (!enabled)
            return 0;

        return this.posLeft;
    }

    /**
     * Gets chasis position using relative encoders
     * 
     * @return right relative position in inches
     */
    public double getRightPosition() {
        if (!enabled)
            return 0;

        return this.posRight;
    }

    /**
     * Sets braking mode on drive motors
     * 
     * @param state
     */
    public void setBrakes(boolean state) {
        if (!enabled)
            return;

        this.brakeMode = state;
    }

    /**
     * @return Drive motor brake state
     */
    public boolean getBrakes() {
        if (!enabled)
            return false;

        return this.brakeMode;
    }

    /**
     * Turns drivetrain/chasis by a provided angle using smart motion
     * 
     * @param angle in degrees
     */
    public void seekTarget(double angle) {
        if (!enabled)
            return;

        SmartDashboard.putNumber("Vision θ", angle);

        this.setPosition(this.driveIO.getDriveL1Encoder().getPosition() + (angle / Constants.CHASIS_LEFT_CONVERSION),
                this.driveIO.getDriveR1Encoder().getPosition() + (angle / Constants.CHASIS_RIGHT_CONVERSION));
    }

    /**
     * Uses PID to balance robot on charging station
     */
    public void balance() {
        if (!gyroEnabled)
            return;

        this.currentState = DriveState.BALANCE;

        this.gyroPid.setTarget(0);
        this.gyroPid.referenceTimer();
        this.gyroPid.setInput(gyro.getAngle());
        this.gyroPid.calculate();
        this.setOutput(this.gyroPid.getOutput(), 0);
    }

    /**
     * Resets balance method variables to remain idle when not balancing
     */
    public void unrestrained() {
        if (!gyroEnabled)
            return;

        this.gyroPid.resetTimer();
        this.gyroPid.resetError();
    }
}