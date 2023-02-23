package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.ArmEnum;

public class ArmController extends SubsystemBase {
    private XboxController xbox;

    public ArmController() {
        xbox = new XboxController(1);
    }

    public ArmEnum getCurrentVelocity() 
    {
        if (xbox.getRightBumper()) 
        {
            return ArmEnum.PICKUP;
        } 
        if (xbox.getLeftBumper()) 
        {
            return ArmEnum.STOW;
        }
        return ArmEnum.STOP;
    }

    @Override
    public void periodic() {}
}