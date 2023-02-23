// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmController;
import frc.robot.subsystems.ArmSubsystem;

/** Add your docs here. */
public class ControllerCommand extends CommandBase {
    private ArmController m_ac;
    private ArmSubsystem m_as;
  
    public ControllerCommand(ArmController ac, ArmSubsystem as) {
      m_ac = ac;
      m_as = as;

      addRequirements(m_as);
      addRequirements(m_ac);
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() 
    {
      m_as.setNewVelocity(m_ac.getCurrentVelocity());
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }
}