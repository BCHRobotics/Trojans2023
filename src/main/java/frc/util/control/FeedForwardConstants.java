package frc.util.control;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FeedForwardConstants {

    // PID coefficients
    public double kS;
    public double kG;
    public double kV;
    public double kA;

    /**
     * SVA values for basic feed forward controller
     * 
     * @param kS
     * @param kG
     * @param kV
     * @param kA
     */
    public FeedForwardConstants(double kS, double kG, double kV, double kA) {
        this.kS = kS;
        this.kG = kG;
        this.kV = kV;
        this.kA = kA;
    }

    public void pushToDashboard(String name) {
        SmartDashboard.putNumber(name + " S Gain", kS);
        SmartDashboard.putNumber(name + " G Gain", kG);
        SmartDashboard.putNumber(name + " V Gain", kV);
        SmartDashboard.putNumber(name + " A Gain", kA);
    }

    public void getFromDashboard(String name) {
        this.kS = SmartDashboard.getNumber(name + " S Gain", 0);
        this.kG = SmartDashboard.getNumber(name + " G Gain", 0);
        this.kV = SmartDashboard.getNumber(name + " V Gain", 0);
        this.kA = SmartDashboard.getNumber(name + " A Gain", 0);
    }

    public boolean valuesChanged(String name) {
        return (this.kS != SmartDashboard.getNumber(name + " S Gain", 0)
                || this.kG != SmartDashboard.getNumber(name + " G Gain", 0)
                || this.kV != SmartDashboard.getNumber(name + " V Gain", 0)
                || this.kA != SmartDashboard.getNumber(name + " A Gain", 0));
    }

    @Override
    public String toString() {
        return String.format("kS: %f, kG: %f, kV: %f, kA: %f\n", kS, kG, kV, kA);
    }

}
