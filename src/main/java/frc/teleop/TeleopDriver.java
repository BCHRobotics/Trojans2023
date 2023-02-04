package frc.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.io.DriverInput;
import frc.robot.Constants;
import frc.subsystems.Drivetrain;
import frc.subsystems.Mechanism;

public class TeleopDriver implements TeleopComponent {
    private static TeleopDriver instance;

    private Drivetrain drivetrain;
    private Mechanism mechanism;

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
        this.drivetrain = Drivetrain.getInstance();
        this.mechanism = Mechanism.getInstance();
    }

    @Override
    public void firstCycle() {
        this.drivetrain.firstCycle();
        this.mechanism.firstCycle();
    }

    @Override
    public void run() {

        SmartDashboard.putNumber("Max Drive Speed %", DriverInput.getDriveMaxSpeed() * 100);

        if (!this.drivetrain.getPositionMode())
            this.drivetrain.setBrakes(DriverInput.getDriveBrakes());

        if (DriverInput.getBalanceMode()) {
            this.frwd = this.drivetrain.balance();
            this.turn = 0;
        } else {
            this.drivetrain.balanceIdle();
            this.frwd = DriverInput.getDriveFrwd();
            this.turn = DriverInput.getDriveTurn();
        }

        if (DriverInput.getDpadInput() == 0)
            this.mechanism.setClawPos(Constants.CONE_PRESET);
        else if (DriverInput.getDpadInput() == 90)
            this.mechanism.setClawPos(Constants.CUBE_PRESET);
        else
            this.mechanism.resetPosition();

        this.drivetrain.setOutput(frwd, turn);
        this.drivetrain.run();
        this.mechanism.run();

    }

    @Override
    public void disable() {
        this.drivetrain.disable();
    }
}
