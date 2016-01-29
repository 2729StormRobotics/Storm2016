package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShooterSpin extends Command {
	
	private double _shoot;

	public ShooterSpin(double shoot){
		_shoot = shoot;
		requires(Robot.driveTrain);
	}
	
	@Override
	protected void initialize() {}

	protected void execute() {
		Robot.shoot.setLeftPower(_shoot);
		Robot.shoot.setRightPower(_shoot);
		Robot.shoot.setInktakePower(_shoot);
		
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
		Robot.shoot.setLeftPower(0);
		Robot.shoot.setRightPower(0);
		Robot.shoot.setInktakePower(0);
	}

	@Override
	protected void interrupted() {
		Robot.shoot.setLeftPower(0);
		Robot.shoot.setRightPower(0);
		Robot.shoot.setInktakePower(0);
	}
}
