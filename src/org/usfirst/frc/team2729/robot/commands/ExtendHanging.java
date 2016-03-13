package org.usfirst.frc.team2729.robot.commands;
import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ExtendHanging extends Command{

	public ExtendHanging() {
		requires(Robot.hang);
	}

	@Override
	protected void execute() {
		Robot.hang.setExtendPower(Robot.hang.isEnabled() ? Robot.oi.getHangExt() : 0); //Inverted due to motor polarity
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.hang.setExtendPower(0);
	}

	@Override
	protected void interrupted() {
		Robot.hang.setExtendPower(0);
	}
}