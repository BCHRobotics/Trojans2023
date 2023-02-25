package frc.auto;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import frc.subsystems.Drivetrain;
import frc.subsystems.Mechanism;
import frc.util.csv.CSVReader;

public class AutoOperate extends AutoComponent {
    private static AutoOperate instance;
    private static List<List<Double>> data = new ArrayList<>();
    private static long startTime;
    private static long currentTime;
    private Drivetrain drive;
    private Mechanism mech;

    /**
     * Get the instance of the AutoOperator, if none create a new instance
     * 
     * @return instance of the AutoOperator
     */
    public static AutoOperate getInstance() {
        if (instance == null) {
            instance = new AutoOperate();
        }
        return instance;
    }

    private AutoOperate() {
        this.drive = Drivetrain.getInstance();
        this.mech = Mechanism.getInstance();
    }

    @Override
    public void firstCycle() {
        data.clear();
        this.drive.firstCycle();
        this.mech.firstCycle();
        startTime = System.currentTimeMillis();
        try {
            data = CSVReader.convertToArrayList(AutoSelecter.getInstance().getFileName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void run() {
        driveMode();
        this.drive.run();
        this.mech.run();
    }

    /**
     * Shooter mode for autonomous
     */
    private void driveMode() {
        currentTime = System.currentTimeMillis() - startTime;

        try {
            if (data.size() <= 0) {
                this.drive.resetEncoderPosition();
                this.mech.resetPosition();
                this.disable();
                data.clear();
                return;
            }
            if (currentTime < data.get(0).get(0).longValue() * 1) { // t = 0.35
                this.drive.setPosition((data.get(0).get(1)), (data.get(0).get(2)));
                this.mech.setShoulderAngle(data.get(0).get(3));
                this.mech.setWristAngle(data.get(0).get(4));
                this.mech.setClawPos(data.get(0).get(5));
                this.mech.setSuctionMode(data.get(0).get(6) == 1.0 ? true : false);
                // this.drive.seekTarget(data.get(0).get(7));
                this.drive.balancePID(data.get(0).get(8) == 1.0 ? true : false);
            } else {
                data.remove(0);
            }
        } catch (Exception e) {
            System.err.println("Autonomous Drive Mode Failed!");
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void disable() {
        this.drive.disable();
        this.mech.disable();
    }

}