package frc.util.control;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SparkMaxConstants {

    // PID coefficients
    public double kP;
    public double kI;
    public double kD;
    public double kIz;
    public double kFF;
    public double minOut;
    public double maxOut;
    public String name;

    /**
     * Control gain values for Spark MAX Controller
     * 
     * @param kP
     * @param kI
     * @param kD
     * @param kIz
     * @param kFF
     * @param kMinOutput
     * @param kMaxOutput
     */
    public SparkMaxConstants(double kP, double kI, double kD, double kIz, double kFF, double minOut, double maxOut) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kIz = kIz;
        this.kFF = kFF;
        this.minOut = minOut;
        this.maxOut = maxOut;
    }

    public void pushToDashboard(String name) {
        this.name = name;
        SmartDashboard.putNumber(this.name + " P Gain", kP);
        SmartDashboard.putNumber(this.name + " I Gain", kI);
        SmartDashboard.putNumber(this.name + " D Gain", kD);
        SmartDashboard.putNumber(this.name + " Feed Forward", kFF);
        SmartDashboard.putNumber(this.name + " I Zone", kIz);
        SmartDashboard.putNumber(this.name + " Min Output", minOut);
        SmartDashboard.putNumber(this.name + " Max Output", maxOut);
    }

    public void getFromDashboard() {
        this.kP = SmartDashboard.getNumber(this.name + " P Gain", 0);
        this.kI = SmartDashboard.getNumber(this.name + " I Gain", 0);
        this.kD = SmartDashboard.getNumber(this.name + " D Gain", 0);
        this.kFF = SmartDashboard.getNumber(this.name + " Feed Forward", 0);
        this.kIz = SmartDashboard.getNumber(this.name + " I Zone", 0);
        this.minOut = SmartDashboard.getNumber(this.name + " Min Output", 0);
        this.maxOut = SmartDashboard.getNumber(this.name + " Max Output", 0);
    }

    public boolean valuesChanged() {
        return (this.kP != SmartDashboard.getNumber(this.name + " P Gain", 0)
                || this.kI != SmartDashboard.getNumber(this.name + " I Gain", 0)
                || this.kD != SmartDashboard.getNumber(this.name + " D Gain", 0)
                || this.kFF != SmartDashboard.getNumber(this.name + " Feed Forward", 0)
                || this.kIz != SmartDashboard.getNumber(this.name + " I Zone", 0)
                || this.minOut != SmartDashboard.getNumber(this.name + " Min Output", 0)
                || this.maxOut != SmartDashboard.getNumber(this.name + " Max Output", 0));
    }

    @Override
    public String toString() {
        return String.format("kP: %f, kI: %f, kD: %f, kIz: %f, kFF: %f\n" +
                "kMaxOutput: %f, kMinOutput: %f\n", kP, kI, kD, kIz, kFF, minOut, maxOut);
    }
}
