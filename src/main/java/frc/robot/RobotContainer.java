// RobotBuilder Version: 5.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: RobotContainer.

package frc.robot;

import frc.robot.commands.*;
import frc.robot.devices.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import java.util.HashMap;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private static RobotContainer m_robotContainer = new RobotContainer();

  public ServoSys m_Servo;
  private ClawController m_ClawController;
  private ClawControllerCommand m_CCC;
  private ArmSubsystem m_Arm;
  private ArmController m_ArmController;
  private ArmControllerCommand m_ACC;
  private ArmCommand m_AC;
  public ArmEncoder m_ae;
  private SwingArmMotor m_SwingArmMotor;
  private SwerveAssembly frontLeft;
  private SwerveAssembly frontRight;
  private SwerveAssembly rearLeft;
  private SwerveAssembly rearRight;
  private DriveController m_DriveController;
  public DriveSubsystem m_DriveSubsystem;
  private DrivePhysics m_DrivePhysics;
  private UpdateSwerveStateCommand m_updateSwerveCommand;
  private DriveCommand m_DriveCommand;
  private HashMap<String, SwerveAssembly> swerves;
  private NavX navx;
  private NavSubsystem navSubsystem;
  private NavCommand navCommand;

  private RobotContainer() {

    // Configure the button bindings
    configureButtonBindings();

        m_SwingArmMotor = new SwingArmMotor(14 /*, m_ArmController*/);
        m_ae = new ArmEncoder(27, 0);
        m_ArmController = new ArmController();
        m_Arm = new ArmSubsystem(m_SwingArmMotor, m_ae);
        m_ACC = new ArmControllerCommand(m_ArmController, m_Arm);
        m_ArmController.setDefaultCommand(m_ACC);
        m_AC = new ArmCommand(m_Arm);
        m_Arm.setDefaultCommand(m_AC);

        m_Servo = new ServoSys();
        m_ClawController = new ClawController();
        m_CCC = new ClawControllerCommand(m_ClawController, m_Servo);
        m_ClawController.setDefaultCommand(m_CCC);
        m_Servo.intialize();

        SmartDashboard.setDefaultNumber("AutoDriveCount", 685);

        // Define the 4 Swerve Assemblies
        rearRight = new SwerveAssembly("Rear Right", 18, false, 17, false, 26,-60.9);
        rearLeft = new SwerveAssembly("Rear Left", 20, false, 19, false, 25, -102.75);
        frontRight = new SwerveAssembly("Front Right", 13, false, 15, false, 23, 48.7);
        frontLeft = new SwerveAssembly("Front Left", 22, false, 21, false, 24, -171.21);
        
        
        swerves = new HashMap<String, SwerveAssembly>();
        swerves.put("FrontLeft", frontLeft);
        swerves.put("FrontRight", frontRight);
        swerves.put("RearLeft", rearLeft);
        swerves.put("RearRight", rearRight);

        // Add the Swerve Assemblies to the DrivePhysics Subsystem and set the default (UpdateSwerveState) command
        m_DrivePhysics = new DrivePhysics(frontLeft, frontRight, rearLeft, rearRight);
        m_updateSwerveCommand = new UpdateSwerveStateCommand(m_DrivePhysics);
        m_DrivePhysics.setDefaultCommand(m_updateSwerveCommand);

        m_DriveController = new DriveController();
        m_DriveSubsystem = new DriveSubsystem(swerves, m_DrivePhysics);

        m_DriveCommand = new DriveCommand(m_DriveController, m_DriveSubsystem);
        m_DriveController.setDefaultCommand(m_DriveCommand);

        // Please don't crash
        /*navx = new NavX();
        navSubsystem = new NavSubsystem(navx);
        navCommand = new NavCommand(navSubsystem);
        navSubsystem.setDefaultCommand(navCommand);*/

  }

  public static RobotContainer getInstance() {
    return m_robotContainer;
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=BUTTONS
// Create some buttons


        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=BUTTONS
  }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
  */
  public Command getAutonomousCommand() {
    // The selected command will be run in autonomous
    return null;
  }

  public Boolean initializeDriveSubsystem() {
    //return m_DriveSubsystem.initialize();
    m_DriveSubsystem.setTeleOp();
    return true;
  }
  public Boolean initalizeAutoDriveSys() {
    m_DriveSubsystem.setAuto();
    return true;
  }
}

