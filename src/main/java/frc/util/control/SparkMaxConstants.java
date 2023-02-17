package frc.util.control;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class SparkMaxConstants {
    // #region turret

    // PID coefficients
    public double kP;
    public double kI;
    public double kD;
    public double kIz;
    public double kFF;
    public double kMinOutput;
    public double kMaxOutput;

    // Smart Motion Coefficients
    public int slot;
    public double minVel; // rpm
    public double maxVel; // rpm
    public double maxAcc; // rpm
    public double allowedErr;

    //// #endregion turret

    public void pushToDashboard(String name) {
        SmartDashboard.putNumber(name + " P Gain", kP);
        SmartDashboard.putNumber(name + " I Gain", kI);
        SmartDashboard.putNumber(name + " D Gain", kD);
        SmartDashboard.putNumber(name + " Feed Forward", kFF);
        /*
         * SmartDashboard.putNumber(name + " I Zone", kIz);
         * 
         * SmartDashboard.putNumber(name + " Max Output", kMaxOutput);
         * SmartDashboard.putNumber(name + " Min Output", kMinOutput);
         * 
         * SmartDashboard.putNumber(name + " Max Velocity", maxVel);
         * SmartDashboard.putNumber(name + " Min Velocity", minVel);
         * SmartDashboard.putNumber(name + " Max Acceleration", maxAcc);
         * SmartDashboard.putNumber(name + " Allowed Closed Loop Error", allowedErr);
         */
    }

    public void getFromDashboard(String name) {
        kP = SmartDashboard.getNumber(name + " P Gain", 0);
        kI = SmartDashboard.getNumber(name + " I Gain", 0);
        kD = SmartDashboard.getNumber(name + " D Gain", 0);
        kFF = SmartDashboard.getNumber(name + " Feed Forward", 0);
        /*
         * kIz = SmartDashboard.getNumber(name + " I Zone", 0);
         * 
         * kMaxOutput = SmartDashboard.getNumber(name + " Max Output", 0);
         * kMinOutput = SmartDashboard.getNumber(name + " Min Output", 0);
         * 
         * maxVel = SmartDashboard.getNumber(name + " Max Velocity", 0);
         * minVel = SmartDashboard.getNumber(name + " Min Velocity", 0);
         * maxAcc = SmartDashboard.getNumber(name + " Max Acceleration", 0);
         * allowedErr = SmartDashboard.getNumber(name + " Allowed Closed Loop Error",
         * 0);
         */
    }

    public boolean valuesChanged(String name) {
        return (this.kP != SmartDashboard.getNumber(name + " P Gain", 0)
                || this.kI != SmartDashboard.getNumber(name + " I Gain", 0)
                || this.kD != SmartDashboard.getNumber(name + " D Gain", 0)
                || this.kFF != SmartDashboard.getNumber(name + " Feed Forward", 0));
    }
}
