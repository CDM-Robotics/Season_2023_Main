// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.devices.ArmEncoder;
import frc.robot.subsystems.ArmSubsystem;

public class ArmCommand extends CommandBase {
  private ArmSubsystem m_ArmSubsystem;

  public ArmCommand(ArmSubsystem as) {
    m_ArmSubsystem = as;

    addRequirements(m_ArmSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_ArmSubsystem.m_ArmEncoder.updatePos();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_ArmSubsystem.m_ArmEncoder.updatePos();
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
