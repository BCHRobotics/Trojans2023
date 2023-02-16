package frc.util.control;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDConstants {

    // PID coefficients
    public double kP;
    public double kI;
    public double kD;

    /**
     * PID gain values for basic pid controller
     * 
     * @param kP
     * @param kI
     * @param kD
     */
    public PIDConstants(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public void pushToDashboard(String name) {
        SmartDashboard.putNumber(name + " P Gain", kP);
        SmartDashboard.putNumber(name + " I Gain", kI);
        SmartDashboard.putNumber(name + " D Gain", kD);
    }

    public void getFromDashboard(String name) {
        this.kP = SmartDashboard.getNumber(name + " P Gain", 0);
        this.kI = SmartDashboard.getNumber(name + " I Gain", 0);
        this.kD = SmartDashboard.getNumber(name + " D Gain", 0);
    }

    public boolean valuesChanged(String name) {
        return (this.kP != SmartDashboard.getNumber(name + " P Gain", 0)
                || this.kI != SmartDashboard.getNumber(name + " I Gain", 0)
                || this.kD != SmartDashboard.getNumber(name + " D Gain", 0));
    }

    @Override
    public String toString() {
        return String.format("kP: %f, kI: %f, kD: %f\n", kP, kI, kD);
    }

}
