package org.usfirst.frc.team997.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

//This auto routine is for when we are on the left or right starting position and our switch
//is on the opposite side.
public class CrossLine extends CommandGroup {

    public CrossLine() {
    	//addSequential(new PDriveDistance(12 * PDriveDistance.ticksPerFoot));
    	addSequential(new PDriveAngle(-30));
    }
}
