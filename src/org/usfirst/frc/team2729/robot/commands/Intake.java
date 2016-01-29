package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Intake extends Command {
	
	public Intake(){
		requires(Robot.intake);
	}
	
	protected void initialize() {
	
	}

	@Override
	protected void execute() {
		double power = Robot.oi.getIntake();
		Robot.intake.Intake(power);
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
