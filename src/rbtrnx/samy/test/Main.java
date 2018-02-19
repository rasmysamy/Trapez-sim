package rbtrnx.samy.test;

import rbtrnx.samy.test.*;

public class Main {

    public static void main(String[] args) {
	    TrapezProfile testProfile = new TrapezProfile(0.1, 1, 0.2, 0);
	    MotionProfile test = testProfile.generatedProfile;
	    MotionProfileFollower follower = new MotionProfileFollower(1);
	    follower.setProfile(test);
	    double initialTime = System.currentTimeMillis();
	    double lastTime = initialTime;
	    while(true){
	        if(follower.isFinishedTime((System.currentTimeMillis()-initialTime)/1000))
	            break;
	        double motorOutput = follower.getMotorOutput((System.currentTimeMillis()-initialTime)/1000);
            try {
                Thread.sleep((long)(50-(System.currentTimeMillis() - lastTime)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(motorOutput);
            System.out.print(",");
            System.out.print(test.driveStateAtTime((System.currentTimeMillis()-initialTime)/1000).position);
            System.out.print(",");
            System.out.println(test.driveStateAtTime((System.currentTimeMillis()-initialTime)/1000).speed);
            lastTime = System.currentTimeMillis();
        }
    }
}
