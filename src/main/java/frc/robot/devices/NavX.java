package frc.robot.devices;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

public class NavX {
    private AHRS ahrs;
    private double offset;

    public NavX() {
    }

    public void init() {
        try {
            ahrs = new AHRS(SPI.Port.kOnboardCS0);
            //ahrs.reset();
            ahrs.zeroYaw();
            offset = ahrs.getYaw();
        } catch(Exception e) {
            System.out.println("Error instantiating NavX");
        }
    }

    public double getFieldAngle() {
        return ahrs.getAngle();
        //return ahrs.getYaw() - offset;
    }
}
