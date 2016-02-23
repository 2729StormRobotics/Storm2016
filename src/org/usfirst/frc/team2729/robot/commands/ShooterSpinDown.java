package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShooterSpinDown extends Command {
	public ShooterSpinDown(){}
	
	@Override
	protected void initialize() {}

	protected void execute() {Robot.shoot.setTargetSpeed(0); Robot.shoot.haltSpin();}	
	@Override
	protected boolean isFinished() {
		return true;
	}
	@Override
	protected void end() {}
	@Override
	protected void interrupted() {}
}
