package frc.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.subsystems.Drivetrain;
import frc.util.imaging.Limelight;
import frc.io.DriverInput;

public class TeleopDriver implements TeleopComponent {
    private static TeleopDriver instance;

    private Drivetrain drive;
    private Limelight limelight;

    private double frwd = 0;
    private double turn = 0;
    private double angle = 0;

    /**
     * Get the instance of the TeleopDriver, if none create a new instance
     * 
     * @return instance of the TeleopDriver
     */
    public static TeleopDriver getInstance() {
        if (instance == null) {
            instance = new TeleopDriver();
        }
        return instance;
    }

    private TeleopDriver() {
        this.limelight = Limelight.getInstance();
        this.drive = Drivetrain.getInstance();
    }

    @Override
    public void firstCycle() {
        this.drive.firstCycle();
    }

    @Override
    public void run() {

        SmartDashboard.putNumber("Max Drive Speed %", DriverInput.getDriveMaxSpeed() * 100);
        SmartDashboard.putNumber("tX", this.limelight.getTargetX());
        SmartDashboard.putNumber("tY", this.limelight.getTargetY());
        SmartDashboard.putBoolean("tV", this.limelight.getTargetExists());
        SmartDashboard.putNumber("pipeline", this.limelight.getPipeline());

        if (DriverInput.getController().getBButton())
            this.drive.resetEncoderPosition();

        // this.drive.balancePID(DriverInput.getBalanceMode());
        if (DriverInput.getBalanceMode()) {
            this.drive.setPosition(141, 141);
        } else if (!DriverInput.getBalanceMode()) {
            this.frwd = DriverInput.getDriveFrwd();
            this.turn = DriverInput.getDriveTurn();
            this.drive.setBrakes(DriverInput.getDriveBrakes());
            this.drive.setOutput(frwd, turn);
        }

        if(DriverInput.getController().getAButton()) {
            //limelight.goToApril();

            //temp code
            this.angle = limelight.getTargetX();

            if(angle >= 0.5){

                this.drive.setOutput(0.0, 0.2);

            } else if(angle <= -0.5){

                this.drive.setOutput(0.0, -0.2);

            }
        }

        this.drive.run();

    }

    @Override
    public void disable() {
        this.drive.disable();
    }
}
