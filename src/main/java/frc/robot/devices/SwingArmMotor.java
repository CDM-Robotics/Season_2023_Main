// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.devices;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.exceptions.SwingMotorException;
//import frc.robot.subsystems.ArmController;

public class SwingArmMotor extends TalonFX {
    private double m_position;
    //private static ArmController armC;
    
    private static SwingArmMotor sam;

    public static SwingArmMotor getInstance() {
        if (sam == null) {
            sam = new SwingArmMotor(14);
        }
        return sam;
    }

    public SwingArmMotor(int canID) 
    {
        super(canID);
    }

    public void initialize() throws SwingMotorException {
        String errorMsg = "";
        
        if(ErrorCode.OK != configFactoryDefault()) {
            throw new SwingMotorException(errorMsg + "Could not set factory defaults");
        }

        setSensorPhase(true);

        if(ErrorCode.OK != configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30)) {
            throw new SwingMotorException(errorMsg + "Could not set sensor feeback device");
        }
        if(ErrorCode.OK != configNominalOutputForward(0.0, 30)) {
            throw new SwingMotorException(errorMsg + "Could not set nominal output forward");
        }
        if(ErrorCode.OK != configNominalOutputReverse(0.0, 30)) {
            throw new SwingMotorException(errorMsg + "Could not set nominal output reverse");
        }
        if(ErrorCode.OK != configPeakOutputForward(1.0,30)) {
            throw new SwingMotorException(errorMsg + "Could not set peak output forward");
        }
        if(ErrorCode.OK != configPeakOutputReverse(-1.0,30)) {
            throw new SwingMotorException(errorMsg + "Could not set peak output reverse");
        }
        if(ErrorCode.OK != configAllowableClosedloopError(0, 0, 30)) {
            throw new SwingMotorException(errorMsg + "Could not set closed loop error");
        }
        if(ErrorCode.OK != config_kF(0, 0, 30)) {
            throw new SwingMotorException(errorMsg + "Could not set kF");
        }
        if(ErrorCode.OK != config_kP(0, SmartDashboard.getNumber("Velocity: config_kP", 1), 30)) {
            throw new SwingMotorException(errorMsg + "Could not set kP");
        }
        if(ErrorCode.OK != config_kI(0, SmartDashboard.getNumber("Velocity: config_kI", 0), 30)) {
            throw new SwingMotorException(errorMsg + "Could not set kI");
        }
        if(ErrorCode.OK != config_kD(0, SmartDashboard.getNumber("Velocity: config_kD", 4), 30)) {
            throw new SwingMotorException(errorMsg + "Could not set kP");
        }  

        configClosedloopRamp(0.05);

        m_position = getSelectedSensorPosition();
        SmartDashboard.putNumber("ArmPosition", m_position);
    }

    public void setVelocity(int armVelocity) 
    {
        //super.set(TalonFXControlMode.Velocity, armVelocity);
    }

    public void increasePosition() 
    {
        m_position += 200;
        SmartDashboard.putNumber("ArmPosition", m_position);
        super.set(TalonFXControlMode.Position, m_position);
    }

    public void decreasePosition() 
    {
        m_position -= 200;
        SmartDashboard.putNumber("ArmPosition", m_position);
        super.set(TalonFXControlMode.Position, m_position);
    }

    public void autoPosition() 
    {
        m_position += 200;
        SmartDashboard.putNumber("ArmPosition", m_position);
        super.set(TalonFXControlMode.Position, m_position);
    }

    public void teleopReturn() 
    {
        m_position -= 200;
        SmartDashboard.putNumber("ArmPosition", m_position);
        super.set(TalonFXControlMode.Position, m_position);
    }
    
    public void fastIncrease() 
    {
        m_position += 300;
        SmartDashboard.putNumber("ArmPosition", m_position);
        super.set(TalonFXControlMode.Position, m_position);
    } 
    public void fastDecrease() 
    {
        m_position -= 300;
        SmartDashboard.putNumber("ArmPosition", m_position);
        super.set(TalonFXControlMode.Position, m_position);
    }
}