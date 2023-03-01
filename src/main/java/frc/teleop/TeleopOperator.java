package frc.teleop;

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

        if (OperatorInput.getGamePiece() == 0) {
            this.mech.setClawPos(Misc.CONE_PRESET);
            this.mech.setStatusLED(StatusLED.CONE_BLINK);
        } else if (OperatorInput.getGamePiece() == 270) {
            this.mech.setClawPos(Misc.CUBE_PRESET);
            this.mech.setStatusLED(StatusLED.CUBE);
        } else if (OperatorInput.getGamePiece() == 180) {
            this.mech.setClawPos(0);
            this.mech.setStatusLED(StatusLED.OFF);
        }

        // this.mech.setWristOffset(OperatorInput.getWristOffset());
        // this.mech.setWristHeight(OperatorInput.getWristHeight());

        // if (OperatorInput.getTestButton()) {
        // this.mech.setStatusLED(StatusLED.CONE);
        // this.mech.setClawPos(0.7);
        // this.mech.setSuctionMode(false);
        // this.mech.setBleedMode(false);
        // } else {
        // this.mech.setStatusLED(StatusLED.CUBE);
        // this.mech.setClawPos(0);
        // this.mech.setSuctionMode(false);
        // }

        // if (OperatorInput.getController().getAButton())
        // this.mech.setBleedMode(false);

        this.mech.setShoulderAngle(OperatorInput.getTestOffset());
        this.mech.setWristAngle(OperatorInput.getWristOffset());

        this.mech.run();

    }

    @Override
    public void disable() {
        this.mech.disable();
    }
}
