package frc.subsystems;

import frc.peripherals.robot.ArmIO;
import frc.peripherals.robot.ClawIO;
import frc.robot.Constants.Arm;
import frc.robot.Constants.Misc;
import frc.util.control.ArmPresets;

import java.lang.Math;

public class Mechanism implements Subsystem {
    private static Mechanism instance;

    private ArmIO armIO;
    private ClawIO clawIO;

    private double endEffectorHeight;
    private double wristOffset = Arm.WRIST_DEFAULT_OFFSET;

    private double armPos;
    private double wristPos;
    private double clawAngle;
    private boolean pumpMode;

    private boolean coneLED, cubeLED, blinkCone, blinkCube;
    private long previousTime, currentTime;

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
        this.resetPosition();
    }

    @Override
    public void run() {
        this.armIO.setShoulderAngle(this.armPos);
        this.armIO.setWristAngle(this.wristPos); // remember wrist offset
        this.clawIO.setClawAngle(this.clawAngle);
        this.clawIO.setLeftPump(this.pumpMode);
        this.clawIO.setMidPump(this.pumpMode);
        this.clawIO.setRightPump(this.pumpMode);
        this.clawIO.setLeftValve(!this.pumpMode);
        this.clawIO.setRightValve(!this.pumpMode);

        this.currentTime = System.currentTimeMillis();

        if ((this.currentTime >= this.previousTime + Misc.BLINK_INTERVAL)) {
            if (this.blinkCone)
                this.coneLED = !this.coneLED;
            if (this.blinkCube)
                this.cubeLED = !this.cubeLED;
            this.previousTime = this.currentTime;
        }

        this.clawIO.setStatusLED(this.coneLED, this.cubeLED);
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
        this.armIO.resetPositions();
        this.clawIO.resetPosition();
    }

    /**
     * Sets raw shoulder angle in degrees from zero position
     * 
     * @param angle
     */
    public void setShoulderAngle(double angle) {
        this.armPos = angle;
    }

    /**
     * @return Shoulder anlge in degrees from encoder
     */
    public double getShoulderAngle() {
        return this.armIO.getShoulderEncoder().getPosition();
    }

    /**
     * Sets wrist angle in degrees from zero position
     * 
     * @param angle
     */
    public void setWristAngle(double angle) {
        this.wristPos = angle;
    }

    /**
     * @return Wrist angle in degrees from encoder
     */
    public double getWristAngle() {
        return this.armIO.getWristEncoder().getPosition();
    }

    /**
     * Sets wrist offset in degrees from encoder
     * 
     * @param angle
     */
    public void setWristOffset(double angle) {
        this.wristOffset = Arm.WRIST_DEFAULT_OFFSET + angle;
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
        this.endEffectorHeight = height;
        this.armPos = Math.acos(Math.toRadians((Arm.SHOULDER_HEIGHT - this.endEffectorHeight) / Arm.ARM_LENGTH));
        this.wristPos = this.armPos + this.wristOffset;
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
     * @param angle
     */
    public void setClawAngle(double angle) {
        this.clawAngle = angle;
        this.pumpMode = angle != 0;

    }

    /**
     * @return Claw open vs closed angle
     */
    public double getClawAngle() {
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
        this.setWristHeight(preset.wristHeight);
        this.setWristOffset(preset.wristOffset);
    }

    /**
     * Set status led state
     * 
     * @param state
     */
    public void setStatusLED(Misc.StatusLED state) {
        resetStatusLED();

        switch (state) {
            case CONE:
                this.coneLED = true;
                break;
            case CUBE:
                this.cubeLED = true;
                break;
            case CONE_BLINK:
                this.blinkCone = true;
                break;
            case CUBE_BLINK:
                this.blinkCube = true;
                break;
            default:
                resetStatusLED();
                break;
        }

    }

    private void resetStatusLED() {
        this.coneLED = false;
        this.cubeLED = false;
        this.blinkCone = false;
        this.blinkCube = false;
    }
}