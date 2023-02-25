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
    private Drivetrain dt;

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
        this.dt = Drivetrain.getInstance();
        this.networkTable = NetworkTableInstance.getDefault().getTable("limelight");
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

            desiredTarget.setX(getTargetDistance());

            desiredTarget.setY(getTargetDistance());

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

    public void GoToApril(){ //goes to april tag
        if(this.getTargetX()<=-0.5||this.getTargetX()>=0.5) 
            this.dt.setYaw(this.getTargetX());//turns to april tag

        else{
            if(this.getTargetDistance()>=4) 
                this.dt.setOutput(0.2,0);
            else 
                this.dt.setOutput(0, 0);
        }
        //drive to april tag but leave some space to prevent ramming the april tag
        //2 is a placeholder
    }

    /*
     * Get the distance to the target using Trigonometry
     * 
     * @return distance to target
     */
    public double getTargetDistance() {
        double a1 = Constants.LIMELIGHT_ANGLE; // Limelight mount angle
        double a2 = this.getTargetY(); // Limelight measured angle to target
        double aR = (a1 + a2) * (Math.PI / 180); // Total anlge in Radians
        double h1 = Constants.LIMELIGHT_HEIGHT; // Limelight lens Height;
        double h2 = Constants.TARGET_HEIGHT; // Known Height of Target

        double distance = (Math.max(h1, h2) - Math.min(h1, h2)) / Math.tan(aR);

        distance = Math.round(distance * 100.0) / 100.0;
        SmartDashboard.putNumber(" Distance", distance);
        return distance;
    }

}