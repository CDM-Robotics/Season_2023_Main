// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.devices;

import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.CANCoderStatusFrame;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmEncoder extends CANCoder {    
    private int myID;
    private int m_offset;
    public double m_pos;

    public ArmEncoder(int canID, int offset) {
        super(canID);
        myID = canID;
        m_offset = -offset;
    }

    public void initialize() {
        CANCoderConfiguration config = new CANCoderConfiguration();
        config.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        config.initializationStrategy = SensorInitializationStrategy.BootToAbsolutePosition;
        config.magnetOffsetDegrees = m_offset;
        super.setStatusFramePeriod(CANCoderStatusFrame.SensorData, 10);

        configAllSettings(config);
    }
    
    public void updatePos() {
        m_pos = getAbsolutePosition();
        if (m_pos < 0) 
        {
            m_pos += 360;
        }
        SmartDashboard.putNumber("Arm Encoder", m_pos);
    }

    public double getPos() {
        return m_pos;
    }

    public int getID() {
        return myID;
    }
}