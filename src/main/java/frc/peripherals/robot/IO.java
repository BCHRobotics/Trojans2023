package frc.peripherals.robot;

import java.util.ArrayList;

public class IO {

    public ArrayList<IIO> subsystems;
    private static IO instance;

    public static IO getInstance() {
        if (instance == null)
            instance = new IO();
        return instance;
    }

    private IO() {
        this.subsystems = new ArrayList<>();
        this.subsystems.add(DriveIO.getInstance());
        this.subsystems.add(ArmIO.getInstance());
        this.subsystems.add(ClawIO.getInstance());
        this.subsystems.add(LEDIO.getInstance());
    }

    /**
     * Updates all robot inputs
     */
    public void updateInputs() {
        for (IIO io : this.subsystems) {
            io.updateInputs();
        }
    }

    /**
     * Reset relative encoders to zero position
     */
    public void resetInputs() {
        for (IIO io : subsystems) {
            io.resetInputs();
        }
    }

    /**
     * Disables all robot outputs
     */
    public void stopAllOutputs() {
        for (IIO io : subsystems) {
            io.stopAllOutputs();
        }
    }

}
