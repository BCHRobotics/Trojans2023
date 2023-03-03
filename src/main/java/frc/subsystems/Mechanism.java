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
    private double shoulderOffset;
    private double wristOffset = Arm.WRIST_DEFAULT_OFFSET;

    private double armAngle;
    private double wristAngle;
    private double clawPos;
    private boolean pumpMode;

    private ArmPresets preset;

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
        this.armIO.setWristAngle(this.wristAngle + this.wristOffset);
        this.clawIO.setClawPosition(this.clawPos);
        this.clawIO.setPump(this.pumpMode);

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
        this.armAngle = 0;
        this.wristAngle = 0;
        this.clawPos = 0;
        this.pumpMode = false;
        this.resetStatusLED();
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
        switch (ID) {
            case 0:
                this.resetPosition();
                break;
            case 1:
                this.goToPreset(Arm.STOWED_AWAY);
                break;
            case 2:
                this.goToPreset(Arm.GROUND_DROPOFF);
                break;
            case 3:
                this.goToPreset(Arm.MID_DROPOFF);
                break;
            case 4:
                this.goToPreset(Arm.TOP_DROPOFF);
                break;
            case 5:
                this.goToPreset(Arm.STATION_PICKUP);
                break;
            default:
                this.resetPosition();
        }
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
     * Retrieves last call to preset position ID
     * 
     * @return ArmPresets (Wrist Height, Wrist Offset) ID
     */
    public int getPresetID() {
        return 0;
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

    /**
     * Set status led state
     * 
     * @param mode
     */
    public void setStatusLED(int mode) {
        resetStatusLED();

        switch (mode) {
            case 1:
                this.coneLED = true;
                break;
            case 2:
                this.cubeLED = true;
                break;
            case 3:
                this.blinkCone = true;
                break;
            case 4:
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