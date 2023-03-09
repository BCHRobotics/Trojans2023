package frc.teleop;

import frc.subsystems.Mechanism;
import frc.peripherals.user.OperatorInput;
import frc.robot.Constants.Arm;
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

        if (OperatorInput.getConeToggle()) {
            this.mech.setStatusLED(StatusLED.CONE);
            this.mech.setClawPos(Misc.CONE_PRESET);
            this.mech.setSuctionMode(true);
        } else if (OperatorInput.getConeReleaseSuction()) {
            this.mech.setSuctionMode(false);
        } else if (OperatorInput.getCubeToggle()) {
            this.mech.setStatusLED(StatusLED.CUBE);
            this.mech.setClawPos(Misc.CUBE_PRESET);
            this.mech.setSuctionMode(true);
        } else if (OperatorInput.getCubeReleaseSuction()) {
            this.mech.setSuctionMode(false);
        } else if (OperatorInput.getClawRelease()) {
            this.mech.setClawPos(0);
            this.mech.setStatusLED(StatusLED.OFF);
        }

        // if (OperatorInput.getToggleSuction())
        // this.mech.setSuctionMode(true);
        // else
        if (OperatorInput.getReleaseSuction())
            this.mech.setSuctionMode(false);

        switch (OperatorInput.getGamePiece()) {
            case 0:
                this.mech.goToPreset(Arm.TOP_DROPOFF);
                break;
            case 90:
                this.mech.goToPreset(Arm.STOWED_AWAY);
                break;
            case 180:
                this.mech.goToPreset(Arm.GROUND_DROPOFF);
                break;
            case 270:
                this.mech.goToPreset(Arm.MID_DROPOFF);
                break;
        }

        if (OperatorInput.getStationRequest())
            this.mech.goToPreset(Arm.STATION_PICKUP);

        if (OperatorInput.getResetButton())
            this.mech.resetPosition();

        if (OperatorInput.getConeLight())
            this.mech.setStatusLED(StatusLED.CONE_BLINK);
        else if (OperatorInput.getCubeLight())
            this.mech.setStatusLED(StatusLED.CUBE_BLINK);
        else if (OperatorInput.getLightRelease())
            this.mech.setStatusLED(StatusLED.OFF);

        // this.mech.setWristHeight(OperatorInput.getWristHeight());
        // this.mech.setWristOffset(OperatorInput.getWristOffset());

        this.mech.run();

    }

    @Override
    public void disable() {
        this.mech.disable();
    }
}
