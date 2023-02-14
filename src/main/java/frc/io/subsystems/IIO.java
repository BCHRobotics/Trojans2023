package frc.io.subsystems;

public interface IIO {

    /**
     * Updates device inputs
     */
    void updateInputs();

    /**
     * Resets device inputs
     */
    void resetInputs();

    /**
     * Disables device ouputs
     */
    void stopAllOutputs();

}
