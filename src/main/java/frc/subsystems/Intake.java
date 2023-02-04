package frc.subsystems;

import frc.io.subsystems.IntakeIO;
import frc.robot.Constants;

public class Intake implements Subsystem {
    private static Intake instance;

    private IntakeIO intakeIO;

    private double intakeSpeed;
    private boolean intakeState;

    private boolean enabled = Constants.INTAKE_ENABLED;

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }
        return instance;
    }

    protected Intake() {
        if (!enabled)
            return;

        this.intakeIO = IntakeIO.getInstance();

        firstCycle();
    }

    @Override
    public void firstCycle() {
        if (!enabled)
            return;

    }

    @Override
    public void run() {
        if (!enabled)
            return;

        this.intakeIO.setIntakeSpeed(intakeSpeed);
        this.intakeIO.setIntakeState(intakeState);
    }

    @Override
    public void disable() {
        if (!enabled)
            return;

        this.intakeIO.stopAllOutputs();
    }

    public void resetPosition() {
        if (!enabled)
            return;

        this.intakeIO.getIntakeEncoder().setPosition(0);
    }

    /**
     * Set the speed of the intake
     * 
     * @param speed in decimal
     */
    public void setIntakeSpeed(double speed) {
        if (!enabled)
            return;

        this.intakeSpeed = speed;
    }

    /**
     * Set the position of the intake
     * 
     * @param state as a boolean value
     *              { FALSE: Raised | TRUE: Lowered }
     */
    public void setIntakeState(boolean state) {
        if (!enabled)
            return;

        this.intakeState = state;
    }

    /**
     * Returns the position of the intake
     * 
     * @param state as a boolean value
     *              { FALSE: Raised | TRUE: Lowered }
     */
    public boolean getIntakeState() {
        if (!enabled)
            return false;

        return this.intakeState;
    }
}