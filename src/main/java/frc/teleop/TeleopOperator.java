package frc.teleop;

import frc.subsystems.Mechanism;
import frc.peripherals.user.OperatorInput;
import frc.robot.Constants.*;
import frc.robot.Constants.MISC.LED_STATE;

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
        this.mech.init();
    }

    @Override
    public void run() {

        // if (OperatorInput.getConeToggle()) {
        // this.mech.setLEDState(LED_STATE.CONE);
        // this.mech.setClawPos(MISC.CONE_PRESET);
        // this.mech.setSuctionMode(true);
        // } else if (OperatorInput.getConeReleaseSuction()) {
        // this.mech.setSuctionMode(false);
        // } else if (OperatorInput.getCubeToggle()) {
        // this.mech.setLEDState(LED_STATE.CUBE);
        // this.mech.setClawPos(MISC.CUBE_PRESET);
        // this.mech.setSuctionMode(true);
        // } else if (OperatorInput.getCubeReleaseSuction()) {
        // this.mech.setSuctionMode(false);
        // } else if (OperatorInput.getClawRelease()) {
        // this.mech.setClawPos(0);
        // this.mech.setLEDState(LED_STATE.OFF);
        // }

        if (OperatorInput.manualOffsetRequest()) {
            this.mech.setShoulderOffset(OperatorInput.getShoulderOffset());
            this.mech.setWristOffset(OperatorInput.getWristOffset());
        } else {
            switch (OperatorInput.getGamePiece()) {
                case 0:
                    this.mech.goToPreset(ROBOT.TOP);
                    break;
                case 90:
                    this.mech.goToPreset(ROBOT.TRANSPORT);
                    break;
                case 180:
                    this.mech.goToPreset(ROBOT.GROUND);
                    break;
                case 270:
                    this.mech.goToPreset(ROBOT.MID);
                    break;
            }
        }

        if (OperatorInput.getStationRequest())
            this.mech.goToPreset(ROBOT.STATION);

        if (OperatorInput.getResetButton())
            this.mech.resetPosition();

        if (OperatorInput.getConeLight())
            this.mech.setLEDState(LED_STATE.CONE_BLINK);
        else if (OperatorInput.getCubeLight())
            this.mech.setLEDState(LED_STATE.CUBE_BLINK);
        else if (OperatorInput.getLightRelease())
            this.mech.setLEDState(LED_STATE.OFF);

        this.mech.run();

    }

    @Override
    public void disable() {
        this.mech.disable();
    }
}
