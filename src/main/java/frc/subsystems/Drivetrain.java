package frc.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.io.subsystems.DriveIO;
import frc.robot.Constants;
import frc.util.pid.PID;
import frc.util.devices.Gyro;

public class Drivetrain extends Subsystem {
    private static Drivetrain instance;

    public enum DriveState {
        OUTPUT,
        POSITION
    }

    private DriveIO driveIO;

    // Objects for balancing
    private PID gyroPid = new PID(Constants.GYRO_CONSTANTS);
    private Gyro gyro = new Gyro(SerialPort.Port.kUSB);

    // Objects for target seeking
    private PID seekPID = new PID(Constants.SEEK_CONSTANTS);

    // states
    private DriveState currentState = DriveState.POSITION;

    private double leftOut;
    private double rightOut;

    private double posLeft;
    private double posRight;

    private boolean positionMode;
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

    private Drivetrain() {
        this.firstCycle();
    }

    @Override
    public void firstCycle() {
        this.driveIO = DriveIO.getInstance();
        this.gyro.resetGyroPosition();
        this.resetPosition();
    }

    @Override
    public void run() {
        SmartDashboard.putString("DRIVE_STATE", this.currentState.toString());

        if (this.positionMode)
            this.currentState = DriveState.POSITION;
        else
            this.currentState = DriveState.OUTPUT;

        switch (currentState) {
            case OUTPUT:
                this.driveIO.setDriveLeft(this.leftOut);
                this.driveIO.setDriveRight(this.rightOut);
                break;
            case POSITION:
                this.driveIO.setDriveLeftPos(this.posLeft);
                this.driveIO.setDriveRightPos(this.posRight);
                break;
            default:
                this.disable();
                break;
        }

        this.driveIO.brakeMode(this.positionMode ? true : this.brakeMode);
    }

    @Override
    public void disable() {
        this.driveIO.stopAllOutputs();
    }

    /**
     * Reset encoders to zero position
     */
    public void resetPosition() {
        this.driveIO.resetInputs();
    }

    /**
     * Set drive position mode
     * 
     * @param state
     */
    public void setPositionMode(boolean state) {
        this.positionMode = state;
    }

    /**
     * Get drive position mode
     * 
     * @return
     */
    public boolean getPositionMode() {
        return this.positionMode;
    }

    /**
     * Sets output to drive
     * 
     * @param frwd percent output [-1 to 1] for forward/backward movement
     * @param turn percent output [-1 to 1] for turn movement
     */
    public void setOutput(double frwd, double turn) {
        this.leftOut = (frwd + turn);
        this.rightOut = (frwd - turn);
    }

    /**
     * Sets chasis position using relative encoders
     * 
     * @param left
     * @param right
     */
    public void setPosition(double left, double right) {
        this.posLeft = left;
        this.posRight = right;
    }

    /**
     * Sets braking mode on drive motors
     * 
     * @param state
     */
    public void setBrakes(boolean state) {
        this.brakeMode = state;
    }

    /**
     * @return Drive motor brake state
     */
    public boolean getBrakes() {
        return this.brakeMode;
    }

    /**
     * Turns drivetrain/chasis by a provided angle using smart motion
     * 
     * @param angle in degrees
     */
    public void seekTarget(double angle) {
        SmartDashboard.putNumber("Vision θ", angle);

        this.setPosition(this.driveIO.getDriveL1Encoder().getPosition() + (angle / Constants.CHASIS_LEFT_CONVERSION),
                this.driveIO.getDriveR1Encoder().getPosition() + (angle / Constants.CHASIS_RIGHT_CONVERSION));
    }

    /**
     * Turns drivetrain/chasis by a provided angle using PID
     * 
     * @param angle in degrees
     */
    public void seekTargetPID(double angle) {
        SmartDashboard.putNumber("Vision Φ", angle);

        this.seekPID.setTarget(0);
        this.seekPID.referenceTimer();
        this.seekPID.setInput(angle);
        this.seekPID.calculate();
        this.setOutput(this.seekPID.getOutput(), 0);
    }

    /**
     * Resets seekTargetPID() variables to remain idle
     */
    public void seekTargetIdle() {
        this.seekPID.resetTimer();
        this.seekPID.resetError();
    }

    /**
     * Uses PID to balance robot
     */
    public double balance() {
        this.gyroPid.setTarget(0);
        this.gyroPid.referenceTimer();
        this.gyroPid.setInput(gyro.getAngle());
        this.gyroPid.calculate();
        return this.gyroPid.getOutput();
    }

    /**
     * Resets balance() variables to remain idle
     */
    public void balanceIdle() {
        this.gyroPid.resetTimer();
        this.gyroPid.resetError();
    }
}