package frc.util.control;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;

public class SmartControl extends PIDController {

    public ArmFeedforward ffController;
    private SmartConstants constants;

    public SmartControl(SmartConstants constants) {
        super(constants.kP, constants.kI, constants.kD);
        this.constants = constants;
    }

    public void updateConstants() {
        this.constants.getFromDashboard();
        this.setPID(this.constants.kP, this.constants.kI, this.constants.kD);
        this.ffController = new ArmFeedforward(constants.kS, constants.kG, constants.kV, constants.kA);
    }

    public void pushConstantsToDashboard(String label) {
        this.constants.pushToDashboard(label);
    }

    public SmartConstants getConstants() {
        return this.constants;
    }

    public double calculate(double setPoint, double feedback, double velFF) {
        return (this.calculate(feedback, setPoint) + this.constants.kFF);
    }

}