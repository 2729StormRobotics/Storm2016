package org.usfirst.frc.team2729.robot.autoModes;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDDriveAuto extends Command{

	double _power;
	double _time;
	Timer _timer;
	boolean _accelerate = false;
	double accelFactor = 0;

	public PIDDriveAuto(double power, double time, boolean accelerate){
		requires(Robot.driveTrain);
		_timer = new Timer();
		_power = power;
		_time = time;
		_accelerate = accelerate;
	}

	@Override
	protected void initialize() {
		Robot.driveTrain.resetLeftEnc();
		Robot.driveTrain.resetRightEnc();
		_timer.start();
		accelFactor = _accelerate ? 1 : 0;
	}

	@Override
	protected void execute() {
		accelFactor = (accelFactor >= 1 ? 1 : accelFactor + .02); //20ms tics of execute
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
		if (_timer.get() > _time) {
			return true;
		}
		else {return false;}
	}

	@Override
	protected void end() {
		Robot.driveTrain.tankDrive(0, 0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}