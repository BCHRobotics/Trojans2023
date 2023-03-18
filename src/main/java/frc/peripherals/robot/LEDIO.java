package frc.peripherals.robot;

// Import required Libraries
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// Import required Modules
import frc.robot.Constants.ROBOT;
import frc.robot.Constants.MISC;
import frc.robot.Constants.MISC.LED_STATE;

public class LEDIO implements IIO {
    private static LEDIO instance;

    // Status LEDs
    private DigitalOutput coneLED;
    private DigitalOutput cubeLED;

    // Current LED state
    private LED_STATE ledState;

    // Timer variables
    private Timer timer;

    // LEDs enabled / disabled
    private boolean enabled = ROBOT.LEDS_ENABLED;

    public static LEDIO getInstance() {
        if (instance == null)
            instance = new LEDIO();

        return instance;
    }

    private LEDIO() {
        if (!enabled)
            return;

        this.coneLED = new DigitalOutput(MISC.CONE_LED_PORT);
        this.cubeLED = new DigitalOutput(MISC.CUBE_LED_PORT);
        this.timer = new Timer();
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
        if (this.blinkRequired())
            this.timer.start();
    }

    /**
     * Pushes current LED state to Digital Outputs
     */
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

    /**
     * Checks to see if the current LED state will require a timer
     * 
     * @return Blink required
     */
    private boolean blinkRequired() {
        return (this.ledState == LED_STATE.CONE_BLINK || this.ledState == LED_STATE.CUBE_BLINK
                || this.ledState == LED_STATE.BOTH_BLINK);
    }

    /**
     * Initializes LED strips
     */
    @Override
    public void init() {
        if (!enabled)
            return;

        this.reset();
    }

    /**
     * Updates LED strips
     */
    @Override
    public void update() {
        if (!enabled)
            return;

        SmartDashboard.putBoolean("Cube Request", this.cubeLED.get());
        SmartDashboard.putBoolean("Cone Request", this.coneLED.get());

        if (this.blinkRequired()) {
            if (this.timer.advanceIfElapsed(MISC.BLINK_INTERVAL))
                this.pushState();
            else
                this.disable();
        } else
            this.pushState();

    }

    /**
     * Resets LED strips
     */
    @Override
    public void reset() {
        if (!enabled)
            return;

        this.ledState = LED_STATE.OFF;
        this.timer.reset();
        this.disable();
    }

    /**
     * Disables LED strips
     */
    @Override
    public void disable() {
        if (!enabled)
            return;
        this.coneLED.set(false);
        this.cubeLED.set(false);
    }

}