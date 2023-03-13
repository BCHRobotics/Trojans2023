package frc.peripherals.robot;

// Import required Libraries
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// Import required Classes
import frc.robot.Constants.Features;
import frc.robot.Constants.Misc;
import frc.robot.Constants.Misc.LED_STATE;

public class LEDIO implements IIO {
    private static LEDIO instance;

    // Status LEDs
    private DigitalOutput coneLED;
    private DigitalOutput cubeLED;

    private LED_STATE ledState;

    private long previousTime, currentTime;

    private boolean enabled = Features.LEDS_ENABLED;

    public static LEDIO getInstance() {
        if (instance == null) {
            instance = new LEDIO();
        }
        return instance;
    }

    private LEDIO() {
        if (!enabled)
            return;
        this.coneLED = new DigitalOutput(Misc.CONE_LED_PORT);
        this.cubeLED = new DigitalOutput(Misc.CUBE_LED_PORT);
    }

    /**
     * Sets LED state
     * 
     * @param state
     */
    public void setLEDState(LED_STATE state) {
        if (!enabled)
            return;

        this.ledState = state;
    }

    @Override
    public void resetInputs() {
        if (!enabled)
            return;

        this.ledState = LED_STATE.OFF;
        this.currentTime = 0;
        this.previousTime = 0;
        this.stopAllOutputs();
    }

    @Override
    public void updateInputs() {
        if (!enabled)
            return;
        SmartDashboard.putBoolean("Cube Request", this.cubeLED.get());
        SmartDashboard.putBoolean("Cone Request", this.coneLED.get());

        this.currentTime = System.currentTimeMillis();

        if (this.blinkRequired()) {
            if ((this.currentTime >= this.previousTime + Misc.BLINK_INTERVAL)) {
                this.pushState();
                this.previousTime = this.currentTime;
            } else
                this.stopAllOutputs();
        } else
            this.pushState();

    }

    private void pushState() {
        if (!enabled)
            return;

        if (this.ledState == LED_STATE.BOTH || this.ledState == LED_STATE.BOTH_BLINK) {
            this.coneLED.set(true);
            this.cubeLED.set(true);
        } else {
            this.coneLED.set(this.ledState == LED_STATE.CONE || this.ledState == LED_STATE.CONE_BLINK);
            this.cubeLED.set(this.ledState == LED_STATE.CUBE || this.ledState == LED_STATE.CUBE_BLINK);
        }
    }

    private boolean blinkRequired() {
        return (this.ledState == LED_STATE.CONE_BLINK || this.ledState == LED_STATE.CUBE_BLINK
                || this.ledState == LED_STATE.BOTH_BLINK);
    }

    /**
     * Disables claw and pump motors
     */
    @Override
    public void stopAllOutputs() {
        if (!enabled)
            return;
        this.coneLED.set(false);
        this.cubeLED.set(false);
    }

}