package frc.util.control;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Terms {

    // PID coefficients
    public double kP;
    public double kI;
    public double kD;
    public double kFF;

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
}
