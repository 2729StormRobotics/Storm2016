package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShooterSpinDown extends Command {
	private double _shoot;

	public ShooterSpinDown(){
	}
	
	@Override
	protected void initialize() {
		Robot.shoot.setTargetSpeed(0);
	}

	protected void execute() {
		
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
