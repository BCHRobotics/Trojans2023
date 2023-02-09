package frc.io.subsystems;

// Import required Libraries
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

// Import required Classes
import frc.robot.Constants;

public class IntakeIO implements IIO {

    private static IntakeIO instance;

    private CANSparkMax intakeMotor;

    private RelativeEncoder intakeEncoder;

    private Compressor compressor;

    private Solenoid intakeArmRaised;
    private Solenoid intakeArmLowered;

    private boolean enabled = Constants.INTAKE_ENABLED;

    private boolean intakeState = false;

    private double intakeSpeed;

    public static IntakeIO getInstance() {
        if (instance == null) {
            instance = new IntakeIO();
        }
        return instance;
    }

    /**
     * Initiates the Intake / Stager / Feeder Output
     */
    private IntakeIO() {
        if (!enabled)
            return;

        // Initiate Pneumatic systems
        this.compressor = new Compressor(0, PneumaticsModuleType.CTREPCM);
        this.intakeArmRaised = new Solenoid(0, PneumaticsModuleType.CTREPCM, Constants.INTAKE_RAISED);
        this.intakeArmLowered = new Solenoid(0, PneumaticsModuleType.CTREPCM, Constants.INTAKE_LOWERED);

        // Enable compressor
        this.compressor.isEnabled();

        // Initiate new motor objects
        this.intakeMotor = new CANSparkMax(Constants.INTAKE_ID, MotorType.kBrushless);

        // Get motor encoder
        this.intakeEncoder = intakeMotor.getEncoder();

        // Restore motor controllers to factory defaults
        this.intakeMotor.restoreFactoryDefaults();

        // Set motor controllers Idle Mode [Brake/Coast]
        this.intakeMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);

        // Inversion state of motors
        this.intakeMotor.setInverted(false);

        // Send out settings to controller
        this.intakeMotor.burnFlash();
    }

    /**
     * Set the speed of the Intake
     * 
     * @param speed in decimal
     */
    public void setIntakeSpeed(double speed) {
        if (!enabled)
            return;
        this.intakeSpeed = speed;
        this.intakeMotor.set(speed);
    }

    /**
     * Set the position of the intake
     * 
     * @param state boolean state of intake.
     *              { FALSE: Raised | TRUE: Lowered }
     */
    public void setIntakeState(boolean state) {
        if (!enabled)
            return;
        this.intakeState = state;
        this.intakeArmRaised.set(!state);
        this.intakeArmLowered.set(state);
    }

    /**
     * Get the state of the intake arms
     * 
     * @return { FALSE: Raised | TRUE: Lowered }
     */
    public Boolean getIntakeState() {
        if (!enabled)
            return null;
        return this.intakeState;
    }

    /**
     * Get the percent output of the intake
     * 
     * @return motor perecentage in decimal
     */
    public double getIntakePercent() {
        if (!enabled)
            return 0.0;
        return this.intakeSpeed;
    }

    /**
     * Get the reference to the Intake encoder
     * 
     * @return CANEncoder reference
     */
    public RelativeEncoder getIntakeEncoder() {
        if (!enabled)
            return null;
        return this.intakeMotor.getEncoder();
    }

    /**
     * Reset the state of the inputs
     */
    @Override
    public void updateInputs() {
        if (!enabled)
            return;
    }

    @Override
    public void resetInputs() {
        if (!enabled)
            return;
        this.intakeEncoder.setPosition(0);
    }

    /**
     * Stop all motors
     */
    @Override
    public void stopAllOutputs() {
        if (!enabled)
            return;
        this.intakeMotor.disable();
    }

}