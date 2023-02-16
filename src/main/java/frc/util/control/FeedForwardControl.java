package frc.util.control;

import edu.wpi.first.math.controller.ArmFeedforward;

public class FeedForwardControl extends ArmFeedforward {

    public FeedForwardControl(FeedForwardConstants c) {
        super(c.kS, c.kG, c.kV, c.kA);
    }

}
