package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmJointToAngle extends Command {
	
	double angle;

    public ArmJointToAngle(double _angle) {
        // Use requires() here to declare subsystem dependencies
    	angle = _angle;
        requires(Robot.armJoint);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.armJoint.setArmSetpoint(angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.armJoint.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
