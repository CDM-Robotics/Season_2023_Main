// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ArmEnum;
import frc.robot.devices.SwingArmMotor;

public class ArmSubsystem extends SubsystemBase{
    private SwingArmMotor m_SwingArmMotor;
	
    public ArmSubsystem(SwingArmMotor SwingAM) 
    {
        m_SwingArmMotor = SwingAM;
    }
	
    public void setNewVelocity(ArmEnum newVelocity)
	{
		if (newVelocity == ArmEnum.PICKUP) 
		{
			//m_SwingArmMotor.setVelocity(0);
			m_SwingArmMotor.increasePosition();
		}
		if (newVelocity == ArmEnum.STOP) 
		{
			//m_SwingArmMotor.setVelocity(0);
		}
		if (newVelocity == ArmEnum.STOW) 
		{
			//m_SwingArmMotor.setVelocity(0);
			m_SwingArmMotor.decreasePosition();
		}
	}    
}