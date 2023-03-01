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
    private double wristOffset = Arm.WRIST_PARALLEL_OFFSET + Arm.WRIST_DEFAULT_OFFSET;

    private double armAngle;
    private double wristAngle;
    private double clawPos;
    private boolean pumpMode;
    private boolean bleed;

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
        this.armIO.setShoulderAngle(this.armAngle);
        this.armIO.setWristAngle(this.wristAngle); // remember wrist offset
        this.clawIO.setClawPosition(this.clawPos);
        this.clawIO.setLeftPump(false);
        this.clawIO.setMidPump(this.pumpMode);
        this.clawIO.setRightPump(false);
        this.clawIO.setLeftValve(this.bleed);
        this.clawIO.setRightValve(this.bleed);

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
     * Sets wrist offset in degrees from encoder
     * 
     * @param angle
     */
    public void setWristOffset(double angle) {
        this.wristOffset = Arm.WRIST_PARALLEL_OFFSET + Arm.WRIST_DEFAULT_OFFSET + angle;
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
        this.armAngle = Math.toDegrees(Math.acos((Arm.SHOULDER_HEIGHT - this.endEffectorHeight) / Arm.ARM_LENGTH))
                + Arm.SHOULDER_DEFAULT_OFFSET;
        this.wristAngle = this.armAngle + this.wristOffset;
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
        // this.pumpMode = angle != 0;

    }

    /**
     * @return Claw open vs closed angle
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
     * Set bleed state
     * 
     * @param state
     */
    public void setBleedMode(boolean state) {
        this.bleed = state;
    }

    /**
     * Get bleed state
     * 
     * @return
     */
    public boolean getBleedMode() {
        return this.bleed;
    }

    /**
     * Sets arm to preset position
     * 
     * @param preset
     */
    public void goToPreset(ArmPresets preset) {
        this.setShoulderAngle(preset.wristHeight);
        this.setWristAngle(preset.wristOffset);
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