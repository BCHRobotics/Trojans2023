package frc.io.subsystems;

// Import required Libraries
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// Import required Classes
import frc.robot.Constants;
import frc.util.control.SparkMaxConstants;
import frc.util.control.SparkMaxPID;

public class ArmIO implements IIO {
    private static ArmIO instance;

    // Drive motors
    private CANSparkMax arm;
    private CANSparkMax wrist;

    // Drive encoders
    private RelativeEncoder armEncoder;
    private RelativeEncoder wristEncoder;

    // PID Controllers
    private SparkMaxPID armPidController;
    private SparkMaxPID wristPidController;

    // PID Constants
    private SparkMaxConstants armConstants = Constants.ARM_CONSTANTS;
    private SparkMaxConstants wristConstants = Constants.WRIST_CONSTANTS;

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
        this.arm = new CANSparkMax(Constants.ARM_ID, MotorType.kBrushless);
        this.wrist = new CANSparkMax(Constants.WRIST_ID, MotorType.kBrushless);

        this.armEncoder = wrist.getEncoder();
        this.wristEncoder = wrist.getEncoder();

        this.arm.restoreFactoryDefaults();
        this.wrist.restoreFactoryDefaults();

        this.arm.setIdleMode(CANSparkMax.IdleMode.kBrake);
        this.wrist.setIdleMode(CANSparkMax.IdleMode.kBrake);

        this.arm.setSmartCurrentLimit(60, 10);
        this.wrist.setSmartCurrentLimit(60, 10);

        this.armPidController = new SparkMaxPID(this.arm, this.armConstants);
        this.wristPidController = new SparkMaxPID(this.wrist, this.wristConstants);

        this.arm.setInverted(false);
        this.wrist.setInverted(false);

        this.armEncoder.setPositionConversionFactor(Constants.ARM_CONVERSION_FACTOR);
        this.wristEncoder.setPositionConversionFactor(Constants.WRIST_CONVERSION_FACTOR);
    }

    public void setArmPos(double position) {
        if (!enabled)
            return;

        this.armPidController.setPosition(position);
    }

    public void setWristPos(double position) {
        if (!enabled)
            return;

        this.wristPidController.setPosition(position);
    }

    public RelativeEncoder getArmEncoder() {
        if (!enabled)
            return null;

        return this.armEncoder;
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

        this.armPidController.setPosition(0);
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

        this.arm.disable();
        this.wrist.disable();
    }
}