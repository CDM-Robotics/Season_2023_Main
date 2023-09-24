package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.NavSubsystem;

public class NavCommand extends CommandBase {
    private NavSubsystem m_NavSubsystem;

    public NavCommand(NavSubsystem nav) {
        m_NavSubsystem = nav;

        addRequirements(m_NavSubsystem);
    }

    @Override
    public void execute() {
        
        SmartDashboard.putNumber("Field Angle", m_NavSubsystem.getFieldAngle());
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
    }
}
