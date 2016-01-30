package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShooterSpinUp extends Command {
	private int TARGET_SPEED = 100;

	public ShooterSpinUp(){
		Robot.shoot.setTargetSpeed(TARGET_SPEED);
	}
	
	@Override
	protected void initialize() {}

	protected void execute() {}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {}

	@Override
	protected void interrupted() {}
}
