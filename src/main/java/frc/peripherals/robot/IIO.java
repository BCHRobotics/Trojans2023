package frc.peripherals.robot;

public interface IIO {

    /**
     * Initializes peripherals
     */
    void init();

    /**
     * Updates peripherals
     */
    void update();

    /**
     * Resets peripherals
     */
    void reset();

    /**
     * Disables peripherals
     */
    void disable();

}
