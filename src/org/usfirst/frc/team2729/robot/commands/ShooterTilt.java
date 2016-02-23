package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShooterTilt extends Command{
	
	private double _tilt;
	
	public ShooterTilt(){
		requires(Robot.shoot);
	}

	@Override
	protected void initialize() {}
	
	@Override
	protected void execute() {	
		Robot.shoot.setTiltPower(Robot.oi.getShootTilt());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.shoot.setTiltPower(0);	
	}

	@Override
	protected void interrupted() {
		Robot.shoot.setTiltPower(0);
	}
}