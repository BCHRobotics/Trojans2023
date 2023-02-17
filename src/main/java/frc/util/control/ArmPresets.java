package frc.util.control;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ArmPresets {

    // PID coefficients
    public double wristHeight;
    public double wristOffset;

    /**
     * Push preset properties to the Smart Dashboard
     * 
     * @param name
     */
    public void pushToDashboard(String name) {
        SmartDashboard.putNumber(name + "Wrist Height", wristHeight);
        SmartDashboard.putNumber(name + "Wrist Offset", wristOffset);
    }

    /**
     * Retrieve preset properties from the Smart Dashboard
     * 
     * @param name
     */
    public void getFromDashboard(String name) {
        wristHeight = SmartDashboard.getNumber(name + "Wrist Height", 0);
        wristOffset = SmartDashboard.getNumber(name + "Wrist Offset", 0);
    }
}
