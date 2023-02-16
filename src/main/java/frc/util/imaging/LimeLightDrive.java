/*
package frc.util.imaging;

import java.util.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import frc.robot.Constants;
import frc.io.subsystems.DriveIO;
import frc.subsystems.Drivetrain;

public class LimeLightDrive extends LimelightIO{
    private static LimeLightDrive Instance;

    public static LimeLightDrive getInstance(){
        if(Instance==null){
            Instance = new LimeLightDrive();
        }
        return Instance;
    }

    private LimelightIO LLI = LimelightIO.getInstance();
    private Drivetrain dr = Drivetrain.getInstance();
    private DriveIO drio = DriveIO.getInstance();

    private class pair{
        double first;
        double second;
        double third;
        private pair(double f, double s, double t){
            first=f;
            second=s;
            third=t;
        }
    }

    private pair coords[]; //array with coords for differing stations

    public void gotostat(int id){ //calls gotocoords using the preset coords of each respective station
        gotocoords(coords[id].first, coords[id].second, coords[id].third);
    }

    private double getdis(double x1, double y1, double x2, double y2){
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }

    
    // gotocoords function
    //Makes the bot go to a certain point on the field using the limelight field map coordinate system
    //intakes the x coords and y coords
    //also intakes the final orientation of the robot at its end destination
    //
    //The bot first gets to a point where the charge station is not in its direct trajectory
    //Once the robot has a clear trajectory to its final point it moves to the point but leaves room for
    //allignment to get the bot to the final orientation
    
 
    public void gotocoords(double x, double y, double o){ //actual method that makes bot go vroom vroom (in progress)
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
                double dis = getdis(result[0], result[1], curx, cury);
                drio.setDriveLeftPos(dis);
                drio.setDriveRightPos(dis);

                curx=result[0];
                cury=result[1];
            } else{
                double ang = Math.atan2(result[0]-curx, result[1]-cury);
                //drive to result[2] and result[3] may need tuning
                dr.seekTargetPID(LLI.getanglex()-ang);
                //move forward until at target
                double dis = getdis(result[2], result[3], curx, cury);
                drio.setDriveLeftPos(dis);
                drio.setDriveRightPos(dis);
                
                curx=result[2];
                cury=result[3];
            }

            result = crosscharge(curx, cury, x-s, y);
        }
        //clear path, drive directly to coords but leave some space
        double tempx = x-(0.5*Math.sin(o));
        double tempy = y-(0.5*Math.cos(o));

        double ang = Math.atan2(tempx-curx, tempy-cury);
        dr.seekTargetPID(LLI.getanglex()-ang);

        double dis = getdis(tempy, tempx, curx, cury);
        drio.setDriveLeftPos(dis);
        drio.setDriveRightPos(dis);

        curx=result[0];
        cury=result[1];
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
            } else if(route.intersectsLine(right)){//if target is to the left of bot
                setResult(result, "right");
            }
            return result;
        }
        if(x2-x1==0){ //y axis
            if(y2>y1&&route.intersectsLine(top)){//if target is above the bot
                setResult(result, "top");
            } else if(route.intersectsLine(btm)){//if target is below the bot
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
                } else if(route.intersectsLine(btm)){
                    setResult(result, "btm");
                }
                return result; 
            } else{
                //x decreases and slope is positive, meaning robot goes from top right to btm left, thus must cross top or right line first
                if(route.intersectsLine(top)){
                    setResult(result, "top");
                } else if(route.intersectsLine(right)){
                    setResult(result, "right");
                }
                return result;
            }
        } else if(m<0){
            //slope is negative
            if(x2>x1){
                //x increases and slope is negative, meaning robot goes from top left to btm right, thus must cross top or left line first
                if(route.intersectsLine(top)){
                    setResult(result, "top");
                }else if(route.intersectsLine(left)){
                    setResult(result, "left");
                }
                return result;
            }  else{
                //x decreases and slope is negative, meaning robot goes from btm right to top left, thus must cross btm or right line first
                if(route.intersectsLine(btm)){
                    setResult(result, "btm");
                } else if(route.intersectsLine(right)){
                    setResult(result, "right");
                }
                return result;
            }
        }
        return result;
    }

    //method which formats results from crosscharge method
    private void setResult(double result[], String s){
        if(s.equals("left")||s.equals("top")||s.equals("btm")) result[0]=Constants.cx2;
        else result[0]=Constants.cx1;

        if(s.equals("left")||s.equals("right")||s.equals("btm")) result[1]=Constants.cy2;
        else result[1]=Constants.cy1;

        if(s.equals("top")||s.equals("right")||s.equals("btm")) result[2]=Constants.cx1;
        else result[2]=Constants.cx2;

        if(s.equals("top")||s.equals("right")||s.equals("left")) result[3]=Constants.cy1;
        else result[3]=Constants.cy2;
    }

    public Trajectory createTrajectory(Pose2d target) {
        ArrayList<Point2D> pointsInTrajectory = new ArrayList<Point2D>();
        pointsInTrajectory.add(new Point2D.Double(getx(), gety()));
        pointsInTrajectory.add(new Point2D.Double(target.getX(), target.getY()));
        Line2D lineToTarget = new Line2D.Double(getx(), gety(), target.getX(), target.getY());
        Point2D[] chargeStationCorners = 
            {new Point2D.Double(Constants.cx1, Constants.cy1), new Point2D.Double(Constants.cx1, Constants.cy2),
             new Point2D.Double(Constants.cx2, Constants.cy1), new Point2D.Double(Constants.cx2, Constants.cy2)};

        while(Constants.CHARGE_STATION.intersectsLine(lineToTarget)) {
            pointsInTrajectory.add(1, null);
            double closestDistance = 1e100;
            for(Point2D corner : chargeStationCorners) {
                if(Math.hypot(corner.getX() - getx(), corner.getY() - gety()) < closestDistance) {
                    pointsInTrajectory.set(1, corner);
                }
            }
            lineToTarget = new Line2D.Double(pointsInTrajectory.get(1), new Point2D.Double(target.getX(), target.getY()));
        }
        
        return new Trajectory();
    }
}

*/
