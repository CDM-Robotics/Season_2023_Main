// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.devices;

import java.lang.Math;

/** Add your docs here. */
public class SwerveState {
    public double angle;
    public double velocity;
    public double vx;
    public double vy;

    public SwerveState(double a, double v) {
        angle = a;
        velocity = v;
        vx = velocity * Math.sin(angle / 180.0 * Math.PI);
        vy = velocity * Math.cos(angle / 180.0 * Math.PI);
    }
}
