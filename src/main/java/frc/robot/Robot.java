// RobotBuilder Version: 5.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: Robot.

package frc.robot;

import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.platform.can.AutocacheState;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.devices.ArmEncoder;
import frc.robot.devices.SwerveState;
import frc.robot.devices.SwingArmMotor;
//import frc.robot.subsystems.MotorSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in 
 * the project.
 */
public class Robot extends TimedRobot {

    private Command m_autonomousCommand;
    private RobotContainer m_robotContainer;
    private int autoCount;
    private int autoDriveCount;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        // autonomous chooser on the dashboard.
        m_robotContainer = RobotContainer.getInstance();
        //m_robotContainer.initializeMotorSubsystem();
        //m_robotContainer.initializeDriveSubsystem();
        autoCount = 0;
        autoDriveCount = 685;
        SmartDashboard.putNumber("Balance Count", 485);
        SwingArmMotor.getInstance();
        
        HAL.report(tResourceType.kResourceType_Framework, tInstances.kFramework_RobotBuilder);
    }

    /**
    * This function is called every robot packet, no matter the mode. Use this for items like
    * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
    *
    * <p>This runs after the mode specific periodic functions, but before
    * LiveWindow and SmartDashboard integrated updating.
    */
    @Override
    public void robotPeriodic() {
        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods.  This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();
    }


    /**
    * This function is called once each time the robot enters Disabled mode.
    */
    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    /**
    * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
    */
    @Override
    public void autonomousInit() {
        m_autonomousCommand = m_robotContainer.getAutonomousCommand();
        autoDriveCount = (int)Math.round(SmartDashboard.getNumber("AutoDriveCount", 685));

        // schedule the autonomous command (example)
        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }
        m_robotContainer.initalizeAutoDriveSys();
    }

    /**
    * This function is called periodically during autonomous.
    */
    @Override
    public void autonomousPeriodic() 
    {
        int returnCount = 300;
        int closeTime = 150;
        int moveCount = 301;
        //int taxiCount = 685;
        //int balanceCount = 555;
        autoCount++;

        SmartDashboard.putNumber("Auto Counter", autoCount);
        if (autoCount >= 0 && autoCount <= 225) 
        {
           if (m_robotContainer.m_ae.m_pos >= 90.00) 
           {
                SwingArmMotor.getInstance().autoPosition();
           }
        }
        if (autoCount >= 225 && autoCount <= returnCount) 
        {
            m_robotContainer.m_Servo.setNewPosition(ServoEnum.OPEN);
        }
        if (autoCount >= returnCount && autoCount <= returnCount + 250) 
        {
            if (m_robotContainer.m_ae.m_pos <= 300.00) 
            {
                SwingArmMotor.getInstance().teleopReturn();
            }
        }
        if (autoCount >= returnCount && autoCount <= returnCount + closeTime) 
        {
            m_robotContainer.m_Servo.setNewPosition(ServoEnum.CLOSE);
        }
        if (autoCount >= moveCount && autoCount < autoDriveCount /*balanceCount*/ /*taxiCount*/)
        {
            m_robotContainer.m_DriveSubsystem.setDesiredSwerveState(new SwerveState(0.0, -0.7));
            //m_robotContainer.m_DriveSubsystem.moveSteps(1);
        }
        if (autoCount >= autoDriveCount /*balanceCount*/ /*taxiCount*/) 
        {
            m_robotContainer.m_DriveSubsystem.setDesiredSwerveState(new SwerveState(0.0, 0.0));
        }
        if (autoCount > returnCount + closeTime)
        {
            m_robotContainer.m_Servo.setNewPosition(ServoEnum.STOP);
        }
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }
        //SwingArmMotor.getInstance().teleopReturn();
        m_robotContainer.initializeDriveSubsystem();
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /**
    * This function is called periodically during test mode.
    */
    @Override
    public void testPeriodic() {
    }

}