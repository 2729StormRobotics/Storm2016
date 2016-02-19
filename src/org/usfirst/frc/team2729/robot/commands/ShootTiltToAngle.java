package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShootTiltToAngle extends Command {
	
	private double target = 0;
	private double setTiltPower;
	public ShootTiltToAngle(double _target){
		target =  _target;
	}
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		double err = target - Robot.shoot.getShooterAngle();
		double Kp = .2;
		setTiltPower = Kp*err;
		Robot.shoot.setTiltPower(setTiltPower);
	}

	@Override
	protected boolean isFinished() {
		return setTiltPower < 0.01;
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