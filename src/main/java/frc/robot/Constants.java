package frc.robot;

import edu.wpi.first.wpilibj.SerialPort;
import frc.util.control.ArmPresets;
import frc.util.control.FeedForwardConstants;
import frc.util.control.PIDConstants;
import frc.util.control.SparkMaxConstants;

public class Constants {

        // All units used are: Degrees, Revolutions, Inches, RPM, inches / sec

        // System time loop constants
        public static final double LOOP_TIME = 0.02; // seconds

        // Subsytems toggle logic
        public static final boolean USING_DASHBOARD = true;
        public static final boolean DRIVE_ENABLED = true;
        public static final boolean ARM_ENABLED = false;
        public static final boolean CLAW_ENABLED = false;
        public static final boolean GYRO_ENABLED = false;
        public static final boolean MINI_BOT = false;

        // Controller deadzones
        public static final double CONTROLLER_DEADZONE = 0.1;
        public static final int DRIVER_PORT = 0;
        public static final int OPERATOR_PORT = 1;

        // Gyro constants
        public static final SerialPort.Port GYRO_PORT = SerialPort.Port.kUSB;
        public static final boolean GYRO_OUTPUT_INVERTED = true;

        // Drive motors inverted
        public static final boolean DRIVE_INVERTED = true;
        public static final boolean DRIVE_OUT_OF_SYNC = false;

        // Drive restrictions
        public static final double MAX_OUTPUT = 0.6;
        public static final double MAX_INTERVAL = 0.25;

        // Robot dimensions (inches) TODO: Correct Dimensions
        public static final double WHEEL_DIAMETER = 6;
        public static final double SHOULDER_HEIGHT = 40;
        public static final double ARM_LENGTH = 30;
        public static final double WRIST_LENGTH = 12;

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

        // TODO: Correct conversion factors

        // Chasis conversion factors
        public static final double CHASIS_LEFT_POS_CONVERSION = 70.5 / 33.35684585571289; // inches per revolutions
        public static final double CHASIS_RIGHT_POS_CONVERSION = 70.5 / 33.904457092285156; // #inches / #revs

        public static final double CHASIS_LEFT_VEL_CONVERSION = CHASIS_LEFT_POS_CONVERSION / 60; // inches per second
        public static final double CHASIS_RIGHT_VEL_CONVERSION = CHASIS_RIGHT_POS_CONVERSION / 60; // #inches / 1 sec

        public static final double CHASIS_MAX_VEL = 144; // in/s
        public static final double CHASIS_MAX_ACCEL = 288; // in/s^2

        // input diameter = Δd inches between center wheels ~~v~~
        public static final double CHASIS_TURN_CONVERSION = ((16) * (Math.PI / 4)) / 90; // Arc length through 90° turn,

        // Robot arm conversion factors
        public static final double SHOULDER_CONVERSION_FACTOR = 360; // Convert revs to degrees
        public static final double WRIST_CONVERSION_FACTOR = 360; // Convert revs to degrees
        public static final double CLAW_CONVERSION_FACTOR = 1 / 1; // #inches / #revs

        // Robot arm ABSOLUTE encoder inversions TODO: Correct encoder inversions
        public static final boolean SHOULDER_ENCODER_INVERTED = false;
        public static final boolean WRIST_ENCODER_INVERTED = false;

        // Robot arm ABSOLUTE encoder offset TODO: Correct encoder offsets
        public static final double SHOULDER_ENCODER_OFFSET = 0;
        public static final double WRIST_ENCODER_OFFSET = 0;

        // Position Balancing Limits
        public static final double BALANCE_LIMIT = 12;

        // Game piece actuator presets in inches
        public static final double CUBE_PRESET = 7;
        public static final double CONE_PRESET = 1;

        // Arm preset profiles
        public static final ArmPresets GROUND_PICKUP = new ArmPresets(0, 0);
        public static final ArmPresets STATION_PICKUP = new ArmPresets(0, 0);
        public static final ArmPresets GROUND_DROPOFF = new ArmPresets(0, 0);
        public static final ArmPresets MID_DROPOFF = new ArmPresets(0, 0);
        public static final ArmPresets TOP_DROPOFF = new ArmPresets(0, 0);

        // CSV Test version
        public static final int VERSION = 1;
        public static final String TEACH_MODE_FILE_NAME = "LIVE_RECORD" + "_" + VERSION;

        // Autonomous directory
        public static final String ROOT_DIRECTORY = "csv/";

        // Drive PID Constants
        public static final SparkMaxConstants DRIVEL1_CONSTANTS = new SparkMaxConstants(0.00005, 0, 0, 0, 0.000185, -1,
                        1);
        public static final SparkMaxConstants DRIVER1_CONSTANTS = new SparkMaxConstants(0.00005, 0, 0, 0, 0.000185, -1,
                        1);

        // Mechanism PID Constants
        public static final FeedForwardConstants SHOULDER_FF_CONSTANTS = new FeedForwardConstants(0, 0, 0, 0);
        public static final FeedForwardConstants WRIST_FF_CONSTANTS = new FeedForwardConstants(0, 0, 0, 0);
        public static final PIDConstants SHOULDER_PID_CONSTANTS = new PIDConstants(0, 0, 0);
        public static final PIDConstants WRIST_PID_CONSTANTS = new PIDConstants(0, 0, 0);

        public static final SparkMaxConstants CLAW_CONSTANTS = new SparkMaxConstants(1e-4, 0, 0, 0, 0.000156, -1, 1);

        // Gyro PID Constants
        public static final PIDConstants GYRO_CONSTANTS = new PIDConstants(0.005, 0.001, 0.0);

        // Target seek PID Constants
        public static final PIDConstants SEEK_CONSTANTS = new PIDConstants(0.0, 0.0, 0.0);

}