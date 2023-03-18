package frc.subsystems;

import frc.peripherals.robot.ShoulderIO;
import frc.peripherals.robot.WristIO;
import frc.peripherals.robot.ClawIO;
import frc.peripherals.robot.LEDIO;
import frc.robot.Constants.*;
import frc.robot.Constants.MISC.LED_STATE;
import frc.util.control.ArmPresets;

import java.lang.Math;

public class Mechanism extends Subsystem {
    private static Mechanism instance;

    private ShoulderIO shoulderIO;
    private WristIO wristIO;
    private ClawIO clawIO;
    private LEDIO ledIO;

    private double endEffectorHeight;
    private double shoulderOffset;
    private double wristOffset = WRIST.DEFAULT_OFFSET;

    private double shoulderAngle;
    private double wristAngle;
    private double clawSpeed;

    private ArmPresets preset;

    private LED_STATE ledState;

    /**
     * Get the instance of the Mechanism, if none create a new instance
     * 
     * @return instance of the Mechanism
     */
    public static Mechanism getInstance() {
        if (instance == null) {
            instance = new Mechanism();
        }
        return instance;
    }

    protected Mechanism() {
        this.init();
    }

    @Override
    public void init() {
        this.shoulderIO = ShoulderIO.getInstance();
        this.wristIO = WristIO.getInstance();
        this.clawIO = ClawIO.getInstance();
        this.ledIO = LEDIO.getInstance();
        this.resetPosition();
    }

    @Override
    public void run() {
        this.shoulderIO.setShoulderAngle(this.shoulderAngle);
        this.wristIO.setWristAngle((this.wristAngle + this.wristOffset), this.shoulderAngle);
        this.clawIO.setClawSpeed(this.clawSpeed);
        this.ledIO.setLEDState(this.ledState);
    }

    @Override
    public void disable() {
        this.shoulderIO.disable();
        this.wristIO.disable();
        this.clawIO.disable();
    }

    /**
     * Reset appendages to zero position
     */
    public void resetPosition() {
        this.shoulderAngle = 0;
        this.wristAngle = 0;
        this.clawSpeed = 0;
        this.ledIO.init();
    }

    /**
     * Sets raw shoulder angle in degrees from zero position
     * 
     * @param angle
     */
    public void setShoulderAngle(double angle) {
        this.shoulderAngle = angle;
    }

    /**
     * @return Shoulder anlge in degrees from encoder
     */
    public double getShoulderAngle() {
        return this.shoulderIO.getShoulderEncoder().getPosition();
    }

    /**
     * Sets raw wrist angle in degrees from zero position
     * 
     * @param angle
     */
    public void setWristAngle(double angle) {
        this.wristAngle = angle;
    }

    /**
     * @return Wrist angle in degrees from encoder
     */
    public double getWristAngle() {
        return this.wristIO.getWristEncoder().getPosition();
    }

    /**
     * Sets shoulder offset in degrees from encoder
     * 
     * @param angle
     */
    public void setShoulderOffset(double angle) {
        this.shoulderOffset = angle;
    }

    /**
     * @return Shoulder offset in degrees
     */
    public double getShoulderOffset() {
        return this.shoulderOffset;
    }

    /**
     * Sets wrist offset in degrees from encoder
     * 
     * @param angle
     */
    public void setWristOffset(double angle) {
        this.wristOffset = angle;
    }

    /**
     * @return Wrist offset in degrees
     */
    public double getWristOffset() {
        return this.wristOffset;
    }

    /**
     * Sets end effector height in inches
     * 
     * @param position
     */
    public void setWristHeight(double height) {
        this.endEffectorHeight = height + this.shoulderOffset;
        this.shoulderAngle = Math.toDegrees(
                Math.acos((SHOULDER.HEIGHT - WRIST.HEIGHT_OFFSET - this.endEffectorHeight)
                        / ROBOT.ARM_LENGTH));
        this.wristAngle = this.shoulderAngle + WRIST.PARALLEL_OFFSET;
    }

    /**
     * @return End effector height in inches
     */
    public double getWristHeight() {
        return this.endEffectorHeight;
    }

    /**
     * Sets Claw speed in percent output
     * 
     * @param speed
     */
    public void setClawSpeed(double speed) {
        this.clawSpeed = speed;

    }

    /**
     * @return Claw velocity (RPM)
     */
    public double getClawSpeed() {
        return this.clawIO.getClawEncoder().getVelocity();
    }

    /**
     * Sets arm to preset position
     * 
     * @param preset
     */
    public void goToPreset(ArmPresets preset) {
        this.preset = preset;
        this.setWristHeight(preset.wristHeight);
        this.setWristOffset(preset.wristOffset);
    }

    /**
     * Sets arm to preset position
     * 
     * @param ID
     */
    public void goToPreset(int ID) {
        // switch (ID) {
        // case 0:
        // this.goToPreset(Arm.DEFAULT);
        // break;
        // case 1:
        // this.goToPreset(Arm.TRANSPORT);
        // break;
        // case 2:
        // this.goToPreset(Arm.GROUND);
        // break;
        // case 3:
        // this.goToPreset(Arm.MID);
        // break;
        // case 4:
        // this.goToPreset(Arm.STATION);
        // break;
        // case 5:
        // this.goToPreset(Arm.TOP);
        // break;
        // default:
        // this.resetPosition();
        // }
        this.goToPreset(ROBOT.PRESETS[ID]);
    }

    /**
     * Retrieves last call to preset position
     * 
     * @return ArmPresets (Wrist Height, Wrist Offset)
     */
    public ArmPresets getPreset() {
        return this.preset;
    }

    /**
     * Set status led state
     * 
     * @param status
     */
    public void setLEDState(LED_STATE status) {
        this.ledState = status;
    }

    /**
     * Set status led state
     * 
     * @param status
     */
    public void setLEDState(int status) {

        switch (status) {
            case 1:
                this.ledState = LED_STATE.CONE;
                break;
            case 2:
                this.ledState = LED_STATE.CUBE;
                break;
            case 3:
                this.ledState = LED_STATE.BOTH;
                break;
            case 4:
                this.ledState = LED_STATE.CUBE_BLINK;
                break;
            case 5:
                this.ledState = LED_STATE.CONE_BLINK;
                break;
            case 6:
                this.ledState = LED_STATE.BOTH_BLINK;
                break;
            default:
                this.ledIO.init();
                break;
        }

    }

}