package frc.util.imaging;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.subsystems.Drivetrain;

public class Limelight {
    private static Limelight instance;

    private NetworkTable networkTable;
    private LimelightTargetType currentTarget;
    private Drivetrain drive;

    public static Limelight getInstance() {
        if (instance == null) {
            instance = new Limelight();
        }
        return instance;
    }

    public enum LimelightTargetType {
        CONE, CUBE
    }

    public Limelight() {
        drive = Drivetrain.getInstance();
        networkTable = NetworkTableInstance.getDefault().getTable("limelight");
    }

    public void setPipeline(int pipeline) {
        this.networkTable.getEntry("pipeline").setNumber(pipeline);
    }

    public double getPipeline() {
        return this.networkTable.getEntry("getpipe").getDouble(0);
    }

    public void setDesiredTarget(LimelightTargetType target) {
        this.currentTarget = target;
    }

    public LimelightTargetType getDesiredTarget() {
        return this.currentTarget;
    }

    public LimelightTarget getTargetInfo() {
       
        LimelightTarget desiredTarget = new LimelightTarget(this.currentTarget);

        if (currentTarget == LimelightTargetType.CONE || currentTarget == LimelightTargetType.CUBE) {

            desiredTarget.setX(getTargetDistance(20));

            desiredTarget.setY(getTargetDistance(20));

            desiredTarget.setTargetFound(getTargetExists());

        } else {
            desiredTarget.setTargetFound(false);
        }

        return desiredTarget;
    }

    /**
     * Change the mode of the led 1-off, 2-blink, 3-on
     * 
     * @param ledMode led mode
     */
    public void setLedMode(int ledMode) {
        this.networkTable.getEntry("ledMode").setNumber(ledMode);
    }

    public double getTargetX() {
        return this.networkTable.getEntry("tx").getDouble(0);
    }

    public double getTargetY() {
        return this.networkTable.getEntry("ty").getDouble(0);
    }

    public boolean getTargetExists() {
        return this.networkTable.getEntry("tv").getDouble(0) == 1;
    }

    public double getTargetArea() {
        return this.networkTable.getEntry("ta").getDouble(0);
    }

    public void goToApril(){ //goes to april tag
       /*  if(this.getTargetX()<=-0.5||this.getTargetX()>=0.5){*/
            drive.setYawPID(this.getTargetX());//turns to april tag
        /* } else{
            if(this.getTargetDistance()>=4) 
                drive.setOutput(0.2,0);
            else 
                drive.setOutput(0, 0);
        }*/
        //drive to april tag but leave some space to prevent ramming the april tag
        //2 is a placeholder
    }

    /*
     * Get the distance to the target using Trigonometry
     * 
     * @return distance to target
     */
      /**
     * Calculates the distance to a target on the ground using trigonometry
     * It is impossible to calculate the distance to a target above the limelight
     * 
     * @return Distance to target in inches (-1 if target >= limelight)
     */
    public double getTargetDistance() {
        // If the target is above the limelight, return -1
        if (this.getTargetY() > 0) return -1;

        double theta = this.getTargetY() + 90;
        double height = Constants.LIMELIGHT_HEIGHT;
        double distance = height * Math.tan(Math.toRadians(theta));

        return distance;
    }

    /**
     * Calculatues the distance to a target on the ground using trigonometry
     * Distance is calculated using the height of the target and the limelight angle
     * 
     * @param targetHeight Inches from the ground to the target
     * @return Distance to target in inches (-1 if target is the same height as the limelight)
     */
    public double getTargetDistance(double targetHeight) {
        double theta = this.getTargetY();
        // If the target is the same height as the limelight, return -1
        if (theta == 0) return -1;

        // If the target is below the limelight, add 90 degrees to the angle
        if (theta < 0) theta += 90;

        double height = Math.max(Constants.LIMELIGHT_HEIGHT, targetHeight) - Math.min(Constants.LIMELIGHT_HEIGHT, targetHeight);
        double distance = height * Math.tan(Math.toRadians(theta));
        return distance;
    }

}