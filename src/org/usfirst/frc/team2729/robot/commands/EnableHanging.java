package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class EnableHanging extends Command {

	@Override
	protected void initialize() {}

	@Override
	protected void execute() {Robot.hang.setEnabled(true);}

	@Override
	protected boolean isFinished() {return false;}
	
	@Override
	protected void end() {Robot.hang.setEnabled(false);}
	@Override
	protected void interrupted() {Robot.hang.setEnabled(false);}
}
