package frc.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.subsystems.Mechanism;
import frc.io.OperatorInput;

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

        SmartDashboard.putNumber("Max Drive Speed %", 100);

        this.mech.setClawPos(OperatorInput.getWristOffset());

        this.mech.run();

    }

    @Override
    public void disable() {
        this.mech.disable();
    }
}
