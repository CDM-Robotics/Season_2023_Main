// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.devices;

import javax.xml.stream.events.EndDocument;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.exceptions.MotorSetupException;

/** Add your docs here. */
public class SwerveAssembly {

    public final double STEERING_RATIO = 150.0 / 7.1;
    public final double DRIVE_RATIO = 6.75;
    int encoderCtr;
    //public final double STEERING_RATIO = 1.0;
    //public final double DRIVE_RATIO = 1.0;

    private SteeringMotor m_steeringMotor;
    private DriveMotor m_driveMotor;
    private SwerveEncoder m_driveEncoder;
    private double m_offset;
    private String m_prettyName;

    public SwerveAssembly(String pretty, int steeringID, boolean steeringInverted, int driveID, boolean driveInverted, int encoderID, double encoderOffset) {
        if(steeringID == 16) 
            m_steeringMotor = new SteeringMotor(steeringID, STEERING_RATIO, false, encoderOffset);
        else
            m_steeringMotor = new SteeringMotor(steeringID, STEERING_RATIO, false, encoderOffset);

        if(driveID == 16)
            m_driveMotor = new DriveMotor(driveID, DRIVE_RATIO, 4.125, false);
        else
            m_driveMotor = new DriveMotor(driveID, DRIVE_RATIO, 4.125, false);

        m_prettyName = pretty;
        m_steeringMotor.setInverted(steeringInverted);
        m_driveMotor.setInverted(driveInverted);
        m_driveEncoder = new SwerveEncoder(encoderID, encoderOffset);
        m_offset = encoderOffset;
        m_driveEncoder.setPosition(encoderOffset);
        encoderCtr = 0;
    }

    public void initialize() throws MotorSetupException {
        m_driveEncoder.initialize();
        m_steeringMotor.initialize();
        m_driveMotor.initialize();
        m_steeringMotor.setAngle(0.0);
        m_driveMotor.setVelocity(0.0);
    }

    public void setState(SwerveState s) {
        m_steeringMotor.setAngle(s.angle);
        m_driveMotor.setVelocity(s.velocity);
    }

    public SwerveState getState() {
        double angle;
        double velocity;
        double position;

        // We'll base the angle on the last commanded value and not the actual position
        // This is because it may take time for the motor to pass the discontinuity +/-180.0
        angle = m_steeringMotor.getMemorizedAngle();
        velocity = m_driveMotor.getVelocity();

        // Periodically post the encoder position for debugging
        encoderCtr++;

        // This should never rollover, but let's just make certain
        if(encoderCtr == Integer.MAX_VALUE)
            encoderCtr=0;

        if(encoderCtr%10 == 0) {
            // Update the offset estimate
            //position = m_driveEncoder.getAbsolutePosition();
            //position = 0.0;
            //m_steeringMotor.setOffset(position - m_offset);
            SmartDashboard.putNumber("Encoder ID (" + getPrettyName() + ", " + m_driveEncoder.getID() + "):", m_driveEncoder.getAbsolutePosition());
        }


        return new SwerveState(angle, velocity);
    }

    public String getPrettyName() {
        return m_prettyName;
    }
}
