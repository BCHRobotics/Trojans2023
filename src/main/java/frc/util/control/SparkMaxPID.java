package frc.util.control;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.SparkMaxPIDController;

public class SparkMaxPID {

    private SparkMaxPIDController pidController;

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

        pidController.setSmartMotionMaxVelocity(1000, 0);
        pidController.setSmartMotionMaxAccel(2000, 0);
    }

    public void setFeedForward(Double f) {
        pidController.setFF(f);
    }

    public SparkMaxConstants getConstants() {
        return new SparkMaxConstants(
                pidController.getP(),
                pidController.getI(),
                pidController.getD(),
                pidController.getIZone(),
                pidController.getFF(),
                pidController.getOutputMin(),
                pidController.getOutputMax());
    }

    public void retrieveDashboardConstants(SparkMaxConstants constants) {
        if (constants.valuesChanged())
            constants.getFromDashboard();
        this.setConstants(constants);
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
        pidController.setReference(setPoint, CANSparkMax.ControlType.kSmartMotion);
    }

    public void setVelocity(double speed) {
        pidController.setReference(speed, CANSparkMax.ControlType.kVelocity);
    }

}
