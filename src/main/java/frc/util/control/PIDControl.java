package frc.util.control;

import edu.wpi.first.math.controller.PIDController;

public class PIDControl extends PIDController {

    public PIDControl(PIDConstants c) {
        super(c.kP, c.kI, c.kD);
    }

    public void setPID(PIDConstants c) {
        super.setPID(c.kP, c.kI, c.kD);
    }

}
