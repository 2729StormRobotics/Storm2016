package org.usfirst.frc.team2729.robot.autoModes;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

public class PIDDriveAuto extends Command{

	double _power;
	double _time;
	Timer _timer;
	
	public PIDDriveAuto(double power, double time){
		_power = power;
		_time = time;
		requires(Robot.driveTrain);
	}
	
	@Override
	protected void initialize() {
		Robot.driveTrain.resetLeftEnc();
		Robot.driveTrain.resetRightEnc();
		_timer.start();
	}

	@Override
	protected void execute() {
		double gain = SmartDashboard.getNumber("Encoder feedback gain", 0.05),
			   err  = Robot.driveTrain.getRightDistance() - Robot.driveTrain.getLeftDistance(),
			   diff = err*gain,
			   left = _power + diff/2,
			   right= _power - diff/2;
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
		Robot.driveTrain.TankDrive(Math.max(-1, Math.min(1, left)), 
								-Math.max(-1, Math.min(1, right)));
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
		Robot.driveTrain.TankDrive(0, 0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}