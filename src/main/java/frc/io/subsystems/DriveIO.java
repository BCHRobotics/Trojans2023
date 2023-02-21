package frc.io.subsystems;

// Import required Libraries
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// Import required Classes
import frc.robot.Constants;
import frc.util.control.SparkMaxPID;

public class DriveIO implements IIO {
    private static DriveIO instance;

    // Drive motors
    private CANSparkMax driveL1;
    private CANSparkMax driveR1;
    private CANSparkMax driveL2;
    private CANSparkMax driveR2;

    // Brake mode
    private IdleMode idleMode;

    // Drive encoders
    private RelativeEncoder driveL1Encoder;
    private RelativeEncoder driveL2Encoder;
    private RelativeEncoder driveR1Encoder;
    private RelativeEncoder driveR2Encoder;

    // PID Controllers
    private SparkMaxPID driveL1PidController;
    private SparkMaxPID driveR1PidController;

    private boolean enabled = Constants.DRIVE_ENABLED;
    private boolean miniBot = Constants.MINI_BOT;

    public static DriveIO getInstance() {
        if (instance == null) {
            instance = new DriveIO();
        }
        return instance;
    }

    private DriveIO() {
        if (!enabled)
            return;

        initMainMotors();

        if (!miniBot)
            initFollowMotors();
    }

    /**
     * Initializes primary drive motors
     */
    private void initMainMotors() {
        this.driveL1 = new CANSparkMax(Constants.DRIVE_LEFT1_ID, MotorType.kBrushless);
        this.driveR1 = new CANSparkMax(Constants.DRIVE_RIGHT1_ID, MotorType.kBrushless);

        this.driveL1Encoder = driveL1.getEncoder();
        this.driveR1Encoder = driveR1.getEncoder();

        this.driveL1.restoreFactoryDefaults();
        this.driveR1.restoreFactoryDefaults();

        this.driveL1.setIdleMode(CANSparkMax.IdleMode.kCoast);
        this.driveR1.setIdleMode(CANSparkMax.IdleMode.kCoast);

        this.driveL1.setSmartCurrentLimit(80, 20);
        this.driveR1.setSmartCurrentLimit(80, 20);

        this.driveL1PidController = new SparkMaxPID(this.driveL1, Constants.DRIVEL1_CONSTANTS);
        this.driveR1PidController = new SparkMaxPID(this.driveR1, Constants.DRIVER1_CONSTANTS);

        this.driveL1PidController.setFeedbackDevice(driveL1Encoder);
        this.driveR1PidController.setFeedbackDevice(driveR1Encoder);

        this.driveL1.setInverted(Constants.DRIVE_INVERTED);
        this.driveR1.setInverted(!Constants.DRIVE_INVERTED);

        this.driveL1Encoder.setPositionConversionFactor(Constants.CHASIS_LEFT_POS_CONVERSION);
        this.driveR1Encoder.setPositionConversionFactor(Constants.CHASIS_RIGHT_POS_CONVERSION);

        this.driveL1Encoder.setVelocityConversionFactor(Constants.CHASIS_LEFT_VEL_CONVERSION);
        this.driveR1Encoder.setVelocityConversionFactor(Constants.CHASIS_RIGHT_VEL_CONVERSION);
    }

    /**
     * Initializes secondary drive motors
     */
    private void initFollowMotors() {
        this.driveL2 = new CANSparkMax(Constants.DRIVE_LEFT2_ID, MotorType.kBrushless);
        this.driveR2 = new CANSparkMax(Constants.DRIVE_RIGHT2_ID, MotorType.kBrushless);

        this.driveL2Encoder = driveL2.getEncoder();
        this.driveR2Encoder = driveR2.getEncoder();

        this.driveL2.restoreFactoryDefaults();
        this.driveR2.restoreFactoryDefaults();

        this.driveL2.setIdleMode(this.driveL1.getIdleMode());
        this.driveR2.setIdleMode(this.driveR1.getIdleMode());

        this.driveL2.setSmartCurrentLimit(80, 20);
        this.driveR2.setSmartCurrentLimit(80, 20);

        this.driveL2.follow(this.driveL1, Constants.DRIVE_OUT_OF_SYNC);
        this.driveR2.follow(this.driveR1, Constants.DRIVE_OUT_OF_SYNC);

        this.driveL2Encoder.setPositionConversionFactor(Constants.CHASIS_LEFT_POS_CONVERSION);
        this.driveR2Encoder.setPositionConversionFactor(Constants.CHASIS_RIGHT_POS_CONVERSION);

        this.driveL2Encoder.setVelocityConversionFactor(Constants.CHASIS_LEFT_VEL_CONVERSION);
        this.driveR2Encoder.setVelocityConversionFactor(Constants.CHASIS_RIGHT_VEL_CONVERSION);
    }

    /**
     * Sets drive left speed in percent output -1 --> 1
     * 
     * @param speed
     */
    public void setDriveLeft(double speed) {
        if (!enabled)
            return;
        this.driveL1.set(speed);
    }

    /**
     * Sets drive right speed in percent output -1 --> 1
     * 
     * @param speed
     */
    public void setDriveRight(double speed) {
        if (!enabled)
            return;
        this.driveR1.set(speed);
    }

    /**
     * Sets drive left position in inches
     * 
     * @param position
     */
    public void setDriveLeftPos(double position) {
        if (!enabled)
            return;
        this.driveL1PidController.retrieveDashboardConstants(Constants.DRIVEL1_CONSTANTS, "Drive Left");
        this.driveL1PidController.setPosition(position);
    }

    /**
     * Sets drive right position in inches
     * 
     * @param position
     */
    public void setDriveRightPos(double position) {
        if (!enabled)
            return;
        this.driveR1PidController.retrieveDashboardConstants(Constants.DRIVER1_CONSTANTS, "Drive Right");
        this.driveR1PidController.setPosition(position);
    }

    /**
     * Sets braking mode
     * 
     * @param mode
     */
    public void brakeMode(boolean mode) {
        if (!enabled)
            return;

        this.idleMode = mode ? CANSparkMax.IdleMode.kBrake : CANSparkMax.IdleMode.kCoast;

        this.driveL1.setIdleMode(this.idleMode);
        this.driveR1.setIdleMode(this.idleMode);
        this.driveL1.burnFlash();
        this.driveR1.burnFlash();

        if (!miniBot) {
            this.driveL2.setIdleMode(this.idleMode);
            this.driveR2.setIdleMode(this.idleMode);
            this.driveL2.burnFlash();
            this.driveR2.burnFlash();
        }

        SmartDashboard.putBoolean("Brake Mode", mode);
    }

    /**
     * Gets drivetrain front left relative encoder object
     * 
     * @return Drive Front Left Relative Encoder
     */
    public RelativeEncoder getDriveL1Encoder() {
        if (!enabled)
            return null;
        return this.driveL1Encoder;
    }

    /**
     * Gets drivetrain front right relative encoder object
     * 
     * @return Drive Front Right Relative Encoder
     */
    public RelativeEncoder getDriveR1Encoder() {
        if (!enabled)
            return null;
        return this.driveR1Encoder;
    }

    /**
     * Gets drivetrain back left relative encoder object
     * 
     * @return Drive Back Left Relative Encoder
     */
    public RelativeEncoder getDriveL2Encoder() {
        if (!enabled || !miniBot)
            return null;
        return this.driveL2Encoder;
    }

    /**
     * Gets drivetrain back right relative encoder object
     * 
     * @return Drive Back Right Relative Encoder
     */
    public RelativeEncoder getDriveR2Encoder() {
        if (!enabled || !miniBot)
            return null;
        return this.driveR2Encoder;
    }

    /**
     * Resets relative encoders to zero position
     */
    @Override
    public void resetInputs() {
        if (!enabled)
            return;

        this.driveL1Encoder.setPosition(0);
        this.driveR1Encoder.setPosition(0);

        if (!miniBot) {
            this.driveL2Encoder.setPosition(0);
            this.driveR2Encoder.setPosition(0);
        }
    }

    @Deprecated
    public void updateInputs() {
        if (!enabled)
            return;
    }

    /**
     * Disables all drive motors
     */
    @Override
    public void stopAllOutputs() {
        if (!enabled)
            return;

        this.brakeMode(true);

        this.driveL1.disable();
        this.driveR1.disable();

        if (!miniBot) {
            this.driveL2.disable();
            this.driveR2.disable();
        }
    }
}