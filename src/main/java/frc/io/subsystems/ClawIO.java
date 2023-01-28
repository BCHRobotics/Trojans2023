package frc.io.subsystems;

// Import required Libraries
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// Import required Classes
import frc.robot.Constants;
import frc.util.pid.SparkMaxConstants;
import frc.util.pid.SparkMaxPID;

public class ClawIO implements IIO {
    private static ClawIO instance;

    // Drive motors
    private CANSparkMax claw;
    private CANSparkMax pump;

    // Drive encoders
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

    private void initMotors() {
        this.claw = new CANSparkMax(Constants.WRIST_ID, MotorType.kBrushless);
        this.pump = new CANSparkMax(Constants.PUMP_ID, MotorType.kBrushed);

        this.clawEncoder = claw.getEncoder();

        this.claw.restoreFactoryDefaults();

        this.claw.setIdleMode(CANSparkMax.IdleMode.kBrake);

        this.claw.setSmartCurrentLimit(60, 10);

        this.clawPidController = new SparkMaxPID(this.claw, this.clawConstants);

        this.claw.setInverted(true);

        this.clawEncoder.setPositionConversionFactor(Constants.CLAW_CONVERSION_FACTOR);
    }

    public void setClawPos(double position) {
        if (!enabled)
            return;

        this.clawPidController.setPosition(position);
    }

    public void setPump(boolean state) {
        if (!enabled)
            return;

        this.pump.set(state ? 1 : 0);
    }

    public RelativeEncoder getClawEncoder() {
        if (!enabled)
            return null;

        return this.clawEncoder;
    }

    @Override
    public void resetInputs() {
        if (!enabled)
            return;

        this.clawPidController.setPosition(0);
    }

    @Override
    public void updateInputs() {
        if (!enabled)
            return;
    }

    @Override
    public void stopAllOutputs() {
        if (!enabled)
            return;

        this.claw.disable();
        this.pump.disable();
    }
}