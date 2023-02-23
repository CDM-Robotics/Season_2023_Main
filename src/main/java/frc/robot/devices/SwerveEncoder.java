// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.devices;

import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.CANCoderStatusFrame;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;

/** Add your docs here. */
public class SwerveEncoder extends CANCoder {
    private int myID;
    private double m_offset;

    public SwerveEncoder(int canID, double offset) {
        super(canID);
        myID = canID;
        m_offset = -offset;
    }

    public void initialize() {
        CANCoderConfiguration config = new CANCoderConfiguration();
        config.absoluteSensorRange = AbsoluteSensorRange.Signed_PlusMinus180;
        config.initializationStrategy = SensorInitializationStrategy.BootToAbsolutePosition;
        config.magnetOffsetDegrees = m_offset;
        super.setStatusFramePeriod(CANCoderStatusFrame.SensorData, 10);

        configAllSettings(config);
        
    }

    public int getID() {
        return myID;
    }

}
