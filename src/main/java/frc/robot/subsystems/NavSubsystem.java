package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.devices.NavX;

public class NavSubsystem extends SubsystemBase {

    private NavX navx;
    private double fieldAngle;

    public NavSubsystem(NavX navx) {
        this.navx = navx;
        this.navx.init();;
    }

    @Override
    public void periodic() {
        fieldAngle = navx.getFieldAngle();
    }

    public double getFieldAngle() {
        return fieldAngle;
    }
}
