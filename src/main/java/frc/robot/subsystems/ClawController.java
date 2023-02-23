package frc.robot.subsystems;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ServoEnum;

public class ClawController extends SubsystemBase
{
    private XboxController xbox;

    public ClawController() 
    {
        xbox = new XboxController(1);
    }

    public ServoEnum getCurrentPos() 
    {
        if (xbox.getBButton()) 
        {
            return ServoEnum.OPEN;
        } 
        if (xbox.getXButton()) 
        {
            return ServoEnum.CLOSE;
        }
        return ServoEnum.STOP;
    }

    
    @Override
    public void periodic() {}
}