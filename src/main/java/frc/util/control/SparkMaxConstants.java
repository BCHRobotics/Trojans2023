package frc.util.control;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SparkMaxConstants extends PIDConstants {

    // Additional coefficients
    public double kIz;
    public double kFF;
    public double minOut;
    public double maxOut;

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
        super(kP, kI, kD);
        this.kIz = kIz;
        this.kFF = kFF;
        this.minOut = minOut;
        this.maxOut = maxOut;
    }

    @Override
    public void pushToDashboard(String name) {
        super.pushToDashboard(name);
        SmartDashboard.putNumber(name + " Feed Forward", kFF);
        SmartDashboard.putNumber(name + " I Zone", kIz);
        SmartDashboard.putNumber(name + " Min Output", minOut);
        SmartDashboard.putNumber(name + " Max Output", maxOut);
    }

    @Override
    public void getFromDashboard(String name) {
        super.getFromDashboard(name);
        this.kFF = SmartDashboard.getNumber(name + " Feed Forward", 0);
        this.kIz = SmartDashboard.getNumber(name + " I Zone", 0);
        this.minOut = SmartDashboard.getNumber(name + " Min Output", 0);
        this.maxOut = SmartDashboard.getNumber(name + " Max Output", 0);
    }

    @Override
    public boolean valuesChanged(String name) {
        return (this.kP != SmartDashboard.getNumber(name + " P Gain", 0)
                || this.kI != SmartDashboard.getNumber(name + " I Gain", 0)
                || this.kD != SmartDashboard.getNumber(name + " D Gain", 0)
                || this.kFF != SmartDashboard.getNumber(name + " Feed Forward", 0)
                || this.kIz != SmartDashboard.getNumber(name + " I Zone", 0)
                || this.minOut != SmartDashboard.getNumber(name + " Min Output", 0)
                || this.maxOut != SmartDashboard.getNumber(name + " Max Output", 0));
    }

    @Override
    public String toString() {
        return String.format("kP: %f, kI: %f, kD: %f, kIz: %f, kFF: %f\n" +
                "kMaxOutput: %f, kMinOutput: %f\n", kP, kI, kD, kIz, kFF, minOut, maxOut);
    }
}
