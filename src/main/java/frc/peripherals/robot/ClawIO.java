package frc.peripherals.robot;

// Import required Libraries
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// Import required Modules
import frc.robot.Constants.*;

public class ClawIO implements IIO {
    private static ClawIO instance;

    // Claw motor
    private CANSparkMax claw;

    // Claw encoder
    private RelativeEncoder clawEncoder;

    // Claw enabled / disabled
    private boolean enabled = ROBOT.CLAW_ENABLED;

    public static ClawIO getInstance() {
        if (instance == null)
            instance = new ClawIO();

        return instance;
    }

    private ClawIO() {
        if (!enabled)
            return;

        initPeripherals();
    }

    /**
     * Initializes wrist peripherals
     */
    private void initPeripherals() {

        // Initialize motor controller
        this.claw = new CANSparkMax(WRIST.ID, MotorType.kBrushless);
        this.claw.restoreFactoryDefaults();
        this.claw.setIdleMode(CANSparkMax.IdleMode.kBrake);
        this.claw.setSmartCurrentLimit(40, 20);
        this.claw.setInverted(false);

        // Initialize encoder
        this.clawEncoder = this.claw.getEncoder();
        this.clawEncoder.setPositionConversionFactor(WRIST.CONVERSION_FACTOR);
    }

    /**
     * Sets the claw wheel speed in percent output [0 --> 1]
     * 
     * @param percent
     */
    public void setClawSpeed(double percent) {
        if (!enabled)
            return;

        this.claw.set(percent);
    }

    /**
     * Gets claw relative encoder object
     * 
     * @return Claw Relative Encoder
     */
    public RelativeEncoder getClawEncoder() {
        if (!enabled)
            return null;

        return this.clawEncoder;
    }

    /**
     * Initializes claw motors
     */
    @Override
    public void init() {
        if (!enabled)
            return;

        this.reset();
    }

    /**
     * Updates claw peripherals
     */
    @Override
    public void update() {
        if (!enabled)
            return;
    }

    /**
     * Resets claw to zero velocity
     */
    @Override
    public void reset() {
        if (!enabled)
            return;

        this.setClawSpeed(0);
    }

    /**
     * Disables claw motor
     */
    @Override
    public void disable() {
        if (!enabled)
            return;

        this.claw.disable();
    }
}