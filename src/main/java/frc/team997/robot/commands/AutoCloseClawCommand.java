package main.java.frc.team997.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import main.java.frc.team997.robot.Robot;

/**
 *
 */
public class AutoCloseClawCommand extends Command {

    public AutoCloseClawCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.claw.closeClaw();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
