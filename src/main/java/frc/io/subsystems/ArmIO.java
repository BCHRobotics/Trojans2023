package frc.io.subsystems;

// Import required Libraries
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;

// Import required Classes
import frc.robot.Constants;
import frc.util.control.SmartControl;

public class ArmIO implements IIO {
    private static ArmIO instance;

    // Arm motors
    private CANSparkMax shoulder;
    private CANSparkMax wrist;

    // Arm encoders
    private SparkMaxAbsoluteEncoder shoulderEncoder;
    private SparkMaxAbsoluteEncoder wristEncoder;

    // PID Controllers
    private SmartControl shoulderPidController;
    private SmartControl wristPidController;

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

    /**
     * Initializes arm motors
     */
    private void initMotors() {
        this.shoulder = new CANSparkMax(Constants.SHOULDER_ID, MotorType.kBrushless);
        this.wrist = new CANSparkMax(Constants.WRIST_ID, MotorType.kBrushless);

        this.shoulder.restoreFactoryDefaults();
        this.wrist.restoreFactoryDefaults();

        this.shoulderEncoder = shoulder.getAbsoluteEncoder(Type.kDutyCycle);
        this.wristEncoder = wrist.getAbsoluteEncoder(Type.kDutyCycle);

        this.shoulderEncoder.setInverted(Constants.SHOULDER_ENCODER_INVERTED);
        this.wristEncoder.setInverted(Constants.WRIST_ENCODER_INVERTED);

        this.shoulderEncoder.setZeroOffset(Constants.SHOULDER_ENCODER_OFFSET);
        this.wristEncoder.setZeroOffset(Constants.WRIST_ENCODER_OFFSET);

        this.shoulder.setIdleMode(CANSparkMax.IdleMode.kBrake);
        this.wrist.setIdleMode(CANSparkMax.IdleMode.kBrake);

        this.shoulder.enableSoftLimit(SoftLimitDirection.kReverse, true);
        this.wrist.enableSoftLimit(SoftLimitDirection.kReverse, true);

        this.shoulder.setSoftLimit(SoftLimitDirection.kReverse, 0);
        this.wrist.setSoftLimit(SoftLimitDirection.kReverse, 0);

        this.shoulder.setSmartCurrentLimit(80, 20);
        this.wrist.setSmartCurrentLimit(80, 20);

        this.shoulderPidController = new SmartControl(Constants.SHOULDER_CONTROL_CONSTANTS);
        this.wristPidController = new SmartControl(Constants.WRIST_CONTROL_CONSTANTS);

        this.shoulder.setInverted(false);
        this.wrist.setInverted(false);

        this.shoulderEncoder.setPositionConversionFactor(Constants.SHOULDER_CONVERSION_FACTOR);
        this.wristEncoder.setPositionConversionFactor(Constants.WRIST_CONVERSION_FACTOR);
    }

    /**
     * Sets shoulder anlge in degrees from zero reference
     * 
     * @param angle
     */
    public void setShoulderAngle(double angle) {
        if (!enabled)
            return;

        this.shoulder.set(shoulderPidController.calculate(angle, this.shoulderEncoder.getPosition(), 2));
    }

    /**
     * Sets wrist angle in degrees from zero reference
     * 
     * @param angle
     */
    public void setWristAngle(double angle) {
        if (!enabled)
            return;

        this.wrist.set(wristPidController.calculate(angle, this.wristEncoder.getPosition(), 2));
    }

    /**
     * Gets shoulder absolute encoder object
     * 
     * @return Shoulder Absolute Encoder
     */
    public SparkMaxAbsoluteEncoder getShoulderEncoder() {
        if (!enabled)
            return null;

        return this.shoulderEncoder;
    }

    /**
     * Gets wrist absolute encoder object
     * 
     * @return Wrist Absolute Encoder
     */
    public SparkMaxAbsoluteEncoder getWristEncoder() {
        if (!enabled)
            return null;

        return this.wristEncoder;
    }

    /**
     * Resets appendages to zero position
     */
    public void resetPositions() {
        if (!enabled)
            return;

        this.setShoulderAngle(0);
        this.setWristAngle(0);
    }

    /**
     * Resets arm and wrist encoders to zero position
     * 
     * <p>
     * Deprecated since absolute encoders should not be reset through code
     */
    @Deprecated
    public void resetInputs() {
        if (!enabled)
            return;
    }

    @Deprecated
    public void updateInputs() {
        if (!enabled)
            return;
    }

    /**
     * Disables shoulder and wrist motors
     */
    @Override
    public void stopAllOutputs() {
        if (!enabled)
            return;

        this.shoulder.disable();
        this.wrist.disable();
    }
}