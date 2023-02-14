package frc.io.subsystems;

// Import required Libraries
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// Import required Classes
import frc.robot.Constants;
import frc.util.control.SparkMaxConstants;
import frc.util.control.SparkMaxPID;

public class ClawIO implements IIO {
    private static ClawIO instance;

    // Claw motors
    private CANSparkMax claw;
    private CANSparkMax pump;

    // Claw encoders
    private RelativeEncoder clawEncoder;

    // PID Controllers
    private SparkMaxPID clawPidController;

    // PID Constants
    private SparkMaxConstants clawConstants = Constants.CLAW_CONSTANTS;

    private boolean enabled = Constants.CLAW_ENABLED;

    public static ClawIO getInstance() {
        if (instance == null) {
            instance = new ClawIO();
        }
        return instance;
    }

    private ClawIO() {
        if (!enabled)
            return;
        initMotors();
    }

    /**
     * Initializes claw motors
     */
    private void initMotors() {
        this.claw = new CANSparkMax(Constants.CLAW_ID, MotorType.kBrushless);
        this.pump = new CANSparkMax(Constants.PUMP_ID, MotorType.kBrushed);

        this.clawEncoder = claw.getEncoder();

        this.claw.restoreFactoryDefaults();

        this.claw.setIdleMode(CANSparkMax.IdleMode.kBrake);

        this.claw.setSmartCurrentLimit(60, 10);

        this.clawPidController = new SparkMaxPID(this.claw, this.clawConstants);

        this.clawPidController.setFeedbackDevice(clawEncoder);

        this.claw.setInverted(false);

        this.clawEncoder.setPositionConversionFactor(Constants.CLAW_CONVERSION_FACTOR);
    }

    /**
     * Sets claw opening in inches
     * 
     * @param position
     */
    public void setClawPos(double position) {
        if (!enabled)
            return;

        this.clawPidController.setPosition(position);
    }

    /**
     * Sets pneumatic suction state boolean
     * 
     * @param state
     */
    public void setPump(boolean state) {
        if (!enabled)
            return;

        this.pump.set(state ? 1 : 0);
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
     * Resets claw to zero position
     */
    public void resetPosition() {
        if (!enabled)
            return;

        this.clawPidController.setPosition(0);
    }

    /**
     * Resets claw encoder to zero position
     * 
     * <p>
     * Deprecated since claw encoders should not be reset through code
     */
    @Deprecated
    public void resetInputs() {
        if (!enabled)
            return;

        this.clawEncoder.setPosition(0);
    }

    @Deprecated
    public void updateInputs() {
        if (!enabled)
            return;
    }

    /**
     * Disables claw and pump motors
     */
    @Override
    public void stopAllOutputs() {
        if (!enabled)
            return;

        this.claw.disable();
        this.pump.disable();
    }
}