package frc.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.peripherals.user.DriverInput;
import frc.subsystems.Drivetrain;
import frc.subsystems.Drivetrain.DriveState;

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

        SmartDashboard.putNumber("Max Drive Speed %", Math.floor(DriverInput.getDriveMaxSpeed() * 100));

        if (DriverInput.getBalanceMode()) {
            // this.drive.setPosition(48, 48);
            this.drive.setOutput(this.drive.balancePID(), 0);
            this.drive.setDriveMode(DriveState.BALANCE);
        } else {
            this.frwd = DriverInput.getDriveFrwd();
            this.turn = DriverInput.getDriveTurn();
            this.drive.setBrakes(DriverInput.getBrakeMode());
            this.drive.setOutput(frwd, turn);
        }

        if (DriverInput.getController().getBButton()) {
            this.drive.resetEncoderPosition();
        }

        if (DriverInput.getController().getXButton()) {
            this.drive.calculatePath();
        } else if (DriverInput.getController().getXButtonReleased()) {
            this.drive.followPath();
        } else {
            this.drive.clearPath();
        }

        if (DriverInput.getABS())
            this.drive.setOutput(0, 0);

        this.drive.run();

    }

    @Override
    public void disable() {
        this.drive.disable();
    }
}
