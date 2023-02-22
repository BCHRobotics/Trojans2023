package frc.robot;

import edu.wpi.first.wpilibj.SerialPort;
import frc.util.control.*;

public class Constants {

        // All units used are: Degrees, Revolutions, Inches, RPM, inches / sec

        public static final class Features {
                // System time loop constants
                public static final double LOOP_TIME = 0.02; // seconds

                // Subsytems toggle logic
                public static final boolean USING_DASHBOARD = true;
                public static final boolean DRIVE_ENABLED = true;
                public static final boolean ARM_ENABLED = false;
                public static final boolean CLAW_ENABLED = false;
                public static final boolean GYRO_ENABLED = false;
                public static final boolean MINI_BOT = false;
        }

        public static final class Chassis {

                // CAN ID(s) for Drivetrain
                public static final int FRONT_LEFT_ID = 10;
                public static final int FRONT_RIGHT_ID = 11;
                public static final int BACK_LEFT_ID = 12;
                public static final int BACK_RIGHT_ID = 13;

                // Drive restrictions
                public static final double MAX_OUTPUT = 0.75;
                public static final double MAX_INTERVAL = 0.25;
                public static final double MAX_VEL = 144; // in/s
                public static final double MAX_ACCEL = 288; // in/s^2
                public static final boolean INVERTED = false;
                public static final boolean OUT_OF_SYNC = false;

                // Chassis dimensions needed
                public static final double WHEEL_DIAMETER = 6;
                public static final double TRACK_LENGTH = 16;

                // Chasis conversion factors TODO: Collect conversion data
                public static final double LEFT_POSITION_CONVERSION = 70.5 / 33.35684585571289; // inches per
                                                                                                // revolutions
                public static final double RIGHT_POSITION_CONVERSION = 70.5 / 33.904457092285156; // #inches / #revs

                public static final double LEFT_VELOCITY_CONVERSION = LEFT_POSITION_CONVERSION / 60; // inches per
                                                                                                     // second
                public static final double RIGHT_VELOCITY_CONVERSION = RIGHT_POSITION_CONVERSION / 60; // #inches /
                                                                                                       // 1 sec

                // input diameter = Δd inches between center wheels ~~v~~
                public static final double TURNING_CONVERSION = ((TRACK_LENGTH) * (Math.PI / 4)) / 90; // Arc length
                                                                                                       // through
                // 90° turn,
                // Drive PID Constants
                public static final SparkMaxConstants LEFT_DRIVE_CONSTANTS = new SparkMaxConstants(0.00005, 0, 0, 0,
                                0.000185, -1,
                                1);
                public static final SparkMaxConstants RIGHT_DRIVE_CONSTANTS = new SparkMaxConstants(0.00005, 0, 0, 0,
                                0.000185, -1,
                                1);

                // Gyro constants
                public static final SerialPort.Port GYRO_PORT = SerialPort.Port.kMXP;
                public static final boolean GYRO_OUTPUT_INVERTED = true;

                // Gyro PID Constants
                public static final SmartConstants GYRO_CONSTANTS = new SmartConstants(0, 0, 0, 0, 0.005, 0.001, 0);

                // Target seek PID Constants
                public static final SmartConstants SEEK_CONSTANTS = new SmartConstants(0, 0, 0, 0, 0, 0, 0);

        }

        public static final class Arm {
                // CAN ID(s) for Robot Arm
                public static final int SHOULDER_ID = 20;
                public static final int WRIST_ID = 21;

                // Robot dimensions (inches) TODO: Correct Dimensions
                public static final double SHOULDER_HEIGHT = 40;
                public static final double ARM_LENGTH = 30;
                public static final double WRIST_LENGTH = 12;

                // Robot arm conversion factors
                public static final double SHOULDER_CONVERSION_FACTOR = 360; // Convert revs to degrees
                public static final double WRIST_CONVERSION_FACTOR = 360; // Convert revs to degrees

                // Robot arm ABSOLUTE encoder inversions TODO: Correct encoder inversions
                public static final boolean SHOULDER_ENCODER_INVERTED = false;
                public static final boolean WRIST_ENCODER_INVERTED = false;

                // Robot arm ABSOLUTE encoder offset TODO: Correct encoder offsets
                public static final double SHOULDER_ENCODER_OFFSET = 0;
                public static final double WRIST_ENCODER_OFFSET = 0;

                // Mechanism PID Constants
                public static final SmartConstants SHOULDER_CONTROL_CONSTANTS = new SmartConstants(0, 0, 0, 0, 0, 0, 0);
                public static final SmartConstants WRIST_CONTROL_CONSTANTS = new SmartConstants(0, 0, 0, 0, 0, 0, 0);

        }

        public static final class Claw {
                // CAN ID(s) for End Effector
                public static final int CLAW_ID = 30;
                public static final int PUMP_ID = 31;

                public static final double CLAW_CONVERSION_FACTOR = 1 / 1; // #inches / #revs

                public static final SparkMaxConstants CLAW_CONSTANTS = new SparkMaxConstants(1e-4, 0, 0, 0, 0.000156,
                                -1, 1);
        }

        public static final class Misc {
                // Controller deadzones
                public static final double CONTROLLER_DEADZONE = 0.1;
                public static final int DRIVER_PORT = 0;
                public static final int OPERATOR_PORT = 1;

                // Game piece actuator presets in inches
                public static final double CUBE_PRESET = 7;
                public static final double CONE_PRESET = 1;

                // Arm preset profiles
                public static final ArmPresets GROUND_PICKUP = new ArmPresets(0, 0);
                public static final ArmPresets STATION_PICKUP = new ArmPresets(0, 0);
                public static final ArmPresets GROUND_DROPOFF = new ArmPresets(0, 0);
                public static final ArmPresets MID_DROPOFF = new ArmPresets(0, 0);
                public static final ArmPresets TOP_DROPOFF = new ArmPresets(0, 0);

                // Limelight vision constants
                public static final double LIMELIGHT_ANGLE = -20; // degrees
                public static final double LIMELIGHT_HEIGHT = 120; // inches TODO: Correct limelight height
                public static final double TARGET_HEIGHT = 4.75; // inches

                // CSV Test version
                public static final int VERSION = 1;
                public static final String TEACH_MODE_FILE_NAME = "LIVE_RECORD" + "_" + VERSION;

                // Autonomous directory
                public static final String ROOT_DIRECTORY = "csv/";
        }

}