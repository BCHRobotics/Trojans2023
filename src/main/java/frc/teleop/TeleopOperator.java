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

        if (OperatorInput.getController().getYButton()) {
            this.mech.setClawPos(Misc.CONE_PRESET);
            this.mech.setStatusLED(StatusLED.CONE);
        } else if (OperatorInput.getController().getXButton()) {
            this.mech.setClawPos(Misc.CUBE_PRESET);
            this.mech.setStatusLED(StatusLED.CUBE);
        } else if (OperatorInput.getController().getAButton()) {
            this.mech.setClawPos(0);
            this.mech.setStatusLED(StatusLED.OFF);
        }

        if (OperatorInput.getController().getBButton())
            this.mech.setSuctionMode(true);
        else if (OperatorInput.getTestButton())
            this.mech.setSuctionMode(false);

        switch (OperatorInput.getGamePiece()) {
            case 0:
                this.mech.goToPreset(Misc.TOP_DROPOFF);
                break;
            case 90:
                this.mech.goToPreset(Misc.STOWED_AWAY);
                break;
            case 180:
                this.mech.goToPreset(Misc.GROUND_DROPOFF);
                break;
            case 270:
                this.mech.goToPreset(Misc.MID_DROPOFF);
                break;
        }

        if (OperatorInput.getResetButton()) {
            this.mech.resetPosition();
        }

        // this.mech.setWristHeight(OperatorInput.getWristHeight());
        // this.mech.setWristOffset(OperatorInput.getWristOffset());

        this.mech.run();

    }

    @Override
    public void disable() {
        this.mech.disable();
    }
}
