// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.devices;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import frc.robot.exceptions.MotorSetupException;
import edu.wpi.first.networktables.NetworkTableInstance.NetworkMode;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.DriveModeEnum;

/** Add your docs here. */
public class DriveMotor extends TalonFX {

    private double m_gearRatio;
    private double m_wheelDiameterInches;
    private double m_revDistanceInches;
    private double simValue;
    private final double UNITS_PER_REV = 2048.0;
    private boolean m_sim;

    public DriveMotor(int canID, double gearRatio, double wheelDiameterInches, boolean simulate) {
        super(canID);
        m_gearRatio = gearRatio;
        m_wheelDiameterInches = wheelDiameterInches;
        m_revDistanceInches = Math.PI * m_wheelDiameterInches;
        simValue = 0.0;
        m_sim = simulate;
    }

    public void initialize(DriveModeEnum mode) throws MotorSetupException {
        String errorMsg = "(CAN ID: " + this.getDeviceID() + ") ";
        
        if(m_sim) return;

        if(ErrorCode.OK != configFactoryDefault()) {
            throw new MotorSetupException(errorMsg + "Could not set factory defaults");
        }

        configNeutralDeadband(0.001);
    
        if(ErrorCode.OK != configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30)) {
            throw new MotorSetupException(errorMsg + "Could not set sensor feeback device");
        }
        if(ErrorCode.OK != configNominalOutputForward(0.0, 30)) {
            throw new MotorSetupException(errorMsg + "Could not set nominal output forward");
        }
        if(ErrorCode.OK != configNominalOutputReverse(0.0, 30)) {
            throw new MotorSetupException(errorMsg + "Could not set nominal output reverse");
        }
        if(ErrorCode.OK != configPeakOutputForward(1.0,30)) {
            throw new MotorSetupException(errorMsg + "Could not set peak output forward");
        }
        if(ErrorCode.OK != configPeakOutputReverse(-1.0,30)) {
            throw new MotorSetupException(errorMsg + "Could not set peak output reverse");
        }
        if(ErrorCode.OK != configAllowableClosedloopError(0, 0, 30)) {
            throw new MotorSetupException(errorMsg + "Could not set closed loop error");
        }
        
        if (mode == DriveModeEnum.VELOCITY) 
        {
            if(ErrorCode.OK != config_kF(0, 1023.0/20660.0, 30)) {
                throw new MotorSetupException(errorMsg + "Could not set kF");
            }
            if(ErrorCode.OK != config_kP(0, SmartDashboard.getNumber("Velocity: config_kP", .1), 30)) {
                throw new MotorSetupException(errorMsg + "Could not set kP");
            }
            if(ErrorCode.OK != config_kI(0, SmartDashboard.getNumber("Velocity: config_kI", .001), 30)) {
                throw new MotorSetupException(errorMsg + "Could not set kI");
            }
            if(ErrorCode.OK != config_kD(0, SmartDashboard.getNumber("Velocity: config_kD", 5), 30)) {
                throw new MotorSetupException(errorMsg + "Could not set kP");
            }  
        } else {
            if(ErrorCode.OK != config_kF(0, 1023.0/20660.0, 30)) {
                throw new MotorSetupException(errorMsg + "Could not set kF");
            }
            if(ErrorCode.OK != config_kP(0, .1, 30)) {
                throw new MotorSetupException(errorMsg + "Could not set kP");
            }
            if(ErrorCode.OK != config_kI(0, 0.001, 30)) {
                throw new MotorSetupException(errorMsg + "Could not set kI");
            }
            if(ErrorCode.OK != config_kD(0, 5, 30)) {
                throw new MotorSetupException(errorMsg + "Could not set kP");
            }
        }
        configClosedloopRamp(0.02);
        setNeutralMode(NeutralMode.Brake);
        //setNeutralMode(NeutralMode.Coast);
    }

    public void setVelocity(double metersPerSec) {
        double revsPerSec;
        double gearedRPS;
        double gearedPer100ms;

        // Convert to inches, then divide by the wheel circumference
        revsPerSec = metersPerSec / 0.0254 / m_revDistanceInches;

        // Apply the gear Ratio
        gearedRPS = revsPerSec * m_gearRatio;

        gearedPer100ms = gearedRPS * 0.1;  // .1s = 100ms

        // Convert to Talon units per rev
        if(RobotBase.isSimulation() || m_sim) {
            simValue = gearedPer100ms * UNITS_PER_REV;
        } else {
            SmartDashboard.putNumber("Motor2 Set Velocity", gearedPer100ms * UNITS_PER_REV);
            super.set(TalonFXControlMode.Velocity, gearedPer100ms * UNITS_PER_REV);
        }
    }

    public void setPosition(int revolutions) {
        //super.set(TalonFXControlMode.Velocity, revolutions * UNITS_PER_REV);
    }

    public double getVelocity() {
        double value;
        double revsPerSec;
        double gearedRPS;
        double metersPerSec;
        
        if(RobotBase.isSimulation() || m_sim) {
            value = simValue;
        } else {
            value = super.getSelectedSensorVelocity();
            SmartDashboard.putNumber("Motor2 Get Velocity", value);
        }

        gearedRPS = (value / 0.1) / UNITS_PER_REV;
        revsPerSec = gearedRPS / m_gearRatio;
        metersPerSec = revsPerSec * 0.0254 * m_revDistanceInches;
        
        
        return metersPerSec;
    }

}
