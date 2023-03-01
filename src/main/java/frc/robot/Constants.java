package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
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
                public static final boolean ARM_ENABLED = true;
                public static final boolean CLAW_ENABLED = true;
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
                public static final double TRACK_WIDTH = 19;

                // Chasis conversion factors TODO: Collect conversion data
                public static final double LEFT_POSITION_CONVERSION = (70 / 39.37)  / 33.35684585571289; // inches ( edited to metres ) per
                                                                                              // revolutions
                public static final double RIGHT_POSITION_CONVERSION = (70 / 39.37) / 33.904457092285156; // #inches / #revs

                public static final double LEFT_VELOCITY_CONVERSION = LEFT_POSITION_CONVERSION / 60.0; // inches ( edited to metres) per
                                                                                                       // second
                public static final double RIGHT_VELOCITY_CONVERSION = RIGHT_POSITION_CONVERSION / 60.0; // #inches /
                                                                                                         // 1 sec

                // input diameter = Δd inches between center wheels ~~v~~ in inches / degree
                public static final double TURNING_CONVERSION = (TRACK_WIDTH * Math.PI) / 360.0;

                // Drive PID Constants
                public static final SparkMaxConstants LEFT_DRIVE_CONSTANTS = new SparkMaxConstants(
                                0.00005, 0, 0, 0, 0.000185, -1, 1, 0, 0, 2000, 1500, 0);
                public static final SparkMaxConstants RIGHT_DRIVE_CONSTANTS = new SparkMaxConstants(
                                0.00005, 0, 0, 0, 0.000185, -1, 1, 0, 0, 2000, 1500, 0);

                // Gyro constants
                public static final SerialPort.Port GYRO_PORT = SerialPort.Port.kMXP;
                public static final boolean GYRO_OUTPUT_INVERTED = true;

                // Gyro PID Constants
                public static final SmartConstants GYRO_CONSTANTS = new SmartConstants(0, 0, 0, 0, 0.005, 0.001, 0);

                // Target seek PID Constants
                public static final SmartConstants SEEK_CONSTANTS = new SmartConstants(0, 0, 0, 0, 0, 0, 0);


                // Test values

                // These are example values only - DO NOT USE THESE FOR YOUR OWN ROBOT! @Stevon2386 ( I am going to use them anyways )
                // These characterization values MUST be determined either experimentally or theoretically
                // for *your* robot's drive.
                // The Robot Characterization Toolsuite provides a convenient tool for obtaining these
                // values for your robot.
                public static final double ksVolts = 0.22;
                public static final double kvVoltSecondsPerMeter = 1.98;
                public static final double kaVoltSecondsSquaredPerMeter = 0.2;

                // Example value only - as above, this must be tuned for your drive!
                public static final double kPDriveVel = 8.5;

                public static final double kTrackwidthMeters = 0.1524;
                public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);
                
                public static final double kMaxSpeedMetersPerSecond = 3;
                public static final double kMaxAccelerationMetersPerSecondSquared = 1;

                // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
                public static final double kRamseteB = 2;
                public static final double kRamseteZeta = 0.7;

        }

        public static final class Arm {
                // CAN ID(s) for Robot Arm
                public static final int SHOULDER_ID = 20;
                public static final int WRIST_ID = 21;

                // Robot dimensions (inches) TODO: Correct Dimensions
                public static final double SHOULDER_HEIGHT = 34.75;
                public static final double ARM_LENGTH = 36;
                public static final double FOREARM_LENGTH = 15;

                // Robot arm conversion factors
                public static final double SHOULDER_CONVERSION_FACTOR = 360; // Convert revs to degrees
                public static final double WRIST_CONVERSION_FACTOR = 360; // Convert revs to degrees
                public static final double WRIST_PARALLEL_OFFSET = 90;
                public static final double SHOULDER_DEFAULT_OFFSET = 17;
                public static final double WRIST_DEFAULT_OFFSET = 10;
                public static final float SHOULDER_LIMIT = 130 + (float) Arm.SHOULDER_DEFAULT_OFFSET;
                public static final float WRIST_LIMIT = 200 + (float) Arm.WRIST_DEFAULT_OFFSET;
                public static final double SHOUDLER_MAX_EXTENSION_LIMIT = 55;

                // Robot arm ABSOLUTE encoder inversions TODO: Correct encoder inversions
                public static final boolean SHOULDER_ENCODER_INVERTED = false;
                public static final boolean WRIST_ENCODER_INVERTED = false;

                // Robot arm ABSOLUTE encoder offset TODO: Correct encoder offsets
                public static final double SHOULDER_ENCODER_OFFSET = (204.7660160) - Arm.SHOULDER_DEFAULT_OFFSET;
                public static final double WRIST_ENCODER_OFFSET = (172.0870650) - Arm.WRIST_DEFAULT_OFFSET;

                // Mechanism PID Constants
                public static final SparkMaxConstants SHOULDER_CONTROL_CONSTANTS = new SparkMaxConstants(
                                0.00014028, 0, 0.00051398, 0, 2e-6, -0.4, 1, 0, 0, 5700, 3500, 0.1);
                public static final SparkMaxConstants WRIST_CONTROL_CONSTANTS = new SparkMaxConstants(
                                2.1028E-05, 0, 5.1398E-05, 0, 0.00004, -1, 1, 0, 0, 5700, 5700, 0.1);

        }

        public static final class Claw {
                // CAN ID(s) for End Effector
                public static final int MOTOR_ID = 30;
                public static final int LEFT_PUMP_ID = 31;
                public static final int MID_PUMP_ID = 32;
                public static final int RIGHT_PUMP_ID = 33;

                public static final int LEFT_BLEED_VALVE = 0;
                public static final int RIGHT_BLEED_VALVE = 1;

                public static final double DEFAULT_OFFSET = 0;
                public static final float LIMIT = 1;
                public static final int LIMIT_SWITCH_PORT = 2;

                public static final double CONVERSION_FACTOR = (1.0 / 50.0); // #inches / #revs

                public static final SparkMaxConstants CONSTANTS = new SparkMaxConstants(
                                0, 0, 0, 0, 0.0028, -1, 1, 0, 0, 5700, 4500, 0.01);
        }

        public static final class Misc {
                // Controller deadzones
                public static final double CONTROLLER_DEADZONE = 0.1;
                public static final int DRIVER_PORT = 0;
                public static final int OPERATOR_PORT = 1;

                // Game piece actuator presets in degrees
                public static final double CUBE_PRESET = 0.7;
                public static final double CONE_PRESET = 1;

                public static final int CUBE_LED_PORT = 1;
                public static final int CONE_LED_PORT = 0;

                public static final int BLINK_INTERVAL = 500; // milliseconds

                public static enum StatusLED {
                        CONE,
                        CUBE,
                        CONE_BLINK,
                        CUBE_BLINK,
                        OFF
                }

                // Arm preset profiles
                public static final ArmPresets STOWED_AWAY = new ArmPresets(0, 30);
                public static final ArmPresets STATION_PICKUP = new ArmPresets(0, 0);
                public static final ArmPresets GROUND_DROPOFF = new ArmPresets(0, 100);
                public static final ArmPresets MID_DROPOFF = new ArmPresets(95, 170);
                public static final ArmPresets TOP_DROPOFF = new ArmPresets(116, 195);

                // Limelight vision constants
                public static final double LIMELIGHT_ANGLE = 21.5; // degrees
                public static final double LIMELIGHT_HEIGHT = 91.25; // inches
                public static final double LIMELIGHT_TOLERANCE = 0.5; // degrees (x axis)
                public static final double TARGET_HEIGHT = 4.75; // inches
                public static final double APRILTAG_HEIGHT = 18.125; // inches

                // CSV Test version
                public static final int VERSION = 1;
                public static final String TEACH_MODE_FILE_NAME = "LIVE_RECORD" + "_" + VERSION;

                // Autonomous directory
                public static final String ROOT_DIRECTORY = "/home/lvuser/deploy/"; // "csv/";

                public static final double ENSURE_RANGE(double value, double min, double max) {
                        return Math.min(Math.max(value, min), max);
                }

                public static final boolean IN_RANGE(double value, double min, double max) {
                        return (value >= min) && (value <= max);
                }

                public static final boolean WITHIN_TOLERANCE(double value, double tolerance) {
                        return (value >= -tolerance) && (value <= tolerance);
                }

                public static final boolean WITHIN_TOLERANCE(double setpoint, double target, double tolerance) {
                        return (setpoint >= (target - tolerance)) && (setpoint <= (target + tolerance));
                }
        }

}