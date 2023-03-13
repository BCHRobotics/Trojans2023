package frc.auto;

import java.util.ArrayList;
import java.util.List;

import frc.robot.Constants.Misc;
import frc.subsystems.Drivetrain;
import frc.util.csv.CSVWriter;

public class AutoBuilder {
    private static AutoBuilder instance;
    private static CSVWriter writer;
    private static List<List<Double>> data = new ArrayList<>();

    private static long startTime;
    private static long currentTime;
    private static long timer;

    private static Drivetrain drive;

    public static AutoBuilder getInstance() {
        if (instance == null) {
            instance = new AutoBuilder();
        }
        return instance;
    }

    private AutoBuilder() {
        writer = new CSVWriter(Misc.ROOT_DIRECTORY);
        drive = Drivetrain.getInstance();
    }

    public void setStartRecording() {
        startTime = System.currentTimeMillis();
    }

    public void recordData() {
        try {
            currentTime = System.currentTimeMillis();
            timer = currentTime - startTime;

            List<Double> rows = new ArrayList<Double>();
            rows.add((double) timer); // Command end time, time to of which to kill previous command and begin new one
            rows.add((double) drive.getLeftPosition()); // Chassis left encoder position
            rows.add((double) drive.getRightPosition()); // Chassis right encoder position
            rows.add((double) 0.0); // Manual Tags (Arm Preset) {0,1,2,3,4,5}
            rows.add((double) 0.0); // Manual Tags (Shoulder Offset) {-12 --> 12 inches}
            rows.add((double) 0.0); // Manual Tags (Wrist Offset) {-20 --> 20 degrees}
            rows.add((double) 0.0); // Manual Tags (Claw Percentage) {0 --> 1 %open}
            rows.add((double) 0.0); // Manual Tags (Pump mode) {0,1}
            rows.add((double) 0.0); // Manual Tags (LED Status) {0,1,2}
            rows.add((double) 0.0); // Manual Tags (Vision) {0,1}
            rows.add((double) 0.0); // Manual Tags (Balancing) {0,1}
            data.add(rows);
        } catch (Exception e) {
            System.err.println(e);
            return;
        }

    }

    public void convertData() {
        try {
            writer.setFileName(Misc.TEACH_MODE_FILE_NAME);
            writer.setHeader(
                    "time,leftDrive,rightDrive,armPreset,shoulderOffset,wristOffset,claw,pump,led,limelight,balance");
            writer.importData(data);
            writer.output();
        } catch (Exception e) {
            System.err.println(e);
            return;
        }
    }
}
