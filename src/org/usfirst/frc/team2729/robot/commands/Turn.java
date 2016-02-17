package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Turn extends Command{
	int _amount;
	
	/**
	 * + is right, - is left
	 */
	public Turn(int amount){
		_amount = amount;
	}
	@Override
	protected boolean isFinished() {return Math.abs(Robot.driveTrain.getLeftDistance()) >= Math.abs(_amount);}
	@Override
	protected void interrupted() {end();}
	@Override
	protected void initialize() {
		Robot.driveTrain.resetLeftEnc();
		Robot.driveTrain.resetRightEnc();
	}
	@Override
	protected void execute() {Robot.driveTrain.TankDrive(0.3 * _amount>0 ? 1 :-1, 0.3 * _amount>0 ? -1 : 1);}
	@Override
	protected void end() {Robot.driveTrain.halt();}
}
