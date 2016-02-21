package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShooterSpinUp extends Command {
	private int DEFAULT_TARGET_SPEED = 22500;
	private double _target;
	public ShooterSpinUp(){
		_target = DEFAULT_TARGET_SPEED;
	}
	public ShooterSpinUp(double targetSpeed){
		_target = targetSpeed;
	}
	
	@Override
	protected void initialize() {}
	protected void execute() {Robot.shoot.setTargetSpeed(_target);}
	@Override
	protected boolean isFinished() {
		return true;
	}
	@Override
	protected void end() {}
	@Override
	protected void interrupted() {}
}