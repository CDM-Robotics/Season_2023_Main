package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;

public class DriveController extends SubsystemBase {
    private XboxController xbox;

    private double desiredAngle;
    private double desiredThrottle;
    private double rotation;
    private boolean wantToRotate;
    private double speedModifier;

    public DriveController() {
        xbox = new XboxController(0);
        //speedModifier = 1.0;
    }

    @Override
    public void periodic() {
        double x, y, mag;

        if(xbox.getRightBumper()) {
            speedModifier = 0.25;
        } else if (xbox.getLeftBumper()) {
            speedModifier = 3.00;
        } else {
            speedModifier = 1.75;
        }

        x = xbox.getLeftX();  // +/- 1.0
        y = -xbox.getLeftY(); // +/- 1.0  Forward is negative, go figure  
        rotation = xbox.getRightX();

        if(Math.abs(rotation) < 0.1) { 
            rotation = 0.0;
            wantToRotate = false;
        } else {
            wantToRotate = true;
        }


        // At 45 degrees, both x and y can have values of 1.0, so we need to normalize
        // the values into unit vectors
        mag = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

        if(mag > 1.0) {
            x = x / mag;
            y = y / mag;
            mag = 1.0;
        }

        // This isn't necessary, since the sqrt will always be positive
        desiredThrottle = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));;
        
        if(desiredThrottle < 0.1) {
            desiredThrottle = 0.0;
            // For now, only allow rotations when the desired throttle is zero
            if(!wantToRotate) {
                desiredAngle = 0.0;
                return;
            } else {
                // Allow a pure rotation
                return;
            }
        }
        
        if(desiredThrottle > 1.0) {
            desiredThrottle = 1.0;
        }

        desiredThrottle = desiredThrottle * Constants.MAX_VELOCITY;

        // Math.acos only returns positive values from 0 to pi
        desiredAngle = Math.acos(y / mag) / Math.PI * 180.0;  // The hypotenuse = 1 for unit circle
        if(x < 0.0) {
            desiredAngle = -desiredAngle;
        }
    }

    public double getDesiredThrottle() {
        if (!DriverStation.isAutonomous()) 
        {
            return desiredThrottle;
        }
        return 0.0;
    }

    public double getDesiredAngle() {
        return desiredAngle;
    }

    public boolean allowedToRotate() {
        return (desiredThrottle == 0.0);
    }

    public double getRotation() {
        return rotation;
    }

    public double getSpeedModifier() {
        return speedModifier;
    }
}