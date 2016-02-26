package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShooterSetTilt extends Command{
	private double _target;
	
	public ShooterSetTilt(double target){
		_target = target;
	}
	@Override
	protected void initialize() {}
	@Override
	protected void execute() {Robot.shoot.setTargetTilt(_target);}
	@Override
	protected boolean isFinished() {return true;}
	@Override
	protected void end() {}
	@Override
	protected void interrupted() {}
}
