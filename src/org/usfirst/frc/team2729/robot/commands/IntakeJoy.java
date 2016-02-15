package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeJoy extends Command {
	
	public IntakeJoy(){
		requires(Robot.intake);
	}
	
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.intake.intakeDrive(Robot.oi.getIntake());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.intake.intakeDrive(0);
	}

	@Override
	protected void interrupted() {
		Robot.intake.intakeDrive(0);
	}
}
