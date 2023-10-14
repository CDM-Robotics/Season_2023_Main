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
    private int numWrapCorrections;
    

    public DriveSubsystem(HashMap<String, SwerveAssembly> assemblies, DrivePhysics physics) {
        m_assemblies = assemblies;
        m_physics = physics;
        numWrapCorrections = 0;
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    public void setTeleOp() {
        try {
            Iterator<SwerveAssembly> iter = m_assemblies.values().iterator();

            SwerveAssembly sa;
            while(iter.hasNext()) {
                sa = iter.next();
                sa.initializeTeleOp();
            }
        } catch(MotorSetupException mse) {
            System.out.println(mse.getMessage());
        }
    }

    public void setAuto() {
        try {
            Iterator<SwerveAssembly> iter = m_assemblies.values().iterator();

            SwerveAssembly sa;
            while(iter.hasNext()) {
                sa = iter.next();
                sa.initializeAuto();
            }
        } catch(MotorSetupException mse) {
            System.out.println(mse.getMessage());
        }
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
        boolean pointed = true;
        SwerveAssembly s;
        double a;
        int wraps;
        boolean counted = false;

        SwerveState allowed = m_physics.askPermissionToMove(state);

        // Check the current state, if we have zero momentum, then we're not moving and we want to point
        // the wheels in the correct direction before throttling
        // Double-check if the wheels lost calibration
        Iterator<SwerveAssembly> iter = m_assemblies.values().iterator();

        iter = m_assemblies.values().iterator();

        while(iter.hasNext()) {
            s = iter.next();
            s.setState(allowed);
        }

        return;

        /*if(Math.abs(m_physics.totalLinearMomentum) < 0.1) {
            if(iter.hasNext()) {
                s = iter.next();
                wraps = s.getNumWraps();
                while(iter.hasNext()) {
                    s = iter.next();
                    if(wraps != s.getNumWraps()) {
                        if(!counted) {
                            numWrapCorrections++;
                            counted = true;
                            SmartDashboard.putNumber("Num Wrap Corrections", 1.0 * numWrapCorrections);
                        }
                    }
                    s.resetNumWraps(wraps);
                }
            }

            allowed.velocity = 0.0; */

            //while(iter.hasNext()) {
            //    s = iter.next();
                //a = s.getDriveAngle();
                //if(Math.abs(a - allowed.angle) > 5.0) {
                //    pointed = false;
           //         allowed.velocity = 0.0;
                //    break;
                //}
           // }
        /*}

        //  Now let's set the desired state
        iter = m_assemblies.values().iterator();
        
        while(iter.hasNext()) {
            s = iter.next();
            s.setState(allowed);
        }*/
    }

    public void setRotation(double rotation, double rotationSpeed) {
        SwerveAssembly fl = m_assemblies.get("FrontLeft");
        SwerveAssembly fr = m_assemblies.get("FrontRight");
        SwerveAssembly rl = m_assemblies.get("RearLeft");
        SwerveAssembly rr = m_assemblies.get("RearRight");

        if(rotation < -0.05) {  // Counter clockwise
            fl.setState(new SwerveState(-135.0, rotationSpeed));
            fr.setState(new SwerveState(-45.0, rotationSpeed));
            rl.setState(new SwerveState(135.0, rotationSpeed));
            rr.setState(new SwerveState(45.0, rotationSpeed));
        } else if(rotation > 0.05) { // Clockwise
            fl.setState(new SwerveState(45.0, rotationSpeed));
            fr.setState(new SwerveState(135.0, rotationSpeed));
            rl.setState(new SwerveState(-45.0, rotationSpeed));
            rr.setState(new SwerveState(-135.0, rotationSpeed));
        } else {  // Go back to zero
            setDesiredSwerveState(new SwerveState(0.0, 0.0));
        }
    }
    public boolean autoOnline() 
    {
        return true;
    }

    public void moveSteps(int revolutions) 
    {
        Iterator<SwerveAssembly> iter = m_assemblies.values().iterator();

            SwerveAssembly sa;
            while(iter.hasNext()) {
                sa = iter.next();
                sa.driveSteps(revolutions);
            }
    }

}
