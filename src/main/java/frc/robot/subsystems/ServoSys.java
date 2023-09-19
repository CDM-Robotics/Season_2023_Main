package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ServoEnum;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ServoSys extends SubsystemBase
{
	//private PWM testPWM;
	private CANSparkMax testCANSpark;
    
    public ServoSys() {
		//testPWM = new PWM(0);
		testCANSpark = new CANSparkMax(28, MotorType.kBrushless);
		thermalSafety();
	}

	public Boolean intialize() 
	{
		return true;
	} 

    public void setNewPosition(ServoEnum newPosition)
	{
		if (newPosition == ServoEnum.OPEN) 
		{
			//testPWM.setBounds(0.55, 0.004, 0.525, 0.004, 0.50);
			testCANSpark.set(0.55);
		}
		if (newPosition == ServoEnum.STOP) 
		{
			//testPWM.setBounds(1.525, 0.004, 1.5, 0.004, 1.475);
			testCANSpark.set(0.0);
			//testCANSpark.stopMotor();
		}
		if (newPosition == ServoEnum.CLOSE) 
		{
			//testPWM.setBounds(2.50, 0.004, 2.475, 0.004, 2.45);
			testCANSpark.set(-0.55);
		}
	}
	public void thermalSafety() 
	{
		double temp = testCANSpark.getMotorTemperature();
		SmartDashboard.putNumber("NEO 550 Current Temperature", temp);
		
		if (temp >= 90.0) 
		{
			testCANSpark.stopMotor();
			SmartDashboard.putString("NEO Temperature Status", "Emergency Shutdown");
		} else {
			SmartDashboard.putString("NEO Temperature Status", "System Nominal");
		}
	}   
}