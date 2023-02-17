package frc.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.subsystems.Drivetrain;
import frc.io.DriverInput;

public class TeleopDriver implements TeleopComponent {
    private static TeleopDriver instance;

    private Drivetrain drive;

    private double frwd = 0;
    private double turn = 0;

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
        this.drive = Drivetrain.getInstance();
    }

    @Override
    public void firstCycle() {
        this.drive.firstCycle();
    }

    @Override
    public void run() {

        SmartDashboard.putNumber("Max Drive Speed %", DriverInput.getDriveMaxSpeed() * 100);

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

        this.drive.run();

    }

    @Override
    public void disable() {
        this.drive.disable();
    }
}
