package org.usfirst.frc.team2729.robot.autoModes;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class BreachDefenseAuto extends Command{


	public final double _distance;
	public final double _power;
	boolean _accelerate;
	double accelFactor = 0;

	public BreachDefenseAuto(double distance, double power, boolean accelerate){
		requires(Robot.driveTrain);
		_accelerate = accelerate;
		_distance = distance;
		_power = power;
	}
	
	@Override
	protected void initialize() {
		Robot.driveTrain.resetLeftEnc();
		Robot.driveTrain.resetRightEnc();
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
	protected void interrupted() {
		end();

	}

	@Override
	protected void end() {
		Robot.driveTrain.tankDrive(0, 0);

	}
	@Override
	protected boolean isFinished() {
		double left = Robot.driveTrain.getLeftDistance();
		double right = Robot.driveTrain.getRightDistance();
		double distance = Math.abs(left) > Math.abs(right) ? left : right;

		return distance == _distance;
	}
}


