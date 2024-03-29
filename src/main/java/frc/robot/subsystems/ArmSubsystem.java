// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ArmEnum;
import frc.robot.commands.ArmCommand;
import frc.robot.devices.ArmEncoder;
import frc.robot.devices.SwingArmMotor;

public class ArmSubsystem extends SubsystemBase{
    private SwingArmMotor m_SwingArmMotor;
	public ArmEncoder m_ArmEncoder;
	
    public ArmSubsystem(SwingArmMotor SwingAM, ArmEncoder ae) 
    {
        m_SwingArmMotor = SwingAM;
		m_ArmEncoder = ae;
    }
	
    public void setNewVelocity(ArmEnum newVelocity)
	{
		if (newVelocity == ArmEnum.PICKUP) 
		{
			if (m_ArmEncoder.getPos() >= 12.0) 
			{
				m_SwingArmMotor.increasePosition();
			}
		}

		if (newVelocity == ArmEnum.FASTPICKUP) 
		{
			if (m_ArmEncoder.getPos() >= 12.0) {
				m_SwingArmMotor.fastIncrease(300); 
			}
		}

		if(newVelocity == ArmEnum.FASTSTOW) 
		{
			if (m_ArmEncoder.getPos() <= 295.0) {
				m_SwingArmMotor.fastDecrease(300); 
			} 
		}
		
		if (newVelocity == ArmEnum.STOW) 
		{
			if (m_ArmEncoder.getPos() <= 295.0) 
			{
				m_SwingArmMotor.decreasePosition();
			}
		}
	}    
}