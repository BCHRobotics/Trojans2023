package frc.subsystems;

// Import required classes
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.io.subsystems.DriveIO;
import frc.robot.Constants;
import frc.util.control.PIDControl;
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
    private PIDControl gyroPid;
    private Gyro gyro;

    // Objects for target seeking
    private PIDControl seekPID;

    // Drive states
    private DriveState currentState = DriveState.POSITION;

    // Motor output variables
    private double leftOut;
    private double rightOut;

    // Drive position variables
    private double posLeft;
    private double posRight;

    // Drive brakes variable
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

        this.driveIO = DriveIO.getInstance();

        if (gyroEnabled) {
            // Objects for balancing
            this.gyroPid = new PIDControl(Constants.GYRO_CONSTANTS);
            this.gyro = new Gyro(Constants.GYRO_PORT);
        }

        // Objects for target seeking
        this.seekPID = new PIDControl(Constants.SEEK_CONSTANTS);

        this.firstCycle();
    }

    @Override
    public void firstCycle() {
        if (!enabled)
            return;
        if (gyroEnabled)
            this.gyro.resetGyroPosition();
        this.resetEncoderPosition();
    }

    @Override
    public void run() {
        if (!enabled)
            return;

        SmartDashboard.putString("DRIVE_STATE", this.currentState.toString());

        SmartDashboard.putNumber("DriveL Pos", this.getLeftPosition());
        SmartDashboard.putNumber("DriveR Pos", this.getRightPosition());

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

    /**
     * Set drive mode:
     * 
     * @param state
     */
    @Deprecated
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
     * @param frwd percent output [-1 --> 1] for forward/backward movement
     * @param turn percent output [-1 --> 1] for turn movement
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

        return this.driveIO.getDriveL1Encoder().getPosition();
    }

    /**
     * Gets chasis position using relative encoders
     * 
     * @return right relative position in inches
     */
    public double getRightPosition() {
        if (!enabled)
            return 0;

        return this.driveIO.getDriveR1Encoder().getPosition();
    }

    /**
     * Gets chasis velocity using relative encoders
     * 
     * @return left relative velocity in rpm
     */
    public double getLeftVelocity() {
        if (!enabled)
            return 0;

        return this.driveIO.getDriveL1Encoder().getVelocity();
    }

    /**
     * Gets chasis velocity using relative encoders
     * 
     * @return right relative velocity in rpm
     */
    public double getRightVelocity() {
        if (!enabled)
            return 0;

        return this.driveIO.getDriveR1Encoder().getVelocity();
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
     * Sets chasis turn using relative encoders and smart motion
     * 
     * @param angle from The Normal Line in degrees
     */
    public void setYaw(double angle) {
        if (!enabled)
            return;

        SmartDashboard.putNumber("Drive Heading θ", angle);

        this.currentState = DriveState.POSITION;

        this.setPosition(this.getLeftPosition() + (angle * Constants.CHASIS_TURN_CONVERSION),
                this.getRightPosition() - (angle * Constants.CHASIS_TURN_CONVERSION));
    }

    /**
     * Turns drivetrain/chasis by a provided angle using PID
     * 
     * @param angle in degrees
     */
    public void setYawPID(double angle) {
        SmartDashboard.putNumber("Drive Heading Φ", angle);

        this.seekPID.setSetpoint(0);
        this.setOutput(this.seekPID.calculate(angle), 0);
    }

    /**
     * Resets seekTargetPID() variables to remain idle
     */
    public void yawIdle() {
        this.seekPID.reset();
    }

    /**
     * Uses PID to balance robot on charging station
     */
    public void balancePID(boolean trigger) {
        if (!gyroEnabled)
            return;
        if (trigger) {
            this.currentState = DriveState.BALANCE;
            this.gyroPid.enableContinuousInput(-20, 20);
            this.gyroPid.setSetpoint(0);
            this.setOutput(this.gyroPid.calculate(this.gyro.getAngle()), 0);
        } else
            this.unrestrained();
    }

    /**
     * Resets balance method variables to remain idle when not balancing
     */
    private void unrestrained() {
        if (!gyroEnabled)
            return;
        this.gyroPid.disableContinuousInput();
        this.gyroPid.reset();
    }

}