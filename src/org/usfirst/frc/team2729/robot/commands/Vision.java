package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Vision extends Command {

	public Vision() {
		requires(Robot.vision);
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		Robot.vision.processImage();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
		
	}
}