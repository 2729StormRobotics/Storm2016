package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShooterSpin extends Command {
	
	private double _shoot;

	public ShooterSpin(double shoot){
		_shoot = shoot;
	}
	
	@Override
	protected void initialize() {}

	protected void execute() {
		//TODO: Check that value below
		
		Robot.shoot.setLeftPower(_shoot);
		Robot.shoot.setRightPower(_shoot);
		Robot.shoot.setInktakePower(_shoot);
		
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
		
		Robot.shoot.setLeftPower(0);
		Robot.shoot.setRightPower(0);
		Robot.shoot.setInktakePower(0);
		
	}
	
	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}
}
