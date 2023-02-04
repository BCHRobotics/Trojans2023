package frc.robot;

import frc.util.control.SparkMaxConstants;
import frc.util.control.Terms;

public class Constants {
        // Subsytems toggle logic
        public static final boolean USING_DASHBOARD = true;
        public static final boolean DRIVE_ENABLED = true;
        public static final boolean INTAKE_ENABLED = true;
        public static final boolean MINI_BOT = false;

        // Controller deadzones
        public static final double CONTROLLER_DEADZONE = 0.1;

        // Drive restrictions
        public static final double MAX_OUTPUT = 0.75;
        public static final double MAX_INTERVAL = 0.25;

        // Robot dimensions
        public static final double WHEEL_DIAMETER = 6;

        // CAN ID(s) for Drivetrain
        public static final int DRIVE_LEFT1_ID = 10;
        public static final int DRIVE_RIGHT1_ID = 11;
        public static final int DRIVE_LEFT2_ID = 12;
        public static final int DRIVE_RIGHT2_ID = 13;

        // CAN ID(s) for Intake
        public static final int INTAKE_ID = 20;

        // Intake solenoid ports
        public static final int INTAKE_LOWERED = 0;
        public static final int INTAKE_RAISED = 1;

        // Chasis conversion factors
        public static final double CHASIS_LEFT_CONVERSION = 1;
        public static final double CHASIS_RIGHT_CONVERSION = 1;

        // Position Balancing Limits
        public static final double BALANCE_LIMIT = 12;

        // CSV Test version
        public static final int VERSION = 0;
        public static final String TEACH_MODE_FILE_NAME = "LIVE_RECORD";

        // Autonomous directory
        public static final String ROOT_DIRECTORY = "csv/";

        // Drive PID Constants
        public static final SparkMaxConstants DRIVEL1_CONSTANTS = new SparkMaxConstants(1e-4, 0, 0, 0, 0.000156, -1, 1,
                        0,
                        0, 6000, 3500, 0.1);
        public static final SparkMaxConstants DRIVER1_CONSTANTS = new SparkMaxConstants(1e-4, 0, 0, 0, 0.000156, -1, 1,
                        0,
                        0, 6000, 3500, 0.1);

        // Gyro PID Constants
        public static final Terms GYRO_CONSTANTS = new Terms(0.005, 0.001, 0, 0);

}