package rbtrnx.samy.test;
import java.lang.*;

public class MotionProfileFollower{
    MotionProfile profile;
    double maxSpeed;
    public static double EPSILON_VALUE = 0.0001;

    public boolean isWithinEpsilon(double o){
        if(Math.abs(o)>EPSILON_VALUE)
            return false;
        else
            return true;
    }
    public MotionProfileFollower(double motorMaxSpeed){
        maxSpeed = motorMaxSpeed;
    }
    public void setProfile(MotionProfile currentProfile){
        profile = currentProfile;
    }
    public double getMotorOutputFB(double distanceTraveled, double time, double speed){
        DriveState intendedDriveState = profile.driveStateAtTime(time);
        double speedDelta = intendedDriveState.speed - speed;
        double distanceDelta = intendedDriveState.position - distanceTraveled;
        // TODO: Implement PID controller dependent on distance delta and speed delta
        return 0;
    }
    public double getMotorOutput(double time){
        DriveState currentState = profile.driveStateAtTime(time);
        return currentState.speed / maxSpeed;
    }
    public boolean isFinishedTime(double time){
        MotionSegment lastSegment = profile.segments.get(profile.segments.size() - 1);
        DriveState finalDriveState = lastSegment.endState;
        if(time + EPSILON_VALUE >= lastSegment.endState.time)
            return true;
        else
            return false;
    }
    public boolean isFinishedDistance(double pos){
        MotionSegment lastSegment = profile.segments.get(profile.segments.size() - 1);
        DriveState finalDriveState = lastSegment.endState;
        return isWithinEpsilon(finalDriveState.position - pos);
    }
}