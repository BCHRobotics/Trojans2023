package frc.robot.util.control;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.SparkMaxPIDController;

public class SparkMaxPID {

    private SparkMaxPIDController pidController;
    private SparkMaxConstants constants;

    public SparkMaxPID(CANSparkMax motor) {
        pidController = motor.getPIDController();
    }

    public SparkMaxPID(CANSparkMax motor, SparkMaxConstants constants) {
        this(motor);
        this.setConstants(constants);
    }

    public SparkMaxPID(SparkMaxPIDController pidController) {
        this.pidController = pidController;
    }

    public SparkMaxPID(SparkMaxPIDController pidController, SparkMaxConstants constants) {
        this(pidController);
        this.setConstants(constants);
    }

    public void setConstants(SparkMaxConstants c) {
        pidController.setP(c.kP);
        pidController.setI(c.kI);
        pidController.setD(c.kD);
        pidController.setFF(c.kFF);
        pidController.setIZone(c.kIz);
        pidController.setOutputRange(c.minOut, c.maxOut);
        this.constants = c;
    }

    public SparkMaxConstants getConstants() {
        return this.constants;
    }

    public void retrieveDashboardConstants() {
        if (this.constants.valuesChanged(this.constants.name))
            this.constants.getFromDashboard(this.constants.name);
        this.setConstants(this.constants);
    }

    public void setFeedbackDevice(RelativeEncoder device) {
        pidController.setFeedbackDevice(device);
    }

    public void setFeedbackDevice(SparkMaxAbsoluteEncoder device) {
        pidController.setFeedbackDevice(device);
    }

    public void setFeedbackDevice(SparkMaxAlternateEncoder device) {
        pidController.setFeedbackDevice(device);
    }

    public void setPosition(double setPoint) {
        pidController.setReference(setPoint, CANSparkMax.ControlType.kPosition);
    }

    public void setVelocity(double speed) {
        pidController.setReference(speed, CANSparkMax.ControlType.kVelocity);
    }

    public void setPosition(double setPoint, double feedForward) {
        pidController.setFF(feedForward);
        pidController.setReference(setPoint, CANSparkMax.ControlType.kPosition);
        this.setConstants(this.constants);
    }

    public void setVelocity(double speed, double feedForward) {
        pidController.setFF(feedForward);
        pidController.setReference(speed, CANSparkMax.ControlType.kVelocity);
        this.setConstants(this.constants);
    }

}
