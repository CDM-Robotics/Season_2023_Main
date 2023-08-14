// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.devices.SwerveState;
import frc.robot.subsystems.DriveController;
import frc.robot.subsystems.DriveSubsystem;

import java.util.Date;

public class DriveCommand extends CommandBase {
  private DriveSubsystem m_driveSubsystem;
  private DriveController m_dc;
  private long m_lastUpdate;
  private final int MOTOR_UPDATE_FREQUENCY = 10;  // msec
  private SwerveState simMovement;
  private int simControl;
  private int i;
  

  /** Creates a new DriveCommand. */
  public DriveCommand(DriveController dc, DriveSubsystem driveSubsystem) {
    m_dc = dc;
    m_driveSubsystem = driveSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_dc);
    addRequirements(m_driveSubsystem);
    simMovement = new SwerveState(-175, 1.0);
    simControl = 0;
    i=0;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double angle;
    double throttle;
    long currTime;
    Date d = new Date();
    currTime = d.getTime();
    if (DriverStation.isAutonomous()) {
      return;
    }
    if((currTime - m_lastUpdate) >= MOTOR_UPDATE_FREQUENCY) {
      angle = m_dc.getDesiredAngle();
      throttle = m_dc.getDesiredThrottle() * 0.3 * m_dc.getSpeedModifier();
      SmartDashboard.putNumber("DesiredThrottle", throttle);
      simMovement = new SwerveState(angle, throttle);
      SmartDashboard.putNumber("Desired Angle", angle);
      
      if(m_dc.allowedToRotate()) {
        m_driveSubsystem.setRotation(m_dc.getRotation(), 0.3 * m_dc.getSpeedModifier());
      } else {
        m_driveSubsystem.setDesiredSwerveState(simMovement);
      }
      
      m_lastUpdate = currTime;
    }
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
