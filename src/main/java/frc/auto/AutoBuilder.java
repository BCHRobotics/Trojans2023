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
            rows.add((double) timer);
            rows.add((double) drive.getLeftPosition());
            rows.add((double) drive.getRightPosition());
            rows.add((double) 0.0); // Manual Tags (Arm Preset)
            rows.add((double) 0.0); // Manual Tags (Shoulder Offset)
            rows.add((double) 0.0); // Manual Tags (Wrist Offset)
            rows.add((double) 0.0); // Manual Tags (Claw Percentage)
            rows.add((double) 0.0); // Manual Tags (Pump mode)
            rows.add((double) 0.0); // Manual Tags (LED Status)
            rows.add((double) 0.0); // Manual Tags (Vision)
            rows.add((double) 0.0); // Manual Tags (Balancing)
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
                    "time,leftDrive,rightDrive,armPresetID,shoulderOffset,wristOffset,claw,pump,led,limelight,balance");
            writer.importData(data);
            writer.output();
        } catch (Exception e) {
            System.err.println(e);
            return;
        }
    }
}
