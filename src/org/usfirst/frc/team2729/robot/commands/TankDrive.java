
package org.usfirst.frc.team2729.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2729.robot.Robot;

public class TankDrive extends Command {

    public TankDrive() {
    	requires(Robot.driveTrain);
    }

    protected void initialize() {
    }

    protected void execute() {
    	double left = Robot.oi.getLeftDrive(),
 			   right = Robot.oi.getRightDrive();
 		Robot.driveTrain.tankDrive(left, right);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.driveTrain.halt();
    }

    protected void interrupted() {
    	Robot.driveTrain.halt();
    }
}
