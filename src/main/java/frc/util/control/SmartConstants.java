package frc.util.control;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartConstants {

    // Feed forward and PID coefficients
    public double kS;
    public double kG;
    public double kV;
    public double kA;
    public double kP;
    public double kI;
    public double kD;
    public String name;

    /**
     * SVA values for basic feed forward controller
     * 
     * @param kS
     * @param kG
     * @param kV
     * @param kA
     * @param kP
     * @param kI
     * @param kD
     */
    public SmartConstants(double kS, double kG, double kV, double kA, double kP, double kI, double kD) {
        this.kS = kS;
        this.kG = kG;
        this.kV = kV;
        this.kA = kA;
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public void pushToDashboard(String name) {
        this.name = name;
        SmartDashboard.putNumber(this.name + " S Gain", kS);
        SmartDashboard.putNumber(this.name + " G Gain", kG);
        SmartDashboard.putNumber(this.name + " V Gain", kV);
        SmartDashboard.putNumber(this.name + " A Gain", kA);
        SmartDashboard.putNumber(this.name + " P Gain", kS);
        SmartDashboard.putNumber(this.name + " I Gain", kG);
        SmartDashboard.putNumber(this.name + " D Gain", kV);
    }

    public void getFromDashboard() {
        this.kS = SmartDashboard.getNumber(this.name + " S Gain", 0);
        this.kG = SmartDashboard.getNumber(this.name + " G Gain", 0);
        this.kV = SmartDashboard.getNumber(this.name + " V Gain", 0);
        this.kA = SmartDashboard.getNumber(this.name + " A Gain", 0);
        this.kG = SmartDashboard.getNumber(this.name + " P Gain", 0);
        this.kV = SmartDashboard.getNumber(this.name + " I Gain", 0);
        this.kA = SmartDashboard.getNumber(this.name + " D Gain", 0);
    }

    public boolean valuesChanged() {
        return (this.kS != SmartDashboard.getNumber(this.name + " S Gain", 0)
                || this.kG != SmartDashboard.getNumber(this.name + " G Gain", 0)
                || this.kV != SmartDashboard.getNumber(this.name + " V Gain", 0)
                || this.kA != SmartDashboard.getNumber(this.name + " A Gain", 0)
                || this.kG != SmartDashboard.getNumber(this.name + " P Gain", 0)
                || this.kV != SmartDashboard.getNumber(this.name + " I Gain", 0)
                || this.kA != SmartDashboard.getNumber(this.name + " D Gain", 0));
    }

    @Override
    public String toString() {
        return String.format("kS: %f, kG: %f, kV: %f, kA: %f, kP: %f, kI: %f, kD: %f\n", kS, kG, kV, kA, kP, kI, kD);
    }

}
