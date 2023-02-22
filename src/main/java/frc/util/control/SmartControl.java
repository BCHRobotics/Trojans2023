package frc.util.control;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;

public class SmartControl {

    public ArmFeedforward ffController;
    public PIDController pidController;
    private SmartConstants constants;

    public SmartControl(SmartConstants constants) {
        this.constants = constants;
        this.updateConstants();
    }

    public void updateConstants() {
        this.constants.getFromDashboard();
        this.ffController = new ArmFeedforward(constants.kS, constants.kG, constants.kV, constants.kA);
        this.pidController = new PIDController(constants.kP, constants.kI, constants.kD);
    }

    public SmartConstants getConstants() {
        return this.constants;
    }

    public double calculate(double setPoint, double feedback, double velFF) {
        return this.ffController.calculate(setPoint, velFF) + this.pidController.calculate(feedback, setPoint);
    }

}
