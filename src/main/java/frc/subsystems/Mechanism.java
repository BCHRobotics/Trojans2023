package frc.subsystems;

import frc.io.subsystems.ArmIO;
import frc.io.subsystems.ClawIO;
import frc.robot.Constants;
import java.lang.Math;

public class Mechanism extends Subsystem {
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
     * Get the instance of the Drive, if none create a new instance
     * 
     * @return instance of the Drive
     */
    public static Mechanism getInstance() {
        if (instance == null) {
            instance = new Mechanism();
        }
        return instance;
    }

    private Mechanism() {
        this.firstCycle();
    }

    @Override
    public void firstCycle() {
        this.armIO = ArmIO.getInstance();
        this.clawIO = ClawIO.getInstance();
        this.resetEncoders();
    }

    @Override
    public void run() {
        this.armIO.setArmPos(this.armPos);
        this.armIO.setWristPos(this.wristPos + this.wristOffset);
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
        this.armIO.setArmPos(0);
        this.clawIO.setClawPos(0);
    }

    /**
     * Reset encoders to zero position
     */
    public void resetEncoders() {
        this.armIO.resetInputs();
        this.clawIO.resetInputs();
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
    public double getClawGap() {
        return this.clawPos;
    }

    /**
     * Sets end effector height in inches
     * 
     * @param position
     */
    public void setWristHeight(double height) {
        this.endHeight = height;
        this.armPos = Math.acos(-(this.endHeight - Constants.SHOULDER_HEIGHT) / Constants.ARM_LENGTH);
        this.wristPos = this.armPos + Constants.WRIST_OFFSET + this.wristOffset;
    }

    /**
     * @return End effector height in inches
     */
    public double getWristHeight() {
        return this.endHeight;
    }

    /**
     * Sets wrist offset in degrees
     * 
     * @param position
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
}