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
import frc.robot.devices.SwerveAssembly;
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

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
// The robot's subsystems
  //private Motor1 theOneAndOnlyMotor1;
  //private Motor2 theOneAndOnlyMotor2;
  private SwerveAssembly frontLeft;
  private SwerveAssembly frontRight;
  private SwerveAssembly rearLeft;
  private SwerveAssembly rearRight;
  //private MotorSubsystem m_motorSubsystem;
  //private TestMotorsCommand m_TestMotorsCommand;
  private DriveController m_DriveController;
  private DriveSubsystem m_DriveSubsystem;
  private DrivePhysics m_DrivePhysics;
  private UpdateSwerveStateCommand m_updateSwerveCommand;
  //private OrientationCommand m_OrientationCommand;
  private DriveCommand m_DriveCommand;
  private HashMap<String, SwerveAssembly> swerves;

// Joysticks

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

  /**
  * The container for the robot.  Contains subsystems, OI devices, and commands.
  */
  private RobotContainer() {

    // Configure the button bindings
    configureButtonBindings();

        SmartDashboard.setDefaultNumber("Velocity: config_kP", .2);
        SmartDashboard.setDefaultNumber("Velocity: config_kI", 0.001);
        SmartDashboard.setDefaultNumber("Velocity: config_kD", 5);

        SmartDashboard.setDefaultNumber("CMD", 0.0);
        SmartDashboard.setDefaultNumber("STAT", 0.0);

        // Define the 4 Swerve Assemblies
        frontLeft = new SwerveAssembly("Front Left", 18, false, 17, false, 26,119.1);
        frontRight = new SwerveAssembly("Front Right", 20, false, 19, false, 25, 77.25);
        rearLeft = new SwerveAssembly("Rear Left", 13, false, 15, false, 23, -131.3);
        rearRight = new SwerveAssembly("Rear Right", 22, false, 21, false, 24, 8.79);
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
    return m_DriveSubsystem.initialize();
  }
  
}
