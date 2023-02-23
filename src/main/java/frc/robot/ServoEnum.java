// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public enum ServoEnum
{
    OPEN(2),
    STOP(1),
    CLOSE(0);
    
    private final int value;

    private ServoEnum(final int v) 
    {
        value = v;
    }
    public int getValue() 
    {
        return value;
    }
}