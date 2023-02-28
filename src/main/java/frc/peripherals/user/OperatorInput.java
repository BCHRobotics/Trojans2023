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
     * Get the wrist offset in degrees 0° --> 90°
     * 
     * @return Wrist offset
     */
    public static double getWristOffset() {
        return operator.getJoystick(Side.RIGHT, Axis.Y) * 180;
    }

    /**
     * Get the wrist height in inches 0" --> 60"
     * 
     * @return Wrist height
     */
    public static double getWristHeight() {
        return Math.abs(operator.getJoystick(Side.LEFT, Axis.Y) * 60);
    }

    /**
     * Get the test offset in degrees 0° --> 90°
     * 
     * @return test offset
     */
    public static double getTestOffset() {
        return Math.abs(operator.getJoystick(Side.LEFT, Axis.Y) * 90);
    }

    /**
     * Get the test button boolean state
     * 
     * @return test offset
     */
    public static boolean getTestButton() {
        return operator.getYButton();
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
     * Set operator controller rumble mode
     * 
     * @param type      Left, Right, Both
     * @param intensity 0 --> 1
     */
    public static void setRumble(RumbleType type, double intensity) {
        operator.setRumble(type, intensity);
    }

}
