package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ActuatePTO extends Command{
	private boolean _enabled;
	public ActuatePTO(boolean enabled){
		requires(Robot.driveTrain);
		requires(Robot.hang);
		_enabled = enabled;
	}
	
	@Override
	protected void initialize() {
		Robot.driveTrain.setPTO(_enabled);
	}
	@Override
	protected void execute() {}
	@Override
	protected boolean isFinished() {return true;}
	@Override
	protected void end() {}
	@Override
	protected void interrupted() {}
}
