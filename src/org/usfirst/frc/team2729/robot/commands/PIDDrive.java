package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDDrive extends Command{

	double _power;
	boolean _accelerate;
	double accelFactor = 0;
	
	public PIDDrive(double power, boolean accelerate){
		requires(Robot.driveTrain);
		_power = power;
		_accelerate = accelerate;
	}

	@Override
	protected void initialize() {
		Robot.driveTrain.resetLeftEnc();
		Robot.driveTrain.resetRightEnc();
		accelFactor = _accelerate ? 1 : 0;
	}

	@Override
	protected void execute() {
		accelFactor = (accelFactor >= 1 ? 1 : accelFactor + .04); //20ms tics of execute. should reach 1 in 500ms
		double gain = SmartDashboard.getNumber("Encoder feedback gain", 0.05),
				err  = Robot.driveTrain.getRightDistance() - Robot.driveTrain.getLeftDistance(),
				diff = err*gain,
				left = (_power * accelFactor) + diff/2,
				right= (_power * accelFactor) - diff/2;
		if(right < -1){
			left -= right+1;
			right = -1;
		}else if(right > 1){
			left -= right-1;
			right = 1;
		}
		if(left <-1){
			right -= left+1;
			left = -1;
		}else if(left>1){
			right -= left-1;
			left = 1;
		}
		Robot.driveTrain.tankDrive(Math.max(-1, Math.min(1, left)),
				Math.max(-1, Math.min(1, right)));
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.driveTrain.tankDrive(0, 0);
	}

	@Override
	protected void interrupted() {
		Robot.driveTrain.tankDrive(0, 0);
	}
}