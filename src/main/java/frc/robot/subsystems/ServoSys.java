package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ServoEnum;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ServoSys extends SubsystemBase
{
	private PWM testPWM;
    
    public ServoSys() {}

	public Boolean intialize() 
	{
		testPWM = new PWM(0);
		return true;
	} 

    public void setNewPosition(ServoEnum newPosition)
	{
		if (newPosition == ServoEnum.OPEN) 
		{
			testPWM.setBounds(0.55, 0.004, 0.525, 0.004, 0.50);
		}
		if (newPosition == ServoEnum.STOP) 
		{
			testPWM.setBounds(1.525, 0.004, 1.5, 0.004, 1.475);
		}
		if (newPosition == ServoEnum.CLOSE) 
		{
			testPWM.setBounds(2.50, 0.004, 2.475, 0.004, 2.45);
		}
		testPWM.setPosition(1);
	}    
}