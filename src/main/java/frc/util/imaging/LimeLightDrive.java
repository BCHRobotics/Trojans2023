package frc.util.imaging;
import java.util.*;
import java.awt.geom.Line2D;

import frc.robot.Constants;
import frc.subsystems.Drivetrain;

public class LimeLightDrive {
    private static LimeLightDrive Instance;

    public static LimeLightDrive getInstance(){
        if(Instance==null){
            Instance = new LimeLightDrive();
        }
        return Instance;
    }

    private LimelightIO LLI = LimelightIO.getInstance();
    private Drivetrain dr = Drivetrain.getInstance();

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
        double curx = LLI.getx();
        double cury = LLI.gety();

        double s = 0;

        double[] result = crosscharge(curx, cury, x-s, y);
        while(result[0]!=Double.MAX_VALUE){//continuosly move until charge station is no longer in way of path
            if(getdis(x, y, result[0], result[1])>getdis(x, y, result[2], result[3])){ //move to corner of charge station closest to original target
                //drive to result[0] and result[1] may need tuning
                //turn to target position
                double ang = Math.atan2(result[0]-curx, result[1]-cury);
                dr.seekTargetPID(LLI.getanglex()-ang);
                //move forward until at target


                curx=result[0];
                cury=result[1];
            }else{
                double ang = Math.atan2(result[0]-curx, result[1]-cury);
                //drive to result[2] and result[3] may need tuning
                dr.seekTargetPID(LLI.getanglex()-ang);
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
                setResult(result, "left");
            }else if(route.intersectsLine(right)){//if target is to the left of bot
                setResult(result, "right");
            }
            return result;
        }
        if(x2-x1==0){ //y axis
            if(y2>y1&&route.intersectsLine(top)){//if target is above the bot
                setResult(result, "top");
            }else if(route.intersectsLine(btm)){//if target is below the bot
                setResult(result, "btm");
            }
            return result;
        }

        double m = (y2-y1)/(x2-x1);

        if(m>0){
            //slope is positive
            if(x2>x1){
                //x increases and slope is positive, meaning robot goes from btm left to top right, thus must cross btm or left line first
                if(route.intersectsLine(left)){
                    setResult(result, "left");
                }else if(route.intersectsLine(btm)){
                    setResult(result, "btm");
                }
                return result; 
            }else {
                //x decreases and slope is positive, meaning robot goes from top right to btm left, thus must cross top or right line first
                if(route.intersectsLine(top)){
                    setResult(result, "top");
                }else if(route.intersectsLine(right)){
                    setResult(result, "right");
                }
                return result;
            }
        }else if(m<0){
            //slope is negative
            if(x2>x1){
                //x increases and slope is negative, meaning robot goes from top left to btm right, thus must cross top or left line first
                if(route.intersectsLine(top)){
                    setResult(result, "top");
                }else if(route.intersectsLine(left)){
                    setResult(result, "left");
                }
                return result;
            }else {
                //x decreases and slope is negative, meaning robot goes from btm right to top left, thus must cross btm or right line first
                if(route.intersectsLine(btm)){
                    setResult(result, "btm");
                }else if(route.intersectsLine(right)){
                    setResult(result, "right");
                }
                return result;
            }
        }
        return result;
    }

    //method which formats results from crosscharge method
    private void setResult(double result[], String s){
        switch(s){
            case "left":
                result[0] = Constants.cx2;
                result[1] = Constants.cy2;
                result[2] = Constants.cx2;
                result[3] = Constants.cy1;
                break;
            case "right":
                result[0] = Constants.cx1;
                result[1] = Constants.cy2;
                result[2] = Constants.cx1;
                result[3] = Constants.cy1;
                break;
            case "top":
                result[0] = Constants.cx2;
                result[1] = Constants.cy1;
                result[2] = Constants.cx1;
                result[3] = Constants.cy1; 
                break;
            case "btm":
                result[0] = Constants.cx2;
                result[1] = Constants.cy2;
                result[2] = Constants.cx1;
                result[3] = Constants.cy2;
                break;
        }
    }
}
