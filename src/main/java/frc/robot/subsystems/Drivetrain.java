// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {

  /**
   * The Drivetrain subsystem incorporates the sensors and actuators attached to
   * the robots chassis.
   * These include four drive motors, a left and right encoder and a gyro.
   */
  private final CANSparkMax frontLeftMotor = new CANSparkMax(Constants.Chassis.FRONT_LEFT_ID,
      Constants.Chassis.MOTOR_TYPE);
  private final CANSparkMax frontRightMotor = new CANSparkMax(Constants.Chassis.FRONT_RIGHT_ID,
      Constants.Chassis.MOTOR_TYPE);
  private final CANSparkMax backLeftMotor = new CANSparkMax(Constants.Chassis.BACK_LEFT_ID,
      Constants.Chassis.MOTOR_TYPE);
  private final CANSparkMax backRightMotor = new CANSparkMax(Constants.Chassis.BACK_RIGHT_ID,
      Constants.Chassis.MOTOR_TYPE);

  private final RelativeEncoder m_leftEncoder = frontLeftMotor.getEncoder();
  private final RelativeEncoder m_rightEncoder = frontRightMotor.getEncoder();

  private final AHRS m_gyro = new AHRS();

  private final DifferentialDrive m_drive = new DifferentialDrive(frontLeftMotor, frontRightMotor);

  /** Creates a new ExampleSubsystem. */
  public Drivetrain() {
    super();

    frontLeftMotor.setInverted(Constants.Chassis.INVERTED);
    frontRightMotor.setInverted(!Constants.Chassis.INVERTED);

    backLeftMotor.follow(frontLeftMotor, Constants.Chassis.OUT_OF_SYNC);
    backRightMotor.follow(frontRightMotor, Constants.Chassis.OUT_OF_SYNC);

    m_leftEncoder.setPositionConversionFactor(Constants.Chassis.LEFT_POSITION_CONVERSION);
    m_rightEncoder.setPositionConversionFactor(Constants.Chassis.RIGHT_POSITION_CONVERSION);

    m_leftEncoder.setVelocityConversionFactor(Constants.Chassis.LEFT_VELOCITY_CONVERSION);
    m_rightEncoder.setVelocityConversionFactor(Constants.Chassis.RIGHT_VELOCITY_CONVERSION);

    addChild("Gyro", m_gyro);
  }

  /** The log method puts interesting information to the SmartDashboard. */
  public void log() {
    SmartDashboard.putNumber("Left Distance", m_leftEncoder.getPosition());
    SmartDashboard.putNumber("Right Distance", m_rightEncoder.getPosition());
    SmartDashboard.putNumber("Left Speed", m_leftEncoder.getVelocity());
    SmartDashboard.putNumber("Right Speed", m_rightEncoder.getVelocity());
    SmartDashboard.putNumber("Gyro Pitch", m_gyro.getPitch());
  }

  /**
   * Drives the robot using arcade controls.
   *
   * @param fwd the commanded forward movement
   * @param rot the commanded rotation
   */
  public void drive(double fwd, double rot) {
    m_drive.arcadeDrive(fwd, rot);
  }

  /** Resets the drive encoders and gyro to currently read a position of 0. */
  public void reset() {
    m_gyro.reset();
    m_leftEncoder.setPosition(0);
    m_rightEncoder.setPosition(0);
  }

  /**
   * Get the robot's heading.
   *
   * @return The robots heading in degrees.
   */
  public double getHeading() {
    return m_gyro.getAngle();
  }

  /**
   * Get the robot's pitch.
   *
   * @return The robots pitch in degrees.
   */
  public double getPitch() {
    return m_gyro.getPitch();
  }

  /**
   * Gets the distance of the left encoder.
   *
   * @return the left encoder readings
   */
  public double getLeftEncoderDistance() {
    return m_leftEncoder.getPosition();
  }

  /**
   * Gets the distance of the right encoder.
   *
   * @return the right encoder readings
   */
  public double getRightEncoderDistance() {
    return m_rightEncoder.getPosition();
  }

  /**
   * Gets the average distance of the TWO encoders.
   *
   * @return the average of the TWO encoder readings
   */
  public double getAverageEncoderDistance() {
    return (m_leftEncoder.getPosition() + m_rightEncoder.getPosition()) / 2.0;
  }

  /**
   * Sets the max output of the drive. Useful for scaling the drive to drive more
   * slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   */
  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }

  /**
   * Balance command factory method.
   *
   * @return a command
   */
  public CommandBase balanceCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a
   * digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean balanced() {
    // Query gyro balnace complete.
    return this.getPitch() == 0;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    this.log();
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    super.initSendable(builder);
    // Publish encoder distances to telemetry.
    builder.addDoubleProperty("Left Travel", m_leftEncoder::getPosition, null);
    builder.addDoubleProperty("Right Travel", m_rightEncoder::getPosition, null);
  }

}
