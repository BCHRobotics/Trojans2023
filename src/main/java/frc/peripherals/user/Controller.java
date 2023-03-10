package frc.peripherals.user;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.Misc;

public final class Controller extends XboxController {

    protected enum Side {
        LEFT, RIGHT
    }

    protected enum Axis {
        X, Y
    }

    protected Controller(int port) {
        super(port);
    }

    protected boolean getModeSwitchButtonsPressed() {
        return this.getStartButtonPressed() || this.getBackButtonPressed();
    }

    protected double getJoystick(Side side, Axis axis) {
        double deadzone = Misc.CONTROLLER_DEADZONE;

        boolean left = side == Side.LEFT;
        boolean y = axis == Axis.Y;

        // multiplies by -1 if y-axis (inverted normally)
        return handleDeadzone((y ? -1 : 1) * this.getRawAxis((left ? 0 : 4) + (y ? 1 : 0)), deadzone);
    }

    private double handleDeadzone(double value, double deadzone) {
        return (Math.abs(value) > Math.abs(deadzone)) ? value : 0;
    }
}
