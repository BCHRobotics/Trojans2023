package frc.util.control;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;

public class SmartControl extends PIDController {

    public ArmFeedforward ffController;
    private SmartConstants constants;

    public SmartControl(SmartConstants constants) {
        super(constants.kP, constants.kI, constants.kD);
        this.constants = constants;
        this.updateConstants();
    }

    public void updateConstants() {
        this.constants.getFromDashboard();
        this.setPID(constants.kP, constants.kI, constants.kD);
        this.ffController = new ArmFeedforward(constants.kS, constants.kG, constants.kV, constants.kA);
    }

    public SmartConstants getConstants() {
        return this.constants;
    }

    public double calculate(double setPoint, double feedback, double velFF) {
        return this.ffController.calculate(setPoint, velFF) + this.calculate(feedback, setPoint);
    }

}