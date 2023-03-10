package frc.peripherals.user;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import frc.peripherals.user.Controller.Axis;
import frc.peripherals.user.Controller.Side;
import frc.robot.Constants.Misc;

public final class OperatorInput {

    private static Controller operator = new Controller(Misc.OPERATOR_PORT);

    /**
     * Get the operator controller
     * 
     * @return Operator Controller
     */
    public static Controller getController() {
        return operator;
    }

    /**
     * Get the wrist offset in degrees 0° --> 45°
     * 
     * @return Wrist offset
     */
    public static double getWristOffset() {
        return operator.getJoystick(Side.RIGHT, Axis.Y) * 45;
    }

    /**
     * Get the wrist height in inches 0" --> 57.75"
     * 
     * @return Wrist height
     */
    public static double getWristHeight() {
        return Math.abs(operator.getJoystick(Side.LEFT, Axis.Y) * 57.75);
    }

    /**
     * Get the shoulder offset in inches 0" --> 12"
     * 
     * @return shoulder offset
     */
    public static double getShoulderOffset() {
        return operator.getJoystick(Side.LEFT, Axis.Y) * 12;
    }

    /**
     * Get the request to manually override a preset position
     * 
     * @return manual offset request
     */
    public static boolean manualOffsetRequest() {
        return (getShoulderOffset() != 0 || getWristOffset() != 0);
    }

    /**
     * Get the cone toggle button boolean state
     * 
     * @return cone toggle
     */
    public static boolean getConeToggle() {
        return operator.getYButton();
    }

    /**
     * Get the cube toggle button boolean state
     * 
     * @return cube toggle
     */
    public static boolean getCubeToggle() {
        return operator.getXButton();
    }

    /**
     * Get the cone release suction button boolean state
     * 
     * @return cone release suction
     */
    public static boolean getConeReleaseSuction() {
        return operator.getYButtonReleased();
    }

    /**
     * Get the cube release suction button boolean state
     * 
     * @return cube release suction
     */
    public static boolean getCubeReleaseSuction() {
        return operator.getXButtonReleased();
    }

    /**
     * Get the claw release button boolean state
     * 
     * @return claw release
     */
    public static boolean getClawRelease() {
        return operator.getAButton();
    }

    /**
     * Get the release suction button boolean state
     * 
     * @return suction release
     */
    public static boolean getReleaseSuction() {
        return operator.getRightBumper();
    }

    /**
     * Get the toggle suction button boolean state
     * 
     * @return suction toggle
     */
    // public static boolean getToggleSuction() {
    // return operator.getBButton();
    // }

    /**
     * Get the Reset button boolean state
     * 
     * @return reset state
     */
    public static boolean getResetButton() {
        return operator.getLeftStickButton();
    }

    /**
     * Get the Operator D-Pad input degrees 0°(N) --> 315°(NW)
     * 
     * @return Operator controller D-Pad input
     */
    public static int getGamePiece() {
        return operator.getPOV();
    }

    /**
     * Get the Operator station request
     * 
     * @return Operator station request
     */
    public static boolean getStationRequest() {
        return operator.getRightStickButton();
    }

    /**
     * Get the cube light button boolean state
     * 
     * @return cube request
     */
    public static boolean getCubeLight() {
        return operator.getLeftBumperPressed();
    }

    /**
     * Get the cone light button boolean state
     * 
     * @return cone request
     */
    public static boolean getConeLight() {
        return operator.getRightBumperPressed();
    }

    /**
     * Get the light release button boolean state
     * 
     * @return light release request
     */
    public static boolean getLightRelease() {
        return operator.getLeftBumperReleased() || operator.getRightBumperReleased();
    }

    /**
     * Set operator controller rumble mode
     * 
     * @param type      Left, Right, Both
     * @param intensity 0 --> 1
     */
    public static void setRumble(RumbleType type, double intensity) {
        operator.setRumble(type, intensity);
    }

}
