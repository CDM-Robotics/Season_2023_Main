// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.devices.SwerveAssembly;
import frc.robot.devices.SwerveState;
import frc.robot.exceptions.MotorSetupException;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.HashMap;
import java.util.Iterator;

/** Add your docs here. */
public class DriveSubsystem extends SubsystemBase {
    
    private HashMap<String, SwerveAssembly> m_assemblies;
    public DrivePhysics m_physics;
    

    public DriveSubsystem(HashMap<String, SwerveAssembly> assemblies, DrivePhysics physics) {
        m_assemblies = assemblies;
        m_physics = physics;
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    public Boolean initialize() {
        try {
            Iterator<SwerveAssembly> iter = m_assemblies.values().iterator();

            SwerveAssembly sa;
            while(iter.hasNext()) {
                sa = iter.next();
                sa.initialize();
            }
        } catch(MotorSetupException mse) {
            System.out.println(mse.getMessage());
            return false;
        }

        return true;
    }

    public void setDesiredSwerveState(SwerveState state) {
        SwerveState allowed = m_physics.askPermissionToMove(state);

        SwerveAssembly s;
        Iterator<SwerveAssembly> iter = m_assemblies.values().iterator();
        while(iter.hasNext()) {
            s = iter.next();
            s.setState(allowed);
        }
    }

    public void setRotation(double rotation) {
        double pureRotationSpeed = 0.5;

        SwerveAssembly fl = m_assemblies.get("FrontLeft");
        SwerveAssembly fr = m_assemblies.get("FrontRight");
        SwerveAssembly rl = m_assemblies.get("RearLeft");
        SwerveAssembly rr = m_assemblies.get("RearRight");

        if(rotation < -0.05) {  // Counter clockwise
            fl.setState(new SwerveState(-135.0, pureRotationSpeed));
            fr.setState(new SwerveState(-45.0, pureRotationSpeed));
            rl.setState(new SwerveState(135.0, pureRotationSpeed));
            rr.setState(new SwerveState(45.0, pureRotationSpeed));
        } else if(rotation > 0.05) { // Clockwise
            fl.setState(new SwerveState(45.0, pureRotationSpeed));
            fr.setState(new SwerveState(135.0, pureRotationSpeed));
            rl.setState(new SwerveState(-45.0, pureRotationSpeed));
            rr.setState(new SwerveState(-135.0, pureRotationSpeed));
        } else {  // Go back to zero
            setDesiredSwerveState(new SwerveState(0.0, 0.0));
        }
    }
}
