package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ServoEnum;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ServoSys extends SubsystemBase
{
	private PWM testPWM;
	//private CANSparkMax testCANSpark;
	//private SparkMaxPIDController pidController;
	//private RelativeEncoder encoder;
    
    public ServoSys() {
		testPWM = new PWM(0);
		//testCANSpark = new CANSparkMax(28, MotorType.kBrushless);
		//testCANSpark.setIdleMode(IdleMode.kBrake);
		//pidController = testCANSpark.getPIDController();
		//encoder = testCANSpark.getEncoder();
		//pidController.setP(0.1);
		//pidController.setI(1e-4);
		//pidController.setD(1);
		//pidController.setIZone(0);
		//pidController.setFF(0);
		//pidController.setOutputRange(-1, 1);
		//thermalSafety();
	}

	public Boolean intialize() 
	{
		return true;
	} 

    public void setNewPosition(ServoEnum newPosition)
	{
		if (newPosition == ServoEnum.OPEN) 
		{
			testPWM.setBounds(0.55, 0.004, 0.525, 0.004, 0.50);
			//testCANSpark.set(0.75);
			//pidController.setReference(1.0, ControlType.kPosition);
		}
		if (newPosition == ServoEnum.STOP) 
		{
			testPWM.setBounds(1.525, 0.004, 1.5, 0.004, 1.475);
			//testCANSpark.set(0.0);
		}
		if (newPosition == ServoEnum.CLOSE) 
		{
			testPWM.setBounds(2.50, 0.004, 2.475, 0.004, 2.45);
			//testCANSpark.set(-0.75);
			//pidController.setReference(0.0, ControlType.kPosition);
		}
		testPWM.setPosition(1);
	}
	/*public void thermalSafety() 
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
	}*/
	/*public double getPosition() {
		return encoder.getPosition();
	}*/
}