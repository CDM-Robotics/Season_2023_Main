package frc.robot.devices;

import frc.robot.exceptions.MotorSetupException;

import com.ctre.phoenix.motorcontrol.can.*;


import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class SteeringMotor extends TalonFX {
    private double m_gearRatio;
    private final double UNITS_PER_REV = 2048.0;
    private double simValue;
    private int m_numWraps;
    private double m_currentSensorPos;
    private double m_currentAngle;
    private double m_memorizedCommandAngle;
    private boolean m_sim;
    private int myID;
    private double m_offset;

    public SteeringMotor(int canID, double gearRatio, boolean simulate, double offset) {
        super(canID);
        m_gearRatio = gearRatio;
        simValue = 0.0;
        m_sim = simulate;
        myID = canID;
        m_offset = offset;
    }

    public void initialize() throws MotorSetupException {
        String errorMsg = "(CAN ID: " + this.getDeviceID() + ") ";

        if(m_sim) return;

        if(ErrorCode.OK != configFactoryDefault()) {
            throw new MotorSetupException(errorMsg + "Could not set factory defaults");
        }
    
        if(ErrorCode.OK != configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30)) {
            throw new MotorSetupException(errorMsg + "Could not set sensor feeback device");
        }

        // No error checking for this method
        setSensorPhase(true);

        if(ErrorCode.OK != configNominalOutputForward(0, 30)) {
            throw new MotorSetupException(errorMsg + "Could not set nominal output forward");
        }
        if(ErrorCode.OK != configNominalOutputReverse(0, 30)) {
            throw new MotorSetupException(errorMsg + "Could not set nominal output reverse");
        }
        if(ErrorCode.OK != configPeakOutputForward(0.3,30)) {
            throw new MotorSetupException(errorMsg + "Could not set peak output forward");
        }
        if(ErrorCode.OK != configPeakOutputReverse(-0.3,30)) {
            throw new MotorSetupException(errorMsg + "Could not set peak output reverse");
        }
        if(ErrorCode.OK != configAllowableClosedloopError(0, 0, 30)) {
            throw new MotorSetupException(errorMsg + "Could not set closed loop error");
        }
        if(ErrorCode.OK != config_kF(0, 0, 30)) {
            throw new MotorSetupException(errorMsg + "Could not set kF");
        }
        
        if(ErrorCode.OK != config_kP(0, SmartDashboard.getNumber("Position: config_kP", 1), 30)) {
            throw new MotorSetupException(errorMsg + "Could not set kP");
        }
        if(ErrorCode.OK != config_kI(0, 0, 30)) {
            throw new MotorSetupException(errorMsg + "Could not set kI");
        }
        if(ErrorCode.OK != config_kD(0, SmartDashboard.getNumber("Position: config_kD", 8), 30)) {
            throw new MotorSetupException(errorMsg + "Could not set kP");
        }  

        configClosedloopRamp(0.02);
        //overrideSoftLimitsEnable(true);
        //overrideLimitSwitchesEnable(true);
    }

    public void setOffset(double offset) {
        this.m_offset = offset;
    }

    public void setAngle(double degrees) {
        double steps;
        double gearedSteps;
        double cross;
        double currentPos;
        double wrapSign;
        double wA;
        double wB;
        double change;
        Faults faults;

        //degrees = degrees + m_offset;

        faults = new Faults();

        if(m_numWraps < 0) {
            wrapSign = -1.0;
        } else {
            wrapSign = 1.0;
        }

        // Where we are in wrapped degrees
        wA = wrapSign * (180 + (Math.abs(m_numWraps) - 1) * 360 + (180 + (wrapSign * m_memorizedCommandAngle)));

        // If the desired angle change is greater than 180.0, we need to go the other way
        if((change = Math.abs(degrees - m_memorizedCommandAngle)) > 180.0) {
            if(m_memorizedCommandAngle > 0.0) {
                m_numWraps++;
            } else {
                m_numWraps--;
            }
        }


        if(m_numWraps < 0) {
            wrapSign = -1.0;
        } else {
            wrapSign = 1.0;
        }

        // Where we are going to in wrapped degrees
        wB = wrapSign * (180 + (Math.abs(m_numWraps) - 1) * 360 + (180 + (wrapSign * degrees)));
        
        steps = (wB / 180.0 * (UNITS_PER_REV / 2.0)); 

        // 1 revolution is 2048, but +/- 180 equals +- 1024, so divide by two
        gearedSteps = steps * m_gearRatio;

        if(myID == 16) {
            SmartDashboard.putNumber("gearedSteps", gearedSteps);
            SmartDashboard.putNumber("position", getSelectedSensorPosition());
            SmartDashboard.putNumber("numwraps", m_numWraps);
            this.getFaults(faults);
            if(faults.hasAnyFault()) {
                SmartDashboard.putString("faults", faults.toString());
            }  else {
                SmartDashboard.putString("faults", "none");
            }
        }

        if(RobotBase.isSimulation()|| m_sim) {
            simValue = degrees;
        } else {
            super.set(TalonFXControlMode.Position, gearedSteps);
        }

        if(!m_sim) {
            SmartDashboard.putNumber("CMD", degrees);
        }

        m_memorizedCommandAngle = degrees;
    }

    public double getMemorizedAngle() {
        double value;
        double revs;
        double gearedSteps;
        double steps;
        double degrees;
        double wrappedAngle;
        
        return m_memorizedCommandAngle - m_offset;

        /*if(RobotBase.isSimulation() || m_sim) {
            gearedSteps = simValue;
        } else {
            gearedSteps = super.getSelectedSensorPosition();
        }

        steps = gearedSteps / m_gearRatio;*/

        // Since the current position may not equal the commanded position, it may not have wrapped
        // to the commanded value yet, so we need to use another technique for determining the 
        // current position.  We cannot use the m_numWraps value for reversing the calculation

        /*if(m_numWraps < 0)
            wrappedAngle = Math.ceil(wrappedAngle) % UNITS_PER_REV;
        else if (m_numWraps > 0)
            wrappedAngle = Math.ceil(wrappedAngle) % UNITS_PER_REV;
        else
            wrappedAngle = 
        
        revs = ((((int)value) % ((int)(UNITS_PER_REV* m_gearRatio)))*1.0) / ((UNITS_PER_REV / 2.0));
        gearedRevs = revs / m_gearRatio;

        degrees = gearedRevs * 180.0;

        if(!m_sim) {
            SmartDashboard.putNumber("STAT", degrees);
        }
        
        return degrees;*/
    }
}
