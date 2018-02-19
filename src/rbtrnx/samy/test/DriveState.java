package rbtrnx.samy.test;
import java.lang.*;

public class DriveState{
    public static double EPSILON_VALUE = 0.0001;

    public boolean isWithinEpsilon(double o){
        if(Math.abs(o)>EPSILON_VALUE)
            return false;
        else
            return true;
    }
    double time;
    double accel;
    double speed;
    double position;

    public DriveState(double time, double accel, double speed, double position){
        this.time = time;
        this.accel = accel;
        this.speed = speed;
        this.position = position;
    }

    public DriveState(DriveState copy){
        this(copy.time, copy.accel, copy.position, copy.speed);
    }

    public double getTime(){
        return time;
    }

    public double getSpeed(){
        return speed;
    }
    public double getAccel(){
        return accel;
    }
    public double getPosition(){
        return position;
    }

    public DriveState extrapolate(double time){
        return extrapolate(time, this.accel);
    }
    
    public DriveState extrapolate(double time, double accel){
        //Il est possible d'extrapoler la distance parcourue sous acceleration constante avec la
        //formule s=ut+(1/2)(at*t), ou s est la position, u est la vitesse initiale, t est le temps,
        //et a est lacceleration
        double deltaTime = time - this.time;
        double pos = this.position + (this.speed * deltaTime) + 0.5*(accel * deltaTime * deltaTime);
        double speed = this.speed + (accel * deltaTime);
        return new DriveState(time, accel, speed, pos);
    }

    public double timeToPos(double pos){
        if(this.accel == 0 && this.speed == 0)
            return Double.POSITIVE_INFINITY;
        if(!isWithinEpsilon(this.accel)) { //Dans ce cas, nous avons une formule quadratique
            //Il y a donc une racine a l'équation d'accélertaion. Nous devons donc trouver la racine
            //de la formule quadratique ax2+bx+c ou:
            //a = 0.5*acceleration
            //b = vitesse initiale
            //c = position initiale - position finale
            //x est le delta de temps
            double a = 0.5 * this.accel;
            double b = this.speed;
            double c = -(pos - this.position);
            double discriminant = b * b - 4 * a * c;
            //Si le discriminant est 0, la position ne sera jamais atteinte
            if (discriminant < 0)
                return Double.NaN;
            double deltaTimeMax = (-b + Math.sqrt(discriminant)) / (2 * a);
            double deltaTimeMin = (-b - Math.sqrt(discriminant)) / (2 * a);
            double finalDeltaTime = deltaTimeMin >= 0 ? deltaTimeMin : deltaTimeMax;

            if (finalDeltaTime < 0) {
                return Double.NaN;
            }
            return finalDeltaTime + this.time;
        }
        else { // Dans ce cas ci, l'accélération est nulle, ce nest donc pas une fonction quadratique
            double returnTime = (pos - this.position) / this.speed;
            if (returnTime < 0){
                return Double.NaN;
            }
            else
                return returnTime + this.time;
        }
    }
    public double timeToSpeed(double maxSpeed){
        double deltaSpeed = maxSpeed-this.speed;
        return deltaSpeed/accel + this.time;
    }
    public double speedAtTime(float time){
        return speed + accel*(time - this.time);
    }
}