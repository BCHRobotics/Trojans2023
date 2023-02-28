package frc.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.subsystems.Mechanism;
import frc.peripherals.user.OperatorInput;
import frc.robot.Constants.Misc;
import frc.robot.Constants.Misc.StatusLED;

public class TeleopOperator implements TeleopComponent {
    private static TeleopOperator instance;

    private Mechanism mech;

    /**
     * Get the instance of the TeleopDriver, if none create a new instance
     * 
     * @return instance of the TeleopDriver
     */
    public static TeleopOperator getInstance() {
        if (instance == null) {
            instance = new TeleopOperator();
        }
        return instance;
    }

    private TeleopOperator() {
        this.mech = Mechanism.getInstance();
    }

    @Override
    public void firstCycle() {
        this.mech.firstCycle();
    }

    @Override
    public void run() {

        // if (OperatorInput.getGamePiece() == 0)
        // this.mech.setClawPos(Misc.CONE_PRESET);
        // else if (OperatorInput.getGamePiece() == 270)
        // this.mech.setClawPos(Misc.CUBE_PRESET);
        // else if (OperatorInput.getGamePiece() == 180)
        // this.mech.setClawPos(0);

        // this.mech.setWristOffset(OperatorInput.getWristOffset());
        // this.mech.setWristHeight(OperatorInput.getTestOffset());

        if (OperatorInput.getTestButton()) {
            this.mech.setStatusLED(StatusLED.CONE);
            this.mech.setClawPos(0.8);
            this.mech.setSuctionMode(false);
            this.mech.setBleedMode(false);
        } else {
            this.mech.setStatusLED(StatusLED.CUBE);
            this.mech.setClawPos(0);
            this.mech.setSuctionMode(false);
        }

        if (OperatorInput.getController().getAButton())
            this.mech.setBleedMode(true);

        this.mech.setShoulderAngle(OperatorInput.getTestOffset());
        this.mech.setWristAngle(OperatorInput.getWristOffset());

        this.mech.run();

        SmartDashboard.putNumber("Arm Setpoint", OperatorInput.getTestOffset());

    }

    @Override
    public void disable() {
        this.mech.disable();
    }
}
