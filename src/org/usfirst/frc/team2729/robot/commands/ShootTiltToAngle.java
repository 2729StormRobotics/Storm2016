package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShootTiltToAngle extends Command {
	
	private double target = 0;
	public ShootTiltToAngle(double _target){
		target =  _target;
	}
	@Override
	protected void initialize() {
	
		
	}

	@Override
	protected void execute() {
		double err = target - Robot.shoot.getShooterAngle();
		double Kp = 0.001;
		double output = Kp*err;
		Robot.shoot.setTiltPower(output);
		
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
//getHooterAngle
//ouput=err*kp=0.001
//