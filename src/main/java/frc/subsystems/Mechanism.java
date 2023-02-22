package frc.subsystems;

import frc.io.subsystems.ArmIO;
import frc.io.subsystems.ClawIO;
import frc.robot.Constants.Arm;

import java.lang.Math;

public class Mechanism implements Subsystem {
    private static Mechanism instance;

    private ArmIO armIO;
    private ClawIO clawIO;

    private double endHeight;
    private double wristOffset;

    private double armPos;
    private double wristPos;
    private double clawPos;
    private boolean pumpMode;

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
        this.clawIO.setClawPos(this.clawPos);
        this.clawIO.setPump(this.pumpMode);
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
        this.endHeight = height;
        this.armPos = Math.acos(-(this.endHeight - Arm.SHOULDER_HEIGHT) / Arm.ARM_LENGTH);
        this.wristPos = this.armPos + this.wristOffset;
    }

    /**
     * @return End effector height in inches
     */
    public double getWristHeight() {
        return this.endHeight;
    }

    /**
     * Sets Claw position in actuator inches
     * 
     * @param position
     */
    public void setClawPos(double position) {
        this.clawPos = position;
    }

    /**
     * @return Claw open vs closed distance
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
}