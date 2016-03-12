package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeTilt extends Command {
	private final boolean _top;
	
	public IntakeTilt(boolean top) {
    	requires(Robot.intake);
        _top = top;
    }
	
	@Override
	protected void initialize() {Robot.intake.setIntakeTilt(_top);}

	@Override
	protected void execute() {}
	@Override
	protected boolean isFinished() {return true;}
	@Override
	protected void end() {}
	@Override
	protected void interrupted() {}

}
