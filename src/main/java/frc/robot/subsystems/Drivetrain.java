// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Chassis;
import frc.robot.util.control.SparkMaxPID;
import frc.robot.util.devices.Gyro;

public final class Drivetrain extends SubsystemBase {

  /**
   * The Drivetrain subsystem incorporates the sensors and actuators attached to
   * the robots chassis.
   * These include four drive motors, a left and right encoder and a gyro.
   */
  private static final CANSparkMax frontLeftMotor = new CANSparkMax(Chassis.FRONT_LEFT_ID,
      Chassis.MOTOR_TYPE);
  private static final CANSparkMax frontRightMotor = new CANSparkMax(Chassis.FRONT_RIGHT_ID,
      Chassis.MOTOR_TYPE);
  private static final CANSparkMax backLeftMotor = new CANSparkMax(Chassis.BACK_LEFT_ID,
      Chassis.MOTOR_TYPE);
  private static final CANSparkMax backRightMotor = new CANSparkMax(Chassis.BACK_RIGHT_ID,
      Chassis.MOTOR_TYPE);

  private static final RelativeEncoder leftEncoder = frontLeftMotor.getEncoder();
  private static final RelativeEncoder rightEncoder = frontRightMotor.getEncoder();

  private static final SparkMaxPID leftController = new SparkMaxPID(frontLeftMotor, Chassis.LEFT_PID_CONSTANTS);
  private static final SparkMaxPID rightController = new SparkMaxPID(frontRightMotor, Chassis.RIGHT_PID_CONSTANTS);

  private static final TrapezoidProfile.Constraints motionProfileConstraints = new Constraints(260, 320);
  private static final TrapezoidProfile leftMotionProfile = new TrapezoidProfile(motionProfileConstraints, null);
  private static final TrapezoidProfile rightMotionProile = new TrapezoidProfile(motionProfileConstraints, null);

  private static final Gyro gyro = new Gyro(Chassis.GYRO_PORT);

  private static final DifferentialDrive drive = new DifferentialDrive(frontLeftMotor, frontRightMotor);

  /** Creates a new ExampleSubsystem. */
  public Drivetrain() {

    super();

    frontLeftMotor.setInverted(Chassis.INVERTED);
    frontRightMotor.setInverted(!Chassis.INVERTED);

    backLeftMotor.follow(frontLeftMotor, Chassis.OUT_OF_SYNC);
    backRightMotor.follow(frontRightMotor, Chassis.OUT_OF_SYNC);

    leftEncoder.setPositionConversionFactor(Chassis.LEFT_POSITION_CONVERSION);
    rightEncoder.setPositionConversionFactor(Chassis.RIGHT_POSITION_CONVERSION);

    leftEncoder.setVelocityConversionFactor(Chassis.LEFT_VELOCITY_CONVERSION);
    rightEncoder.setVelocityConversionFactor(Chassis.RIGHT_VELOCITY_CONVERSION);

    leftController.setFeedbackDevice(leftEncoder);
    rightController.setFeedbackDevice(rightEncoder);

    addChild("Gyro", gyro);
  }

  /** The log method puts interesting information to the SmartDashboard. */
  public void log() {
    SmartDashboard.putNumber("Left Distance", leftEncoder.getPosition());
    SmartDashboard.putNumber("Right Distance", rightEncoder.getPosition());
    SmartDashboard.putNumber("Left Speed", leftEncoder.getVelocity());
    SmartDashboard.putNumber("Right Speed", rightEncoder.getVelocity());
    SmartDashboard.putNumber("Gyro Pitch", gyro.getPitch());
    Chassis.LEFT_PID_CONSTANTS.pushToDashboard("Left Drive");
    Chassis.RIGHT_PID_CONSTANTS.pushToDashboard("Right Drive");
  }

  /**
   * Drives the robot using arcade controls.
   *
   * @param fwd the commanded forward movement
   * @param rot the commanded rotation
   */
  public void drive(double fwd, double rot, boolean sqr) {
    drive.arcadeDrive(fwd, rot, sqr);
  }

  /**
   * Sets chassis left and right position in inches
   *
   * @param leftPos  the commanded left movement
   * @param rightPos the commanded right movement
   */
  public void setPosition(double leftPos, double rightPos) {
    leftController.setPosition(leftPos);
    rightController.setPosition(rightPos);
  }

  /**
   * Sets chassis left and right velocity in inches / sec
   *
   * @param leftVel  the commanded left speed
   * @param rightVel the commanded right speed
   */
  public void setVelocity(double leftVel, double rightVel) {
    leftController.setVelocity(leftVel);
    rightController.setVelocity(rightVel);
  }

  /** Resets the drive encoders and gyro to currently read a position of 0. */
  public void reset() {
    gyro.reset();
    Drivetrain.leftEncoder.setPosition(0);
    Drivetrain.rightEncoder.setPosition(0);
  }

  /**
   * Get the robot's heading.
   *
   * @return The robots heading in degrees.
   */
  public double getHeading() {
    return Drivetrain.gyro.getAngle();
  }

  /**
   * Get the robot's pitch.
   *
   * @return The robots pitch in degrees.
   */
  public double getPitch() {
    return Drivetrain.gyro.getPitch();
  }

  /**
   * Gets the distance of the left encoder.
   *
   * @return the left encoder readings
   */
  public double getLeftEncoderDistance() {
    return Drivetrain.leftEncoder.getPosition();
  }

  /**
   * Gets the distance of the right encoder.
   *
   * @return the right encoder readings
   */
  public double getRightEncoderDistance() {
    return Drivetrain.rightEncoder.getPosition();
  }

  /**
   * Gets the velocity of the left encoder.
   *
   * @return the left encoder readings
   */
  public double getLeftEncoderVelocity() {
    return Drivetrain.leftEncoder.getVelocity();
  }

  /**
   * Gets the velocity of the right encoder.
   *
   * @return the right encoder readings
   */
  public double getRightEncoderVelocity() {
    return Drivetrain.rightEncoder.getVelocity();
  }

  /**
   * Gets the average distance of the TWO encoders.
   *
   * @return the average of the TWO encoder readings
   */
  public double getAverageEncoderDistance() {
    return (this.getLeftEncoderDistance() + this.getRightEncoderDistance()) / 2.0;
  }

  /**
   * Gets the average velocity of the TWO encoders.
   *
   * @return the average of the TWO encoder readings
   */
  public double getAverageEncoderVelocity() {
    return (this.getLeftEncoderVelocity() + this.getRightEncoderVelocity()) / 2.0;
  }

  /**
   * Sets the max output of the drive. Useful for scaling the drive to drive more
   * slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   */
  public void setMaxOutput(double maxOutput) {
    Drivetrain.drive.setMaxOutput(maxOutput);
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a
   * digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean isBalanced() {
    // Query gyro balnace complete.
    return this.getPitch() == 0;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    super.periodic();
    leftController.retrieveDashboardConstants();
    rightController.retrieveDashboardConstants();
    this.log();
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    super.initSendable(builder);
    // Publish encoder distances to telemetry.
    builder.addDoubleProperty("Left Travel", Drivetrain.leftEncoder::getPosition, null);
    builder.addDoubleProperty("Right Travel", Drivetrain.rightEncoder::getPosition, null);
  }

}
