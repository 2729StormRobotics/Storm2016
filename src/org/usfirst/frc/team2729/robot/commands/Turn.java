package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turn extends Command{
	int _amount;
	double _power;
	double _angle;
	double ANGLE_TICKS_RATIO = 1800/90;
	/**
	 * + is right, - is left
	 */
	public Turn(int amount){
		_amount = amount;
		_power = 0.40;
	}
	public Turn(int amount, double speed){
		_amount = amount;
		_power = speed;
	}
	public Turn(double angle, double power){
		_amount = (int) (angle * ANGLE_TICKS_RATIO);
		_power = power;
		SmartDashboard.putNumber("AUTO ANGLE", angle);
	}
	@Override
	protected void interrupted() {end();}
	@Override
	protected void initialize() {
		Robot.driveTrain.resetLeftEnc();
		Robot.driveTrain.resetRightEnc();
	}
	@Override
	protected void execute() {Robot.driveTrain.tankDrive(_power * (_amount>0 ? 1 :-1), _power * (_amount>0 ? 1 : -1));}
	@Override
	protected boolean isFinished() {return Math.abs(Robot.driveTrain.getLeftDistance()) >= Math.abs(_amount);}
	@Override
	protected void end() {Robot.driveTrain.halt();}
}
