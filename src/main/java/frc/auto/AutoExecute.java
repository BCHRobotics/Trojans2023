package frc.auto;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import frc.subsystems.Drivetrain;
import frc.subsystems.Mechanism;
import frc.util.csv.CSVReader;

public class AutoExecute extends AutoComponent {
    private static AutoExecute instance;
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
    public static AutoExecute getInstance() {
        if (instance == null) {
            instance = new AutoExecute();
        }
        return instance;
    }

    private AutoExecute() {
        this.drive = Drivetrain.getInstance();
        this.mech = Mechanism.getInstance();
    }

    @Override
    public void firstCycle() {
        data.clear();
        this.drive.init();
        this.mech.init();
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
        currentTime = System.currentTimeMillis() - startTime;

        try {
            if (data.size() <= 0) {
                this.drive.resetEncoderPosition();
                this.mech.resetPosition();
                this.disable();
                data.clear();
                return;
            }
            if (currentTime < (data.get(0).get(0).longValue() * 1)) {
                this.drive.setPosition((data.get(0).get(1)), (data.get(0).get(2)));
                this.mech.goToPreset(data.get(0).get(3).intValue());
                this.mech.setShoulderOffset(data.get(0).get(4));
                this.mech.setWristOffset(data.get(0).get(5));
                this.mech.setClawSpeed(data.get(0).get(6));
                this.mech.setLEDState(data.get(0).get(8).intValue());
                switch (data.get(0).get(9).intValue()) {
                    case 1:
                        this.drive.calculatePath();
                        break;
                    case 2:
                        this.drive.followPath();
                        break;
                    default:
                        this.drive.clearPath();
                        break;
                }
                if (data.get(0).get(10) == 1.0) {
                    this.drive.balancePID();
                } else
                    this.drive.unrestrained();
            } else {
                data.remove(0);
            }
        } catch (Exception e) {
            System.err.println("Autonomous Drive Mode Failed!");
            e.printStackTrace();
            return;
        }

        this.drive.run();
        this.mech.run();
    }

    @Override
    public void disable() {
        this.drive.disable();
        this.mech.disable();
    }

}
