package frc.subsystems;

import frc.io.subsystems.ArmIO;
import frc.io.subsystems.ClawIO;
import frc.robot.Constants;
import java.lang.Math;

public class Mechanism implements Subsystem {
    private static Mechanism instance;

    private boolean enabled = (Constants.ARM_ENABLED && Constants.CLAW_ENABLED);

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
        if (!enabled)
            return;
        this.firstCycle();
    }

    @Override
    public void firstCycle() {
        if (!enabled)
            return;

        this.armIO = ArmIO.getInstance();
        this.clawIO = ClawIO.getInstance();
        this.resetPosition();
    }

    @Override
    public void run() {
        if (!enabled)
            return;

        this.armIO.setShoulderAngle(this.armPos);
        this.armIO.setWristAngle(this.wristPos + this.wristOffset);
        this.clawIO.setClawPos(this.clawPos);
        this.clawIO.setPump(this.pumpMode);
    }

    @Override
    public void disable() {
        if (!enabled)
            return;

        this.armIO.stopAllOutputs();
        this.clawIO.stopAllOutputs();
    }

    /**
     * Reset appendages to zero position
     */
    public void resetPosition() {
        if (!enabled)
            return;

        this.armIO.resetPositions();
        this.clawIO.resetPosition();
    }

    /**
     * Sets end effector height in inches
     * 
     * @param position
     */
    public void setWristHeight(double height) {
        if (!enabled)
            return;

        this.endHeight = height;
        this.armPos = Math.acos(-(this.endHeight - Constants.SHOULDER_HEIGHT) / Constants.ARM_LENGTH);
        this.wristPos = this.armPos + this.wristOffset;
    }

    /**
     * @return End effector height in inches
     */
    public double getWristHeight() {
        if (!enabled)
            return 0;

        return this.endHeight;
    }

    /**
     * Sets wrist offset in degrees
     * 
     * @param position
     */
    public void setWristOffset(double angle) {
        if (!enabled)
            return;

        this.wristOffset = angle;
    }

    /**
     * @return Wrist offset in degrees
     */
    public double getWristOffset() {
        if (!enabled)
            return 0;

        return this.wristOffset;
    }

    /**
     * Sets Claw position in actuator inches
     * 
     * @param position
     */
    public void setClawPos(double position) {
        if (!enabled)
            return;

        this.clawPos = position;
    }

    /**
     * @return Claw open vs closed distance
     */
    public double getClawGap() {
        if (!enabled)
            return 0;

        return this.clawPos;
    }

    /**
     * Set pump state
     * 
     * @param state
     */
    public void setSuctionMode(boolean state) {
        if (!enabled)
            return;

        this.pumpMode = state;
    }

    /**
     * Get pump state
     * 
     * @return
     */
    public boolean getSuctionMode() {
        if (!enabled)
            return false;

        return this.pumpMode;
    }
}