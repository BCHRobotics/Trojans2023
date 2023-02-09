package frc.robot;

import frc.util.control.SparkMaxConstants;
import frc.util.control.Terms;

import frc.util.control.ArmPresets;

public class Constants {
        // Subsytems toggle logic
        public static final boolean USING_DASHBOARD = true;
        public static final boolean DRIVE_ENABLED = true;
        public static final boolean ARM_ENABLED = false;
        public static final boolean CLAW_ENABLED = false;
        public static final boolean GYRO_ENABLED = false;
        public static final boolean MINI_BOT = false;

        // Controller deadzones
        public static final double CONTROLLER_DEADZONE = 0.1;

        // Drive motors inverted
        public static final boolean DRIVE_INVERTED = false;
        public static final boolean DRIVE_OUT_OF_SYNC = false;

        // Drive restrictions
        public static final double MAX_OUTPUT = 0.6;
        public static final double MAX_INTERVAL = 0.25;

        // Robot dimensions (inches)
        public static final double WHEEL_DIAMETER = 6;
        public static final double SHOULDER_HEIGHT = 40;
        public static final double ARM_LENGTH = 30;

        // CAN ID(s) for Drivetrain
        public static final int DRIVE_LEFT1_ID = 10;
        public static final int DRIVE_RIGHT1_ID = 11;
        public static final int DRIVE_LEFT2_ID = 12;
        public static final int DRIVE_RIGHT2_ID = 13;

        // CAN ID(s) for Robot Arm
        public static final int SHOULDER_ID = 20;
        public static final int WRIST_ID = 21;

        // CAN ID(s) for End Effector
        public static final int CLAW_ID = 30;
        public static final int PUMP_ID = 31;

        // Chasis conversion factors
        public static final double CHASIS_LEFT_CONVERSION = 1;
        public static final double CHASIS_RIGHT_CONVERSION = 1;

        // Robot arm conversion factors
        public static final double SHOULDER_CONVERSION_FACTOR = (11 / 1296) * 360;
        public static final double WRIST_CONVERSION_FACTOR = (1 / 100) * 360;
        public static final double CLAW_CONVERSION_FACTOR = 0.165 / 4;

        // Position Balancing Limits
        public static final double BALANCE_LIMIT = 12;

        // Game piece actuator presets in inches
        public static final double CUBE_PRESET = 1;
        public static final double CONE_PRESET = 3;

        // Arm preset profiles
        public static final ArmPresets GROUND_PICKUP = new ArmPresets(0, 0);
        public static final ArmPresets STATION_PICKUP = new ArmPresets(0, 0);
        public static final ArmPresets GROUND_DROPOFF = new ArmPresets(0, 0);
        public static final ArmPresets MID_DROPOFF = new ArmPresets(0, 0);
        public static final ArmPresets TOP_DROPOFF = new ArmPresets(0, 0);

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

        // Drive PID Constants
        public static final SparkMaxConstants SHOULDER_CONSTANTS = new SparkMaxConstants(1e-4, 0, 0, 0, 0.000156, -1, 1,
                        0,
                        0, 6000, 3500, 0.1);
        public static final SparkMaxConstants WRIST_CONSTANTS = new SparkMaxConstants(1e-4, 0, 0, 0, 0.000156, -1, 1,
                        0,
                        0, 6000, 3500, 0.1);
        public static final SparkMaxConstants CLAW_CONSTANTS = new SparkMaxConstants(1e-4, 0, 0, 0, 0.000156, -1, 1,
                        0,
                        0, 6000, 3500, 0.1);

        // Gyro PID Constants
        public static final Terms GYRO_CONSTANTS = new Terms(0.005, 0.001, 0, 0);

        // Target seek PID Constants
        public static final Terms SEEK_CONSTANTS = new Terms(0.0, 0.0, 0, 0);

}