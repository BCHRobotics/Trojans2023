package frc.util.imaging;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.peripherals.robot.DriveIO;
import frc.robot.Constants.Chassis;
public class AdvancedTarget {
    private static AdvancedTarget instance;

    private DriveIO m_robotDrive = DriveIO.getInstance();

    public static AdvancedTarget getInstance() {
        if (instance == null) {
            instance = new AdvancedTarget();
        }
        return instance;
    }
    
    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // Create a voltage constraint to ensure we don't accelerate too fast
        var autoVoltageConstraint =
            new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(
                    Chassis.ksVolts,
                    Chassis.kvVoltSecondsPerMeter,
                    Chassis.kaVoltSecondsSquaredPerMeter),
                Chassis.kDriveKinematics,
                10);

        // Create config for trajectory
        TrajectoryConfig config =
            new TrajectoryConfig(
                    Chassis.kMaxSpeedMetersPerSecond,
                    Chassis.kMaxAccelerationMetersPerSecondSquared)
                // Add kinematics to ensure max speed is actually obeyed
                .setKinematics(Chassis.kDriveKinematics)
                // Apply the voltage constraint
                .addConstraint(autoVoltageConstraint);

        // An example trajectory to follow.  All units in meters.
        Trajectory exampleTrajectory =
            TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                new Pose2d(0, 0, new Rotation2d(0)),
                // Pass through these two interior waypoints, making an 's' curve path
                List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
                // End 3 meters straight ahead of where we started, facing forward
                new Pose2d(3, 0, new Rotation2d(0)),
                // Pass config
                config);

        RamseteCommand ramseteCommand =
        new RamseteCommand(
            exampleTrajectory,
            m_robotDrive::getPose,
            new RamseteController(Chassis.kRamseteB, Chassis.kRamseteZeta),
            new SimpleMotorFeedforward(
                Chassis.ksVolts,
                Chassis.kvVoltSecondsPerMeter,
                Chassis.kaVoltSecondsSquaredPerMeter),
            Chassis.kDriveKinematics,
            m_robotDrive::getWheelSpeeds,
            new PIDController(Chassis.kPDriveVel, 0, 0),
            new PIDController(Chassis.kPDriveVel, 0, 0),
            // RamseteCommand passes volts to the callback
            m_robotDrive::tankDriveVolts, m_robotDrive);

        // Reset odometry to the starting pose of the trajectory.
        m_robotDrive.resetOdometry(exampleTrajectory.getInitialPose());

        // Run path following command, then stop at the end.
        return ramseteCommand.andThen(() -> m_robotDrive.tankDriveVolts(0, 0));
    }
}
