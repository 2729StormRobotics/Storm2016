package org.usfirst.frc.team2729.robot.commands;
import org.usfirst.frc.team2729.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ExtendHanging extends Command{
	private final double EXTEND_POWER = .5;
	boolean out;
	public ExtendHanging(boolean _out){
		out = _out;
		requires(Robot.hang);
	}

	@Override
	protected void execute() {
		Robot.hang.setExtendPower(EXTEND_POWER * (out ? -1 : 1)); //Inverted due to motor polarity
	}
	
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