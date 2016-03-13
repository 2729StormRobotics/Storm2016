package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Winch extends Command{
	private final double EXTEND_POWER = .75;

	private boolean winch;
	public Winch(boolean _winch){
		winch = _winch;
		requires(Robot.hang);
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
		Robot.hang.setWinch(EXTEND_POWER * (winch ? 1 : -1));
	}


	@Override
	protected boolean isFinished() {

		return false;
	}

	@Override
	protected void end() {
		Robot.hang.setWinch(0);
	}

	@Override
	protected void interrupted() {
		Robot.hang.setWinch(0);

	}

}
