package frc.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.io.DriverInput;
import frc.subsystems.Drivetrain;

public class TeleopDriver extends TeleopComponent {
    private static TeleopDriver instance;

    private Drivetrain drivetrain;
    private DriverInput driverInput;

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
        this.driverInput = DriverInput.getInstance();
        this.drivetrain = Drivetrain.getInstance();
    }

    @Override
    public void firstCycle() {
        this.drivetrain.firstCycle();
    }

    @Override
    public void run() {

        SmartDashboard.putNumber("Max Drive Speed %", this.driverInput.getDriveMaxSpeed() * 100);

        if (!this.drivetrain.getPositionMode())
            this.drivetrain.setBrakes(this.driverInput.getDriveBrakes());

        if (this.driverInput.getBalanceMode()) {
            this.frwd = this.drivetrain.balance();
            this.turn = 0;
        } else {
            this.drivetrain.balanceIdle();
            this.frwd = this.driverInput.getDriveFrwd();
            this.turn = this.driverInput.getDriveTurn();
        }

        this.drivetrain.setOutput(frwd, turn);
        this.drivetrain.run();

    }

    @Override
    public void disable() {
        this.drivetrain.disable();
    }
}
