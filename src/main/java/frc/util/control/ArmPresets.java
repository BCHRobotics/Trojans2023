package frc.util.control;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmPresets {

    // PID coefficients
    public double wristHeight;
    public double wristOffset;

    /**
     * PID Values for Basic Balancing Algorithm
     * 
     * @param wristHeight
     * @param wristOffset
     */
    public ArmPresets(double height, double offset) {
        this.wristHeight = height;
        this.wristOffset = offset;
    }

    public void pushToDashboard(String name) {
        SmartDashboard.putNumber(name + "Wrist Height", wristHeight);
        SmartDashboard.putNumber(name + "Wrist Offset", wristOffset);
    }

    public void getFromDashboard(String name) {
        wristHeight = SmartDashboard.getNumber(name + "Wrist Height", 0);
        wristOffset = SmartDashboard.getNumber(name + "Wrist Offset", 0);
    }

    @Override
    public String toString() {
        return String.format("Wrist Height: %f, Wrist Offset: %f\n", wristHeight, wristOffset);
    }

}
