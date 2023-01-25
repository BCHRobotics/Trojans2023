package frc.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.io.DriverInput;
import frc.robot.Constants;
import frc.subsystems.Drivetrain;
import frc.util.devices.Controller;
import frc.util.devices.Controller.Axis;
import frc.util.devices.Controller.Side;

public class TeleopDriver extends TeleopComponent {
    private static TeleopDriver instance;

    private Drivetrain drivetrain;

    private Controller driverController;
    private DriverInput driverInput;

    private double straight = 0;
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
        this.driverController = driverInput.getDriverController();
    }

    @Override
    public void firstCycle() {
        this.drivetrain.firstCycle();
        SmartDashboard.putNumber("Intake Motor Speed", 0.8);
    }

    @Override
    public void run() {

        double speed = Constants.MAX_OUTPUT;

        speed -= (this.driverController.getLeftTriggerAxis() * Constants.MAX_INTERVAL);
        speed += (this.driverController.getRightTriggerAxis() * Constants.MAX_INTERVAL);

        SmartDashboard.putNumber("Drive Output", speed);

        this.straight = this.driverController.getJoystick(Side.LEFT, Axis.Y) * speed;
        this.turn = this.driverController.getJoystick(Side.RIGHT, Axis.X) * speed;

        if (!this.drivetrain.getPositionMode()) {
            if (this.driverController.getLeftBumper())
                this.drivetrain.brake(true);
            else
                this.drivetrain.brake(false);
        }

        if (this.driverController.getAButton()) {
            this.straight = this.drivetrain.balance();
            this.drivetrain.brake(true);
            this.turn = 0;
        } else
            this.drivetrain.balanceIdle();

        this.drivetrain.setOutput(straight, turn);
        this.drivetrain.run();

    }

    @Override
    public void disable() {
        this.drivetrain.disable();
    }
}
