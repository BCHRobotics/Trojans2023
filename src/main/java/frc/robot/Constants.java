package frc.robot;

import frc.util.pid.SparkMaxConstants;
import frc.util.pid.Terms;

public class Constants {
        public static final boolean USING_DASHBOARD = true;
        public static final double CONTROLLER_DEADZONE = 0.1;
        public static final double WHEEL_DIAMETER = 6;
        public static final double MAX_OUTPUT = 0.75;
        public static final double MAX_INTERVAL = 0.25;
        public static final double PATH_TURN_P = 6;

        // CAN ID(s) for Drivetrain
        public static final int DRIVE_LEFT1_ID = 10;
        public static final int DRIVE_RIGHT1_ID = 11;
        public static final int DRIVE_LEFT2_ID = 12;
        public static final int DRIVE_RIGHT2_ID = 13;

        // Chasis conversion factors
        public static final double CHASIS_LEFT_CONVERSION = 0;
        public static final double CHASIS_RIGHT_CONVERSION = 0;

        // CSV Test version
        public static final int VERSION = 0;
        public static final String TEACH_MODE_FILE_NAME = "LIVE_RECORD";

        // Subsytems toggle logic
        public static final boolean DRIVE_ENABLED = true;
        public static final boolean MINI_BOT = false;

        // Drive PID Constants
        public static final SparkMaxConstants DRIVEL1_CONSTANTS = new SparkMaxConstants(1e-4, 0, 0, 0, 0.000156, -1, 1,
                        0,
                        0, 6000, 3500, 0.1);
        public static final SparkMaxConstants DRIVER1_CONSTANTS = new SparkMaxConstants(1e-4, 0, 0, 0, 0.000156, -1, 1,
                        0,
                        0, 6000, 3500, 0.1);
        public static final SparkMaxConstants DRIVEL2_CONSTANTS = new SparkMaxConstants(1e-4, 0, 0, 0, 0.000156, -1, 1,
                        0,
                        0, 6000, 3500, 0.1);
        public static final SparkMaxConstants DRIVER2_CONSTANTS = new SparkMaxConstants(1e-4, 0, 0, 0, 0.000156, -1, 1,
                        0,
                        0, 6000, 3500, 0.1);

        // Gyro PID Constants
        public static final Terms GYRO_CONSTANTS = new Terms(0.005, 0.001, 0, 0);

        // Auto directory
        public static final String ROOT_DIRECTORY = "csv/";
}