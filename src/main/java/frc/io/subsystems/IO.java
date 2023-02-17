package frc.io.subsystems;

import java.util.ArrayList;

public class IO {

    public ArrayList<IIO> subsystems = new ArrayList<>();
    private static IO instance;

    public static IO getInstance() {
        if (instance == null)
            instance = new IO();
        return instance;
    }

    private IO() {
        this.subsystems.add(DriveIO.getInstance());
        this.subsystems.add(ArmIO.getInstance());
        this.subsystems.add(ClawIO.getInstance());
    }

    /**
     * Updates all robot inputs
     */
    public void updateInputs() {
        this.subsystems.forEach(IIO::updateInputs);
    }

    /**
     * Reset relative encoders to zero position
     */
    public void resetInputs() {
        this.subsystems.forEach(IIO::resetInputs);
    }

    /**
     * Disables all robot outputs
     */
    public void stopAllOutputs() {
        this.subsystems.forEach(IIO::stopAllOutputs);
    }
}
