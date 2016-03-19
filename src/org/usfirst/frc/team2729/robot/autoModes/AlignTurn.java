package org.usfirst.frc.team2729.robot.autoModes;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AlignTurn extends Command{
	int _amount;
	double _power;
	double _angle;
	double ANGLE_TICKS_RATIO = 1800/90;
	/**
	 * + is right, - is left
	 */
	public AlignTurn(double speed){
		_amount = 0;
		_power = speed;
	}

	@Override
	protected void interrupted() {end();}
	@Override
	protected void initialize() {
		Robot.driveTrain.resetLeftEnc();
		Robot.driveTrain.resetRightEnc();
		_amount = (int)(Robot.vision.findCrosshairHorizontalAngle(3) * ANGLE_TICKS_RATIO);
	}
	@Override
	protected void execute() {Robot.driveTrain.tankDrive(_power * (_amount>0 ? 1 :-1), _power * (_amount>0 ? 1 : -1));}
	@Override
	protected boolean isFinished() {return Math.abs(Robot.driveTrain.getLeftDistance()) >= Math.abs(_amount);}
	@Override
	protected void end() {Robot.driveTrain.halt();}
}
