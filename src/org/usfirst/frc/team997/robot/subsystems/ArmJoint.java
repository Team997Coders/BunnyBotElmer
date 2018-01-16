package org.usfirst.frc.team997.robot.subsystems;

import org.usfirst.frc.team997.robot.Robot;
import org.usfirst.frc.team997.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ArmJoint extends Subsystem {
	
	public TalonSRX Motor;
	public static final double absoluteTolerance = 0.01;
	public boolean isZeroed = false;

    // Initialize your subsystem here
    public ArmJoint() {
    	
    	Motor = new TalonSRX(RobotMap.Ports.bucketLifter);
    	Motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 2);
    	Motor.clearStickyFaults(2);
    	Motor.enableCurrentLimit(false);
    	Motor.configNominalOutputVoltage(0, 0);
    	Motor.configPeakOutputVoltage(12, -12);
    	Motor.setAllowableClosedLoopErr(10);
    	Motor.selectProfileSlot(0, 0);
    	Motor.config_kP(0, RobotMap.Values.armPidP, 2);
    	Motor.config_kI(0, RobotMap.Values.armPidI, 2);
    	Motor.config_kD(0, RobotMap.Values.armPidD, 2);
    	Motor.config_kF(0, 0, 2);

    	//Motor.configEncoderCodesPerRev(1);
    	//Motor.enableLimitSwitch(true, true);
    	//Motor.enableBrakeMode(false);
    	//Motor.enable();
    	//Motor.changeControlMode(TalonControlMode.PercentVbus);
    	Motor.enableZeroSensorPositionOnReverseLimit(true);
    	Motor.set(ControlMode.PercentOutput, 0);
    	
    	LiveWindow.addActuator("ArmJoint", 1, (Sendable) Motor);
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    
    public double AngleToEncoderTick(double angle) {
    	double countPerDegree = 1024 / 360;
    	return countPerDegree * angle;
    }
    
    public void autozero() {
    	if (Motor.isRevLimitSwitchClosed() && !isZeroed) {
    		isZeroed = true;
    		
    	}
    }
    
    public void getPosition(double NewAngle) {
    	double angle = Motor.getEncPosition();
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public void stop() {
    	System.out.println("Stop Arm");
    	Motor.set(ControlMode.PercentOutput, 0.0);
    }
    
    public void setVoltage(double volts) {
    	Motor.set(ControlMode.PercentOutput, volts);
    }
    
    public void setArmSetpoint(double angle) {
    }    
    
    public void updateSmartDashboard() {
    	SmartDashboard.putNumber("TalonSRX Mode", Motor.getControlMode().value);
    	SmartDashboard.putNumber("Arm Voltage", Motor.getMotorOutputVoltage());
    	SmartDashboard.putBoolean("Holo1", Motor.isFwdLimitSwitchClosed());
    	SmartDashboard.putBoolean("Holo2", Motor.isRevLimitSwitchClosed());
    	SmartDashboard.putBoolean("ArmZeroed", Robot.armJoint.isZeroed);
    	SmartDashboard.putNumber("ArmPIDError", Motor.getClosedLoopError(0));
    	SmartDashboard.putNumber("Arm Position ", Motor.getPosition());
    }
}
