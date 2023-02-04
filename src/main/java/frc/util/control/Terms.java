package frc.util.control;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Terms {

    // PID coefficients
    public double kP;
    public double kI;
    public double kD;
    public double kFF;

    /**
     * PID Values for Basic Balancing Algorithm
     * 
     * @param kP
     * @param kI
     * @param kD
     * @param kFF
     */
    public Terms(double kP, double kI, double kD, double kFF) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kFF = kFF;
    }

    public void pushToDashboard(String name) {
        SmartDashboard.putNumber(name + " P Gain", kP);
        SmartDashboard.putNumber(name + " I Gain", kI);
        SmartDashboard.putNumber(name + " D Gain", kD);
        SmartDashboard.putNumber(name + " Feed Forward", kFF);
    }

    public void getFromDashboard(String name) {
        kP = SmartDashboard.getNumber(name + " P Gain", 0);
        kI = SmartDashboard.getNumber(name + " I Gain", 0);
        kD = SmartDashboard.getNumber(name + " D Gain", 0);
        kFF = SmartDashboard.getNumber(name + " Feed Forward", 0);
    }

    @Override
    public String toString() {
        return String.format("kP: %f, kI: %f, kD: %f, kFF: %f\n", kP, kI, kD, kFF);
    }

}
