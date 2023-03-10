package frc.subsystems;

// Import required classes
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.peripherals.robot.DriveIO;
import frc.peripherals.robot.Gyro;
import frc.robot.Constants.Chassis;
import frc.robot.Constants.Features;
import frc.robot.Constants.Misc;
import frc.util.control.PID;
import frc.util.imaging.Limelight;
import frc.util.imaging.Limelight.LimelightTargetType;

public class Drivetrain implements Subsystem {

    private static Drivetrain instance;

    private boolean enabled = Features.DRIVE_ENABLED;
    private boolean gyroEnabled = Features.GYRO_ENABLED;

    public enum DriveState {
        OUTPUT,
        POSITION,
        BALANCE,
    }

    private DriveIO driveIO;

    // Objects for balancing
    private PID gyroPid;
    private Gyro gyro;

    // Objects for target seeking
    private PID seekPID;

    // Limelight Objects needed
    protected Limelight limelight;

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

    private double phi;
    private double theta;
    private double x;
    private double y;

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
            this.gyroPid = new PID(Chassis.GYRO_CONSTANTS);

            this.gyro = new Gyro(Chassis.GYRO_PORT);
        }

        // Objects for target seeking
        this.seekPID = new PID(Chassis.SEEK_CONSTANTS);

        this.limelight = Limelight.getInstance();

        this.firstCycle();
    }

    @Override
    public void firstCycle() {
        if (!enabled)
            return;

        if (gyroEnabled)
            this.gyro.reset();

        this.limelight.setDesiredTarget(LimelightTargetType.CONE);
        this.limelight.setPipeline();
        this.limelight.setLedMode(1);

        this.gyroPid.pushConstantsToDashboard("Gyro");

        this.resetEncoderPosition();
    }

    @Override
    public void run() {
        if (!enabled)
            return;

        SmartDashboard.putString("Drive Mode", this.currentState.toString());

        SmartDashboard.putNumber("Left Travel", this.getLeftPosition());
        SmartDashboard.putNumber("Right Travel", this.getRightPosition());

        switch (currentState) {
            case OUTPUT:
                this.driveIO.setRampRate(true);
                this.driveIO.setDriveLeft(this.leftOut);
                this.driveIO.setDriveRight(this.rightOut);
                break;
            case POSITION:
                this.driveIO.setDriveLeftPos(this.posLeft);
                this.driveIO.setDriveRightPos(this.posRight);
                break;
            case BALANCE:
                this.driveIO.setRampRate(false);
                this.driveIO.setDriveLeft(this.leftOut);
                this.driveIO.setDriveRight(this.rightOut);
                break;
            default:
                this.disable();
                break;
        }

        this.gyroPid.retrieveDashboardConstants();
        SmartDashboard.putNumber("Pitch", this.gyro.getPitch());

        this.driveIO.brakeMode(this.currentState != DriveState.OUTPUT ? true : this.brakeMode);
    }

    @Override
    public void disable() {
        if (!enabled)
            return;

        this.driveIO.stopAllOutputs();
    }

    public Limelight getLimelight() {
        return this.limelight;
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
     * Sets chasis position using relative encoders
     * 
     * @param left  relative position in inches
     * @param right relative position in inches
     */
    public void setPosition(double left, double right, double angle) {
        if (!enabled)
            return;

        this.currentState = DriveState.POSITION;

        angle *= Chassis.TURNING_CONVERSION;

        this.posLeft = left + angle;
        this.posRight = right - angle;
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
     * @return left relative velocity in inches per second
     */
    public double getLeftVelocity() {
        if (!enabled)
            return 0;

        return this.driveIO.getDriveL1Encoder().getVelocity();
    }

    /**
     * Gets chasis velocity using relative encoders
     * 
     * @return right relative velocity in inches per second
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

        // SmartDashboard.putNumber("Drive Heading θ", angle);

        angle *= Chassis.TURNING_CONVERSION;

        this.setPosition(angle, -angle);
    }

    /**
     * Aligns robot chassis with neareset detected limelight target
     * 
     */
    public void alignTarget() {
        if (!enabled)
            return;

        SmartDashboard.putNumber("Drive Heading θ", this.limelight.getTargetX());

        this.seekTarget(this.limelight.getTargetX());
    }

    /**
     * Turns drivetrain/chasis to a provided target using PID
     *
     * @param angle in degrees
     */
    public void seekTarget(double angle) {
        SmartDashboard.putNumber("Drive Heading Φ", angle);

        this.seekPID.setTarget(angle);
        this.seekPID.referenceTimer();
        this.seekPID.setInput(this.limelight.getTargetX());
        this.seekPID.calculate();
        this.setOutput(0, this.seekPID.getOutput());
        this.currentState = DriveState.POSITION;
    }

    /**
     * Returns whether or not the robot is facing the target
     */
    public boolean facingTarget() {
        if (!gyroEnabled)
            return false;
        return this.seekPID.atSetpoint();
    }

    /**
     * Resets seekTarget() variables to remain idle
     */
    public void seekIdle() {
        this.seekPID.resetTimer();
        this.seekPID.resetError();
    }

    /**
     * Uses PID to balance robot on charging station
     */
    public void balancePID() {
        if (!gyroEnabled) {
            return;
        }

        this.gyroPid.setTarget(0);
        this.gyroPid.referenceTimer();
        this.gyroPid.setInput(this.gyro.getPitch());
        this.gyroPid.calculate();
        this.setOutput(this.gyroPid.getOutput(), 0);
        this.currentState = DriveState.BALANCE;
    }

    /**
     * Returns whether or not the robot is balanced
     */
    public boolean isBalanced() {
        if (!gyroEnabled)
            return false;
        return this.gyroPid.atSetpoint();
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

    public void goToTarget() { // goes to april tag
        double angle = this.limelight.getTargetX();
        double distance = this.limelight.getTargetDistance();

        if (!this.limelight.reachedTargetX())
            this.setYaw(-angle); // turns to april tag
        else {
            this.resetEncoderPosition();
            this.setPosition(distance, distance);
        }
        // drive to april tag but leave some space to prevent ramming the april tag
        // 2 is a placeholder
    }

    public void calculatePath() {
        this.phi = (this.gyro.getYaw() > 0 ? 90 : -90) - this.gyro.getYaw();
        this.theta = this.phi - (this.limelight.getTargetX() * (this.gyro.getYaw() > 0 ? 1 : -1));
        this.x = (this.limelight.getTargetDistance() * Math.cos(theta));
        this.y = (this.limelight.getTargetDistance() * Math.sin(theta)) - Misc.LIMELIGHT_CHASSIS_OFFSET;
    }

    public void followPath() {

        if (!Misc.WITHIN_TOLERANCE(this.gyro.getYaw(), this.phi, Chassis.GYRO_TOLERANCE))
            this.setYaw(this.phi);
        else if (!Misc.WITHIN_TOLERANCE(this.getLeftPosition(), this.x, Chassis.TOLERANCE))
            this.setPosition(this.x, this.x);
        else if (!Misc.WITHIN_TOLERANCE(this.gyro.getYaw(), 0, Chassis.GYRO_TOLERANCE))
            this.setYaw(0);
        else if (!Misc.WITHIN_TOLERANCE(this.getLeftPosition(), this.y, Chassis.TOLERANCE))
            this.setPosition(this.y, this.y);

    }

    public void clearPath() {
        this.phi = 0;
        this.theta = 0;
        this.x = 0;
        this.y = 0;
    }

}
