package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;
import org.usfirst.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PDriveDistance extends Command {
	private double distSetpoint;
	private double minError = 10;
	public Timer timer = new Timer();
	private double lastTime = 0;
	private double lastVoltage = 0;
	private double deltaT = 0;
	private double speed = 0.5;
	private double initYaw = -999;
	private double Ktheta = 0.2;

    public PDriveDistance(double _speed, double _dist) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	distSetpoint = _dist;
    	speed = _speed;
    }
    
    public PDriveDistance(double _dist) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	distSetpoint = _dist;
    	speed = 0.5;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.resetEncoders();
    	initYaw = Robot.driveTrain.ahrs.getFusedHeading();
    	timer.reset();
    	timer.start();
    }
    
    // current algorithm assumes that we are starting
    // from a stop
    private double linearAccel(double input) {
    	double Klin = 1.0;
    	double deltaT = timer.get() - lastTime;
    	lastTime = timer.get();
    	
    	double Volts = Robot.clamp(input, input, lastVoltage + Klin * (deltaT));
    	lastVoltage = Volts;
    	return Volts;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// compute the pid P value
    	double pfactor = Robot.clamp(1, -1, speed * RobotMap.Values.driveDistanceP * piderror());
    	double pfactor2 = linearAccel(pfactor);
    	double deltaTheta = Robot.driveTrain.ahrs.getFusedHeading() - initYaw;
    	deltaT = timer.get() - lastTime;
    	lastTime = timer.get();

    	// calculate yaw correction
    	double yawcorrect = deltaTheta * Ktheta;
    	
    	// set the output voltage
    	Robot.driveTrain.SetVoltages(pfactor2 - yawcorrect, pfactor2 + yawcorrect);

    	// Debug information to be placed on the smart dashboard.
    	SmartDashboard.putNumber("Setpoint", distSetpoint);
    	SmartDashboard.putNumber("Encoder Distance", Robot.driveTrain.getEncoderDistance());
    	SmartDashboard.putNumber("Encoder Rate", Robot.driveTrain.getEncoderRate());
    	SmartDashboard.putNumber("Distance Error", piderror());
    	SmartDashboard.putNumber("K-P factor", pfactor);
    	SmartDashboard.putNumber("K-P factor w/ Accel", pfactor2);
    	SmartDashboard.putNumber("deltaT", deltaT);
    	SmartDashboard.putNumber("Theta Correction", yawcorrect);
    	SmartDashboard.putBoolean("On Target", onTarget());
    }

    private double piderror() {
    	return distSetpoint - Robot.driveTrain.getEncoderDistance();
    }
    
    private boolean onTarget() {
    	return piderror() < minError;
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	timer.stop();
    	Robot.driveTrain.StopVoltage();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}