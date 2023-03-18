package frc.peripherals.robot;

// Import required Libraries
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;
import com.revrobotics.SparkMaxPIDController.AccelStrategy;

// Import required Modules
import frc.robot.Constants.*;
import frc.util.control.SparkMaxPID;

public class ShoulderIO implements IIO {
    private static ShoulderIO instance;

    // Shoulder motor
    private CANSparkMax shoulder;

    // Shoulder encoder
    private SparkMaxAbsoluteEncoder shoulderEncoder;

    // Shoulder PID Controller
    private SparkMaxPID shoulderController;

    // Shoulder enabled / disabled
    private boolean enabled = ROBOT.SHOULDER_ENABLED;

    public static ShoulderIO getInstance() {
        if (instance == null)
            instance = new ShoulderIO();

        return instance;
    }

    private ShoulderIO() {
        if (!enabled)
            return;

        initPeripherals();
    }

    /**
     * Initializes arm peripherals
     */
    private void initPeripherals() {

        // Initialize motor controller
        this.shoulder = new CANSparkMax(SHOULDER.ID, MotorType.kBrushless);
        this.shoulder.restoreFactoryDefaults();
        this.shoulder.setIdleMode(CANSparkMax.IdleMode.kBrake);
        this.shoulder.setSmartCurrentLimit(60, 20);
        this.shoulder.setInverted(false);

        // Initialize encoder
        this.shoulderEncoder = this.shoulder.getAbsoluteEncoder(Type.kDutyCycle);
        this.shoulderEncoder.setInverted(SHOULDER.ENCODER_INVERTED);
        this.shoulderEncoder.setPositionConversionFactor(SHOULDER.CONVERSION_FACTOR);
        this.shoulderEncoder.setZeroOffset(SHOULDER.ENCODER_OFFSET);

        // Setup soft limits
        this.shoulder.enableSoftLimit(SoftLimitDirection.kForward, true);
        this.shoulder.enableSoftLimit(SoftLimitDirection.kReverse, true);
        this.shoulder.setSoftLimit(SoftLimitDirection.kForward, SHOULDER.LIMIT);
        this.shoulder.setSoftLimit(SoftLimitDirection.kReverse, (float) SHOULDER.DEFAULT_OFFSET);

        // Initialize PID controller
        this.shoulderController = new SparkMaxPID(this.shoulder, SHOULDER.CONTROL_CONSTANTS);
        this.shoulderController.setFeedbackDevice(this.shoulderEncoder);
        this.shoulderController.setPIDWrapping(false);
        this.shoulderController.setMotionProfileType(AccelStrategy.kSCurve);
    }

    /**
     * Sets shoulder anlge in degrees from the "Normal/Perpendicular" line to the
     * "Parallel" line
     * 
     * @param angle
     */
    public void setShoulderAngle(double angle) {
        if (!enabled || ROBOT.WITHIN_TOLERANCE(this.shoulderEncoder.getPosition(), angle, SHOULDER.TOLERANCE))
            return;

        this.shoulderController.setSmartPosition(angle, SHOULDER.DEFAULT_OFFSET, SHOULDER.LIMIT);
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
     * Initializes shoulder motors
     */
    @Override
    public void init() {
        if (!enabled)
            return;

        this.reset();
    }

    /**
     * Updates shoulder peripherals
     */
    @Override
    public void update() {
        if (!enabled)
            return;
    }

    /**
     * Resets shoulder to zero position
     */
    @Override
    public void reset() {
        if (!enabled)
            return;

        this.setShoulderAngle(0);
    }

    /**
     * Disables shoulder motor
     */
    @Override
    public void disable() {
        if (!enabled)
            return;

        this.shoulder.disable();
    }
}