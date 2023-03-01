package frc.peripherals.robot;

// Import required Libraries
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;
import com.revrobotics.SparkMaxPIDController.AccelStrategy;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants.Claw;
import frc.robot.Constants.Features;
import frc.robot.Constants.Misc;
import frc.util.control.SparkMaxConstants;
import frc.util.control.SparkMaxPID;

public class ClawIO implements IIO {
    private static ClawIO instance;

    // Claw motors
    private CANSparkMax grip;
    private CANSparkMax leftPump;
    private CANSparkMax midPump;
    private CANSparkMax rightPump;

    // Claw solenoids
    private Solenoid leftBleedValve;
    private Solenoid rightBleedValve;

    // Claw Status LEDs
    private DigitalOutput coneLED;
    private DigitalOutput cubeLED;

    // Claw encoders
    private RelativeEncoder clawEncoder;

    // PID Controllers
    private SparkMaxPID clawPidController;

    // PID Constants
    private SparkMaxConstants clawConstants = Claw.CONSTANTS;

    private DigitalInput forwardLimit;

    private boolean enabled = Features.CLAW_ENABLED;
    private boolean calibrated = false;

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
        this.grip = new CANSparkMax(Claw.MOTOR_ID, MotorType.kBrushless);
        this.leftPump = new CANSparkMax(Claw.LEFT_PUMP_ID, MotorType.kBrushed);
        this.midPump = new CANSparkMax(Claw.MID_PUMP_ID, MotorType.kBrushed);
        this.rightPump = new CANSparkMax(Claw.RIGHT_PUMP_ID, MotorType.kBrushed);

        this.leftBleedValve = new Solenoid(PneumaticsModuleType.CTREPCM, Claw.LEFT_BLEED_VALVE);
        this.rightBleedValve = new Solenoid(PneumaticsModuleType.CTREPCM, Claw.RIGHT_BLEED_VALVE);

        this.coneLED = new DigitalOutput(Misc.CONE_LED_PORT);
        this.cubeLED = new DigitalOutput(Misc.CUBE_LED_PORT);

        this.clawEncoder = grip.getEncoder();

        this.grip.restoreFactoryDefaults();

        this.grip.setIdleMode(CANSparkMax.IdleMode.kBrake);

        this.grip.setSmartCurrentLimit(20, 10);

        this.grip.setInverted(false);

        this.grip.enableSoftLimit(SoftLimitDirection.kForward, false);
        this.grip.getReverseLimitSwitch(Type.kNormallyOpen).enableLimitSwitch(true);

        this.grip.setSoftLimit(SoftLimitDirection.kForward, Claw.LIMIT);

        this.clawPidController = new SparkMaxPID(this.grip, this.clawConstants);

        this.clawPidController.setFeedbackDevice(clawEncoder);

        this.clawEncoder.setPositionConversionFactor(Claw.CONVERSION_FACTOR);

        this.clawPidController.setMotionProfileType(AccelStrategy.kSCurve);

        this.forwardLimit = new DigitalInput(Claw.LIMIT_SWITCH_PORT);
    }

    /**
     * Sets claw travel in inches
     * 
     * @param position
     */
    public void setClawPosition(double position) {

        if ((!enabled) || (!this.calibrated))
            return;

        // || (!this.forwardLimit.get() && position >= 1)
        if ((this.grip.getReverseLimitSwitch(Type.kNormallyOpen).isPressed() && position <= 0)) {
            this.clawEncoder.setPosition(position);
            return;
        } else if (Misc.WITHIN_TOLERANCE(this.getClawEncoder().getPosition(), position, 0.05)) {
            this.grip.set(0);
            return;
        } else {
            this.clawPidController.setSmartPosition(position, Claw.DEFAULT_OFFSET,
                    Claw.LIMIT);
        }
    }

    /**
     * Sets pneumatic suction state boolean
     * 
     * @param state
     */
    public void setLeftPump(boolean state) {
        if (!enabled)
            return;

        this.leftPump.set(state ? 1 : 0);
    }

    /**
     * Sets pneumatic suction state boolean
     * 
     * @param state
     */
    public void setMidPump(boolean state) {
        if (!enabled)
            return;

        this.midPump.set(state ? 1 : 0);
    }

    /**
     * Sets pneumatic suction state boolean
     * 
     * @param state
     */
    public void setRightPump(boolean state) {
        if (!enabled)
            return;

        this.rightPump.set(state ? 1 : 0);
    }

    /**
     * Sets pneumatic bleed valve state boolean
     * 
     * @param state
     */
    public void setLeftValve(boolean state) {
        if (!enabled)
            return;

        this.leftBleedValve.set(state); // TODO: Check Normal solenoid state
    }

    /**
     * Sets pneumatic bleed valve state boolean
     * 
     * @param state
     */
    public void setRightValve(boolean state) {
        if (!enabled)
            return;

        this.rightBleedValve.set(state);
    }

    /**
     * Sets status LED
     * 
     * @param cone
     * @param cube
     */
    public void setStatusLED(boolean cone, boolean cube) {
        if (!enabled)
            return;

        this.coneLED.set(cone);
        this.cubeLED.set(cube);
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

        this.setClawPosition(0);
        this.setLeftPump(false);
        this.setMidPump(false);
        this.setRightPump(false);
        this.setLeftValve(false);
        this.setRightValve(false);
    }

    /**
     * Resets claw encoder to zero position
     * 
     * <p>
     * Deprecated since claw encoders should not be reset outside of class
     */
    @Deprecated
    public void resetInputs() {
        if (!enabled)
            return;
        this.calibrated = false;
    }

    public void push() {
        this.clawPidController.pushConstantsToDashboard("Claw");
    }

    @Override
    public void updateInputs() {
        this.clawPidController.retrieveDashboardConstants();

        if ((!enabled) || (this.calibrated))
            return;

        if (this.grip.getReverseLimitSwitch(Type.kNormallyOpen).isPressed()) {
            this.grip.set(0);
            this.clawEncoder.setPosition(0);
            this.calibrated = true;
        } else
            this.grip.set((double) -0.4);

        System.out.println("Calibration Status: " + this.calibrated);
    }

    /**
     * Disables claw and pump motors
     */
    @Override
    public void stopAllOutputs() {
        if (!enabled)
            return;

        this.grip.disable();
        this.leftPump.disable();
        this.midPump.disable();
        this.rightPump.disable();
    }

}