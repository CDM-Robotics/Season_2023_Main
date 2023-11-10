package frc.robot.subsystems;

import javax.swing.text.StyleContext.SmallAttributeSet;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.devices.NavX;

public class NavSubsystem extends SubsystemBase {

    private NavX navx;
    static double fieldAngle;
    private boolean initialized;
    private double startAngle;
    private int updating;

    {
        fieldAngle = 0.0;
    }

    public NavSubsystem(NavX navx) {
        this.navx = navx;
        this.navx.init();
        initialized = false;
        startAngle = 0.0;
        updating = 0;
    }

    @Override
    public void periodic() {
        
    }

    public void update() {
        double angle;

        angle = this.navx.getFieldAngle();
        NavSubsystem.setFieldAngle(angle);
        updating++;
        if(updating % 50 == 0) {
            SmartDashboard.putNumber("updating", updating);
        }
        SmartDashboard.putNumber("Raw Angle", angle);
        if(!initialized) {
            startAngle = getFieldAngle();
            initialized = true;
            SmartDashboard.putNumber("Start Angle", startAngle);
        }
    }

    public static synchronized void setFieldAngle(double a) {
        fieldAngle = a;
    }

    public static synchronized double getFieldAngle() {
        return fieldAngle;
    }
}
