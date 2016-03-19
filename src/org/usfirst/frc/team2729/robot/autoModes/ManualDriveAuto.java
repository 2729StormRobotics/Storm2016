package org.usfirst.frc.team2729.robot.autoModes;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ManualDriveAuto extends Command{

	double _power;
	double _time;
	Timer _timer;
	boolean _accelerate = false;
	double accelFactor = 0;

	public ManualDriveAuto(double power, double time, boolean accelerate){
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
		Robot.driveTrain.tankDrive(_power*accelFactor, -_power*accelFactor);
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