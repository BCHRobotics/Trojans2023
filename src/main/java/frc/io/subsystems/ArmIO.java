package frc.io.subsystems;

// Import required Libraries
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// Import required Classes
import frc.robot.Constants;
import frc.util.control.SparkMaxPID;

public class ArmIO implements IIO {
    private static ArmIO instance;

    // Drive motors
    private CANSparkMax shoulder;
    private CANSparkMax wrist;

    // Drive encoders
    private RelativeEncoder shoulderEncoder;
    private RelativeEncoder wristEncoder;

    // PID Controllers
    private SparkMaxPID shoulderPidController;
    private SparkMaxPID wristPidController;

    private boolean enabled = Constants.ARM_ENABLED;

    public static ArmIO getInstance() {
        if (instance == null) {
            instance = new ArmIO();
        }
        return instance;
    }

    private ArmIO() {
        if (!enabled)
            return;
        initMotors();
    }

    private void initMotors() {
        this.shoulder = new CANSparkMax(Constants.SHOULDER_ID, MotorType.kBrushless);
        this.wrist = new CANSparkMax(Constants.WRIST_ID, MotorType.kBrushless);

        this.shoulderEncoder = wrist.getEncoder();
        this.wristEncoder = wrist.getEncoder();

        this.shoulder.restoreFactoryDefaults();
        this.wrist.restoreFactoryDefaults();

        this.shoulder.setIdleMode(CANSparkMax.IdleMode.kBrake);
        this.wrist.setIdleMode(CANSparkMax.IdleMode.kBrake);

        this.shoulder.setSmartCurrentLimit(60, 10);
        this.wrist.setSmartCurrentLimit(60, 10);

        this.shoulderPidController = new SparkMaxPID(this.shoulder, Constants.SHOULDER_CONSTANTS);
        this.wristPidController = new SparkMaxPID(this.wrist, Constants.WRIST_CONSTANTS);

        this.shoulder.setInverted(false);
        this.wrist.setInverted(false);

        this.shoulderEncoder.setPositionConversionFactor(Constants.SHOULDER_CONVERSION_FACTOR);
        this.wristEncoder.setPositionConversionFactor(Constants.WRIST_CONVERSION_FACTOR);
    }

    public void setArmPos(double position) {
        if (!enabled)
            return;

        this.shoulderPidController.setPosition(position);
    }

    public void setWristPos(double position) {
        if (!enabled)
            return;

        this.wristPidController.setPosition(position);
    }

    public RelativeEncoder getShoulderEncoder() {
        if (!enabled)
            return null;

        return this.shoulderEncoder;
    }

    public RelativeEncoder getWristEncoder() {
        if (!enabled)
            return null;

        return this.wristEncoder;
    }

    @Override
    public void resetInputs() {
        if (!enabled)
            return;

        this.shoulderPidController.setPosition(0);
        this.wristPidController.setPosition(0);
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

        this.shoulder.disable();
        this.wrist.disable();
    }
}