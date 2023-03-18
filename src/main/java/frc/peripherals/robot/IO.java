package frc.peripherals.robot;

import java.util.ArrayList;

public class IO {

    public ArrayList<IIO> peripherals;
    private static IO instance;

    public static IO getInstance() {
        if (instance == null)
            instance = new IO();
        return instance;
    }

    private IO() {
        this.peripherals = new ArrayList<>();
        this.peripherals.add(ChassisIO.getInstance());
        this.peripherals.add(ShoulderIO.getInstance());
        this.peripherals.add(WristIO.getInstance());
        this.peripherals.add(ClawIO.getInstance());
        this.peripherals.add(LEDIO.getInstance());
    }

    /**
     * Initializes all peripherals
     */
    public void init() {
        for (IIO io : peripherals) {
            io.init();
        }
    }

    /**
     * Updates all peripherals
     */
    public void update() {
        for (IIO io : this.peripherals) {
            io.update();
        }
    }

    /**
     * Resets all peripherals
     */
    public void reset() {
        for (IIO io : peripherals) {
            io.reset();
        }
    }

    /**
     * Disables all peripherals
     */
    public void disable() {
        for (IIO io : peripherals) {
            io.disable();
        }
    }

}
