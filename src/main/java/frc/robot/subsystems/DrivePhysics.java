// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.devices.SwerveAssembly;
import frc.robot.devices.SwerveState;
import java.lang.Math;

public class DrivePhysics extends SubsystemBase {
  private final double ROBOT_MASS = 56.7; // 56.7 kilograms = 125 lbs

  private SwerveAssembly m_frontLeft;
  private SwerveAssembly m_frontRight;
  private SwerveAssembly m_rearLeft;
  private SwerveAssembly m_rearRight;
  private double m_centerOfMassHeight;
  private double m_centerOfMassToWorstCaseTipAxis;
  private double m_weightInNewtons;

  public double totalLinearMomentum;
  public double totalVelocity;
  public double directionOfTravel;
  private double overTurningMoment;

  /** Creates a new DrivePhysics. */
  public DrivePhysics(SwerveAssembly frontLeft, SwerveAssembly frontRight, SwerveAssembly rearLeft, SwerveAssembly rearRight) {
    m_frontLeft = frontLeft;
    m_frontRight = frontRight;
    m_rearLeft = rearLeft;
    m_rearRight = rearRight;
    totalLinearMomentum = 0.0;
    totalVelocity = 0.0;
    directionOfTravel = 0.0;

    m_centerOfMassHeight = 16 * 0.0254;               // 16 inches in meters
    m_centerOfMassToWorstCaseTipAxis = 12 * 0.0254;   // 12 inches in meters
    m_weightInNewtons = ROBOT_MASS * 9.81;
    overTurningMoment = m_weightInNewtons * m_centerOfMassToWorstCaseTipAxis;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void updateState() {
    SwerveState flState, frState, rlState, rrState;

    if(m_frontLeft != null) {
      flState = m_frontLeft.getState();
    } else {
      flState = new SwerveState(0.0, 0.0);
    }

    if(m_frontRight != null) {
      frState = m_frontRight.getState();
    } else {
      frState = new SwerveState(0.0, 0.0);
    }

    if(m_rearLeft != null) {
      rlState = m_rearLeft.getState();
    } else {
      rlState = new SwerveState(0.0, 0.0);
    }

    if(m_rearRight != null) {
      rrState = m_rearRight.getState();
    } else {
      rrState = new SwerveState(0.0, 0.0);
    }

    estimateRobotMomentum(flState, frState, rlState, rrState);
  }

  private void estimateRobotMomentum(SwerveState fl, SwerveState fr, SwerveState rl, SwerveState rr) {
    double rvx, rvy;

    // Determine the XY vector components for each wheel's direction-of-travel

    // The velocity components at the center of the base will be the Mean of the
    // respective velocity components
    rvx = (fl.vx + fr.vx + rl.vx +rr.vx) / 4.0;
    rvy = (fl.vy + fr.vy + rl.vy +rr.vy) / 4.0;

    totalVelocity = Math.sqrt(Math.pow(rvx, 2) + Math.pow(rvy, 2));

    SmartDashboard.putNumber("Physics: totalVelocity", totalVelocity);

    // Therefore, the total magnitude of the momentum is simply the total velocity * mass of the robot
    totalLinearMomentum = ROBOT_MASS * totalVelocity;

    // And now... find the robots direction of travel???
    if((totalVelocity >= 0.01) || (totalVelocity <= -0.01)) {
      // totalVelocity is only the magnitude, it has no direction
      // even though rvy may be negative, acos only returns values between 0 and PI,
      // so we have to check if we are on the negative x-axis, if so the directionOfTravel
      // needs to be multiplied by -1
      directionOfTravel = Math.acos(rvy / totalVelocity) / Math.PI * 180.0;
      if(rvx < 0.0) {
        directionOfTravel = -directionOfTravel;
      }
    } else {
      directionOfTravel = 0.0;
    }
  }

  public SwerveState askPermissionToMove(SwerveState ask) {
    double rmx, rmy;
    double dvx, dvy;
    double cvx, cvy;
    double desiredAppliedForce;
    double allowedAppliedForce;
    double allowedMomentumChange;
    double allowedVelocityChange;
    double allowedVx;
    double allowedVy; 
    double permittedVelocity;
    double permittedAngle;

    // Sanity check the input and see if the velocity exceeds the Max Allowed
    if(Math.abs(ask.velocity) > Constants.MAX_VELOCITY) {
      if(ask.velocity > 0.0) {                                // BAD ROBOT!!!
        ask.velocity = Constants.MAX_VELOCITY;  
      } else {
        ask.velocity = -Constants.MAX_VELOCITY;
      }
    }

    // Find the individual components of the current momentum in X and Y
    rmx = totalLinearMomentum * Math.sin(directionOfTravel / 180.0 * 3.1415);
    rmy = totalLinearMomentum * Math.cos(directionOfTravel / 180.0 * 3.1415);

    // Given the desired direction of travel, find the max velocity change in that direction
    cvx = rmx / ROBOT_MASS;
    cvy = rmy / ROBOT_MASS;
    dvx = ask.vx - cvx;
    dvy = ask.vy - cvy;

    // Desired applied force at CoM
    desiredAppliedForce = Math.sqrt(Math.pow(dvx, 2) + Math.pow(dvy, 2)) * ROBOT_MASS / (Constants.MAX_TALON_CMD_RATE_MSEC / 1000.0);
    
    if(true) {
      return ask;
    }
    if(desiredAppliedForce * m_centerOfMassHeight * Constants.MARGIN_OF_SAFETY <= overTurningMoment) {
      return ask; // And ye shall receive
    }
    
    // Sadly, you're too greedy.  Let me put you in your place
    allowedAppliedForce = overTurningMoment / Constants.MARGIN_OF_SAFETY / m_centerOfMassHeight;
    allowedMomentumChange = allowedAppliedForce * (Constants.MAX_TALON_CMD_RATE_MSEC / 1000.0);
    allowedVelocityChange = allowedMomentumChange / ROBOT_MASS;

    // The new velocity components are the current minus the allowed
    allowedVx = totalVelocity * Math.sin(directionOfTravel / 180.0 * 3.1415) + allowedVelocityChange * dvx / Math.sqrt(Math.pow(dvx,2) + Math.pow(dvy,2));
    allowedVy = totalVelocity * Math.cos(directionOfTravel / 180.0 * 3.1415) + allowedVelocityChange * dvy / Math.sqrt(Math.pow(dvx,2) + Math.pow(dvy,2));
    permittedVelocity = Math.sqrt(Math.pow(allowedVx, 2) + Math.pow(allowedVy, 2));
    permittedAngle = (Math.atan2(allowedVx, allowedVy)) / Math.PI * 180.0;

    return new SwerveState(permittedAngle, permittedVelocity);
  }

  public SwerveState getCurrentSwerveState() {
    return new SwerveState(directionOfTravel, totalVelocity);
  }
}
