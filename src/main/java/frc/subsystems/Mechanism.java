package frc.subsystems;

import frc.peripherals.robot.ArmIO;
import frc.peripherals.robot.ClawIO;
import frc.peripherals.robot.LEDIO;
import frc.robot.Constants.Arm;
import frc.robot.Constants.Misc.LED_STATE;
import frc.util.control.ArmPresets;

import java.lang.Math;

public class Mechanism implements Subsystem {
    private static Mechanism instance;

    private ArmIO armIO;
    private ClawIO clawIO;
    private LEDIO ledIO;

    private double endEffectorHeight;
    private double shoulderOffset;
    private double wristOffset = Arm.WRIST_DEFAULT_OFFSET;

    private double armAngle;
    private double wristAngle;
    private double clawPos;
    private boolean pumpMode;

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
        this.firstCycle();
    }

    @Override
    public void firstCycle() {
        this.armIO = ArmIO.getInstance();
        this.clawIO = ClawIO.getInstance();
        this.ledIO = LEDIO.getInstance();
        this.resetPosition();
    }

    @Override
    public void run() {
        this.armIO.setShoulderAngle(this.armAngle);
        this.armIO.setWristAngle(this.wristAngle + this.wristOffset);
        this.clawIO.setClawPosition(this.clawPos);
        this.clawIO.setPump(this.pumpMode);
        this.ledIO.setLEDState(this.ledState);
    }

    @Override
    public void disable() {
        this.armIO.stopAllOutputs();
        this.clawIO.stopAllOutputs();
    }

    /**
     * Reset appendages to zero position
     */
    public void resetPosition() {
        this.armAngle = 0;
        this.wristAngle = 0;
        this.clawPos = 0;
        this.pumpMode = false;
        this.clawIO.recalibrateClaw();
        this.ledIO.resetInputs();
    }

    /**
     * Sets raw shoulder angle in degrees from zero position
     * 
     * @param angle
     */
    public void setShoulderAngle(double angle) {
        this.armAngle = angle;
    }

    /**
     * @return Shoulder anlge in degrees from encoder
     */
    public double getShoulderAngle() {
        return this.armIO.getShoulderEncoder().getPosition();
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
        return this.armIO.getWristEncoder().getPosition();
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
        this.armAngle = Math.toDegrees(
                Math.acos((Arm.SHOULDER_HEIGHT - Arm.WRIST_HEIGHT_OFFSET - this.endEffectorHeight) / Arm.ARM_LENGTH));
        this.wristAngle = this.armAngle + Arm.WRIST_PARALLEL_OFFSET;
    }

    /**
     * @return End effector height in inches
     */
    public double getWristHeight() {
        return this.endEffectorHeight;
    }

    /**
     * Sets Claw position in actuator degrees
     * 
     * @param position
     */
    public void setClawPos(double position) {
        this.clawPos = position;

    }

    /**
     * @return Claw percent open (0 = open, 1 = closed)
     */
    public double getClawPos() {
        return this.clawIO.getClawEncoder().getPosition();
    }

    /**
     * Set pump state
     * 
     * @param state
     */
    public void setSuctionMode(boolean state) {
        this.pumpMode = state;
    }

    /**
     * Get pump state
     * 
     * @return
     */
    public boolean getSuctionMode() {
        return this.pumpMode;
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
        this.goToPreset(Arm.PRESETS[ID]);
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
                this.ledIO.resetInputs();
                break;
        }

    }

}