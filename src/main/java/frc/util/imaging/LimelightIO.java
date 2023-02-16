package frc.util.imaging;

import edu.wpi.first.networktables.*;
import frc.robot.Constants;

//import frc.io.SensorInput; //for future sensor input (motors and gyro)

public class LimelightIO {
    private static LimelightIO Instance;

    private NetworkTable table;

    private NetworkTableEntry tx; //x axis angle from target
    private NetworkTableEntry ty; //y axis angle from target
    private NetworkTableEntry ta; //area of target
    private NetworkTableEntry tid; //id of target
    private NetworkTableEntry tv; //if there is a target
    private NetworkTableEntry getpipe; //get current pipeline for limelight
    private NetworkTableEntry camerapose; //camera position in the game field
    private NetworkTableEntry botpose; //bot position in the game field

    public static LimelightIO getInstance(){
        if(Instance==null){
            Instance = new LimelightIO();
        }
        return Instance;
    }

    public LimelightIO(){
        table = NetworkTableInstance.getDefault().getTable("limelight");

        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        botpose = table.getEntry("botpose_targetspace");
        tid = table.getEntry("tid");
        tv = table.getEntry("tv");
        getpipe = table.getEntry("getpipe");
        camerapose = table.getEntry("camerapose_targetspace");
    }

    public void setLedMode(int ledMode) { //0 = set to pipeline led state, 1 = set to off,
                                          //2 = set to blink, 3 = set to constant on
        this.table.getEntry("ledMode").setNumber(ledMode);
    }
    public double gettid(){
        return tid.getDouble(0.0);
    }
    
    public boolean gettv(){
        return (tv.getDouble(0.0)==0?false:true);
    }

    public double getpipe(){
        return getpipe.getDouble(0.0);
    }

    public double[] getcamerapose(){
        return camerapose.getDoubleArray(new double[6]);
    }

    public double gettx(){
        return tx.getDouble(0.0);
    }

    public double getty(){
        return ty.getDouble(0.0);
    }

    public double getta(){
        return ta.getDouble(0.0);
    }

    public double getx(){
        return botpose.getDoubleArray(new double[6])[0];
    }

    public double gety(){
        return botpose.getDoubleArray(new double[6])[1];
    }

    public double getz(){
        return botpose.getDoubleArray(new double[6])[2];
    }

    public double getanglex(){
        return botpose.getDoubleArray(new double[6])[3];
    }

    public double[] getbotposearr(){
        return botpose.getDoubleArray(new double[6]);
    }

    public double getForOff(){ //returns forward offset from bot to april tag

        double a1 = getty();
        double ar = (a1+Constants.limelightVertAng)*(Math.PI/180);
        double dis = ((Constants.aprilTagHeight-Constants.limelightHeight)/Math.tan(ar))-Constants.limelightIndent; 

        dis = Math.round(dis*100)/100.0;
        return dis;
    }

    public double getSideOff(){ //return sideways offset from bot to april tag, negative is left, positive is right 
        double h1 = getForOff() + Constants.limelightIndent;
        double a1 = gettx();
        double ar = (a1+Constants.limelightHorAng)*(Math.PI/180);
        double dis = (h1-Constants.limelightIndent)*Math.tan(ar);

        dis = Math.round(dis*100)/100.0;
        return dis;
    }

    


}