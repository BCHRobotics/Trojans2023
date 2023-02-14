package frc.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.io.DriverInput;
import frc.robot.Constants;
import frc.commands.chasis.Drive;
import frc.commands.chasis.Pickup;

public class TeleopDriver implements TeleopComponent {
    private static TeleopDriver instance;

    private Drive drive;
    private Pickup intake;

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
        this.intake = Pickup.getInstance();

        // HI MY NAME IS NOAH!
    }

    @Override
    public void firstCycle() {
        this.drive.firstCycle();
        this.intake.firstCycle();
    }

    @Override
    public void run() {

        SmartDashboard.putNumber("Max Drive Speed %", DriverInput.getDriveMaxSpeed() * 100);

        if (!this.drive.getPositionMode())
            this.drive.setBrakes(DriverInput.getDriveBrakes());

        if (DriverInput.getBalanceMode() && Constants.GYRO_ENABLED) {
            this.frwd = this.drive.balance();
            this.turn = 0;
        } else {
            this.drive.balanceIdle();
            this.frwd = DriverInput.getDriveFrwd();
            this.turn = DriverInput.getDriveTurn();
            this.intake.setIntakeSpeed(0.8);
            this.intake.setIntakeState(DriverInput.getIntakeState());
        }

        this.drive.setOutput(frwd, turn);
        this.drive.run();
        this.intake.run();

    }

    @Override
    public void disable() {
        this.drive.disable();
        this.intake.disable();
    }
}
