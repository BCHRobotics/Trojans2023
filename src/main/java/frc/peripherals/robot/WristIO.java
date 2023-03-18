package frc.peripherals.robot;

// Import required Libraries
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;
import com.revrobotics.SparkMaxPIDController.AccelStrategy;

// Import required Modules
import frc.robot.Constants.*;
import frc.util.control.SparkMaxPID;

public class WristIO implements IIO {
    private static WristIO instance;

    // Wrist motor
    private CANSparkMax wrist;

    // Wrist encoder
    private SparkMaxAbsoluteEncoder wristEncoder;

    // Wrist PID Controller
    private SparkMaxPID wristController;

    // Wrist enabled / disabled
    private boolean enabled = ROBOT.WRIST_ENABLED;

    public static WristIO getInstance() {
        if (instance == null)
            instance = new WristIO();

        return instance;
    }

    private WristIO() {
        if (!enabled)
            return;

        initPeripherals();
    }

    /**
     * Initializes wrist peripherals
     */
    private void initPeripherals() {

        // Initialize motor controller
        this.wrist = new CANSparkMax(WRIST.ID, MotorType.kBrushless);
        this.wrist.restoreFactoryDefaults();
        this.wrist.setIdleMode(CANSparkMax.IdleMode.kBrake);
        this.wrist.setSmartCurrentLimit(40, 20);
        this.wrist.setInverted(false);

        // Initialize encoder
        this.wristEncoder = this.wrist.getAbsoluteEncoder(Type.kDutyCycle);
        this.wristEncoder.setInverted(WRIST.ENCODER_INVERTED);
        this.wristEncoder.setPositionConversionFactor(WRIST.CONVERSION_FACTOR);
        this.wristEncoder.setZeroOffset(WRIST.ENCODER_OFFSET);

        // Setup soft limits
        this.wrist.enableSoftLimit(SoftLimitDirection.kForward, true);
        this.wrist.enableSoftLimit(SoftLimitDirection.kReverse, true);
        this.wrist.setSoftLimit(SoftLimitDirection.kForward, WRIST.LIMIT);
        this.wrist.setSoftLimit(SoftLimitDirection.kReverse, (float) WRIST.DEFAULT_OFFSET);

        // Initialize PID controller
        this.wristController = new SparkMaxPID(this.wrist, WRIST.CONTROL_CONSTANTS);
        this.wristController.setFeedbackDevice(this.wristEncoder);
        this.wristController.setPIDWrapping(false);
        this.wristController.setMotionProfileType(AccelStrategy.kSCurve);
    }

    /**
     * Sets wrist anlge in degrees from the "Normal/Perpendicular - Wrist Default
     * Offset" line to the parallel line taking into account the shoulder angle to
     * prevent wrist from driving into the ground
     * 
     * @param wristAngle
     * @param shoulderAngle
     */
    public void setWristAngle(double wristAngle, double shoulderAngle) {
        if (!enabled || ROBOT.WITHIN_TOLERANCE(this.wristEncoder.getPosition(), wristAngle, WRIST.TOLERANCE))
            return;

        this.wristController.setSmartPosition(wristAngle, WRIST.DEFAULT_OFFSET,
                shoulderAngle < ROBOT.ARM_MAX_HEIGHT ? ((shoulderAngle - SHOULDER.DEFAULT_OFFSET)
                        / (ROBOT.ARM_MAX_HEIGHT - SHOULDER.DEFAULT_OFFSET)) * (WRIST.LIMIT - WRIST.PARALLEL_OFFSET)
                        + WRIST.PARALLEL_OFFSET + WRIST.DEFAULT_OFFSET : WRIST.LIMIT);
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
     * Initializes wrist motors
     */
    @Override
    public void init() {
        if (!enabled)
            return;

        this.reset();
    }

    /**
     * Updates wrist peripherals
     */
    @Override
    public void update() {
        if (!enabled)
            return;
    }

    /**
     * Resets wrist to zero position
     */
    @Override
    public void reset() {
        if (!enabled)
            return;

        this.setWristAngle(0, SHOULDER.DEFAULT_OFFSET);
    }

    /**
     * Disables wrist motor
     */
    @Override
    public void disable() {
        if (!enabled)
            return;

        this.wrist.disable();
    }
}