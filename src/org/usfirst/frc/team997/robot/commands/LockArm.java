package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LockArm extends Command {

	public double position;

	public LockArm() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.armJoint);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		position = Robot.armJoint.Motor.getSelectedSensorPosition(0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.armJoint.lock(position);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		int closedLoopError = Robot.armJoint.Motor.getClosedLoopError(0);
		return !Robot.armJoint.isZeroed || (Math.abs(closedLoopError) < 60);
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}