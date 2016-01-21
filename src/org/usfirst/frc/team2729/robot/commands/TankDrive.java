
package org.usfirst.frc.team2729.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2729.robot.Robot;

public class TankDrive extends Command {

    public TankDrive() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double left = Robot.oi.getLeftDrive(),
 			   right = Robot.oi.getRightDrive();
 		Robot.driveTrain.TankDrive(left, right);
    }

    // Make this return true when this Command no longer needs to run execute
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.driveTrain.TankDrive(0, 0);
    }

    protected void interrupted() {
    	Robot.driveTrain.TankDrive(0, 0);
    }
}
