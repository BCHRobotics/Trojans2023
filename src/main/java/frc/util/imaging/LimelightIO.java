package frc.util.imaging;

import java.awt.geom.Line2D;
import java.util.*;

import edu.wpi.first.networktables.*;
import frc.robot.Constants;



import frc.subsystems.Drivetrain;
//import frc.io.SensorInput; //for future sensor input (motors and gyro)

public class LimelightIO {
    private static LimelightIO Instance;

    private NetworkTable table;

    private Drivetrain dr = Drivetrain.getInstance();

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

    private class pair{
        double first;
        double second;
        private pair(double f, double s){
            first=f;
            second=s;
        }
    }

    private pair coords[]; //array with coords for differing stations

    public void gotostat(int id){ //calls gotocoords using the preset coords of each respective station
        gotocoords(coords[id].first, coords[id].second);
    }

    private double getdis(double x1, double y1, double x2, double y2){
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }

    public void gotocoords(double x, double y){ //actual method that makes bot go vroom vroom (in progress)
        double curx = getx();
        double cury = gety();
        double curAng = getanglex();

        double s = 0;

        double[] result = crosscharge(curx, cury, x-s, y);
        while(result[0]!=Double.MAX_VALUE){//continuosly move until charge station is no longer in way of path
            if(getdis(x, y, result[0], result[1])>getdis(x, y, result[2], result[3])){ //move to corner of charge station closest to original target
                //drive to result[0] and result[1] may need tuning
                //turn to target position
                double ang = Math.atan2(result[0]-curx, result[1]-cury);
                dr.seekTargetPID(getanglex()-ang);
                //move forward until at target


                curx=result[0];
                cury=result[1];
            }else{
                double ang = Math.atan2(result[0]-curx, result[1]-cury);
                //drive to result[2] and result[3] may need tuning
                dr.seekTargetPID(getanglex()-ang);
                //move forward until at target
                curx=result[2];
                cury=result[3];
            }

            result = crosscharge(curx, cury, x-s, y);
        }

        //clear path, drive directly to coords but leave some space
        //use space to rotate bot towards target
        //move forward to get to target coords

    } 
    
    



    public double[] crosscharge(double x1, double y1, double x2, double y2){ //part of charge station which bot could collide with
        double[] result = new double[4];
        Arrays.fill(result, Double.MAX_VALUE); //null values for if bot doesn't collide

        Line2D route = new Line2D.Double(x1, y1, x2, y2); //robot path
        Line2D top = new Line2D.Double(Constants.cx2, Constants.cy1, Constants.cx1, Constants.cy1); //top charging station line segment
        Line2D btm = new Line2D.Double(Constants.cx2, Constants.cy2, Constants.cx1, Constants.cy2); //bottom charging station line segment
        Line2D left = new Line2D.Double(Constants.cx2, Constants.cy2, Constants.cx2, Constants.cy1); //left charging station line segment
        Line2D right = new Line2D.Double(Constants.cx1, Constants.cy2, Constants.cx1, Constants.cy1); //right charging station line segment

        //edge case if bot is travelling parallel to an axis
        if(y2-y1==0){ //x axis
            if(x2>x1&&route.intersectsLine(left)){//if target is to the right of bot
                setresult(result, "left");
            }else if(route.intersectsLine(right)){//if target is to the left of bot
                setresult(result, "right");
            }
            return result;
        }
        if(x2-x1==0){ //y axis
            if(y2>y1&&route.intersectsLine(top)){//if target is above the bot
                setresult(result, "top");
            }else if(route.intersectsLine(btm)){//if target is below the bot
                setresult(result, "btm");
            }
            return result;
        }

        double m = (y2-y1)/(x2-x1);

        
        if(m>0){
            //slope is positive
            if(x2>x1){
                //x increases and slope is positive, meaning robot goes from btm left to top right, thus must cross btm or left line first
                if(route.intersectsLine(left)){
                    setresult(result, "left");
                }else if(route.intersectsLine(btm)){
                    setresult(result, "btm");
                }
                return result; 
            }else {
                //x decreases and slope is positive, meaning robot goes from top right to btm left, thus must cross top or right line first
                if(route.intersectsLine(top)){
                    setresult(result, "top");
                }else if(route.intersectsLine(right)){
                    setresult(result, "right");
                }
                return result;
            }
        }else if(m<0){
            //slope is negative
            if(x2>x1){
                //x increases and slope is negative, meaning robot goes from top left to btm right, thus must cross top or left line first
                if(route.intersectsLine(top)){
                    setresult(result, "top");
                }else if(route.intersectsLine(left)){
                    setresult(result, "left");
                }
                return result;
            }else {
                //x decreases and slope is negative, meaning robot goes from btm right to top left, thus must cross btm or right line first
                if(route.intersectsLine(btm)){
                    setresult(result, "btm");
                }else if(route.intersectsLine(right)){
                    setresult(result, "right");
                }
                return result;
            }
        }
        return result;
    }

    private void setresult(double result[], String s){//function to set values for intersection
        if(s.equals("left")){
            result[0] = Constants.cx2;
            result[1] = Constants.cy2;
            result[2] = Constants.cx2;
            result[3] = Constants.cy1;
        }else if(s.equals("right")){
            result[0] = Constants.cx1;
            result[1] = Constants.cy2;
            result[2] = Constants.cx1;
            result[3] = Constants.cy1;
        }else if(s.equals("top")){
            result[0] = Constants.cx2;
            result[1] = Constants.cy1;
            result[2] = Constants.cx1;
            result[3] = Constants.cy1; 
        }else{ //bottom
            result[0] = Constants.cx2;
            result[1] = Constants.cy2;
            result[2] = Constants.cx1;
            result[3] = Constants.cy2;
        }
    }


}