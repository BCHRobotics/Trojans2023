package frc.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.commands.Drive;
import frc.io.DriverInput;

public class TeleopDriver implements TeleopComponent {
    private static TeleopDriver instance;

    private Drive drive;

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
        this.drive = Drive.getInstance();
    }

    @Override
    public void firstCycle() {
        this.drive.firstCycle();
    }

    @Override
    public void run() {

        SmartDashboard.putNumber("Max Drive Speed %", DriverInput.getDriveMaxSpeed() * 100);

        if (DriverInput.getBalanceMode())
            this.drive.balance();
        else {
            this.drive.unrestrained();
            this.frwd = DriverInput.getDriveFrwd();
            this.turn = DriverInput.getDriveTurn();
            this.drive.setBrakes(DriverInput.getDriveBrakes());
        }

        this.drive.setOutput(frwd, turn);
        this.drive.run();

    }

    @Override
    public void disable() {
        this.drive.disable();
    }
}
