package frc.io;

import frc.util.devices.Controller;
import frc.util.devices.Controller.Axis;
import frc.util.devices.Controller.Side;

public class OperatorInput {

    private static Controller operator = new Controller(1);

    /**
     * Get the operator controller
     * 
     * @return Operator Controller
     */
    public static Controller getController() {
        return operator;
    }

    /**
     * Get the wrist offset in degrees -90 --> 90
     * 
     * @return Wrist offset
     */
    public static double getWristOffset() {
        return operator.getJoystick(Side.RIGHT, Axis.Y) * 90;
    }

    /**
     * Get the Operator D-Pad input degrees 0 --> 360
     * 
     * @return Operator controller D-Pad input
     */
    public static int getGamePiece() {
        return operator.getPOV();
    }

}
