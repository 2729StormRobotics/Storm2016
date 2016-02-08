package org.usfirst.frc.team2729.robot.autoModes;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2729.robot.Robot;


public class BreachDefenseAuto extends Command{

	public final int _defense;
	public final double _distance;
	public final double _power;
	
	public BreachDefenseAuto(int defense, double distance, double power){
		requires(Robot.driveTrain);
		_defense = defense;
		_distance = distance;
		_power = power;	
	}

	@Override
	protected void end() {
		Robot.driveTrain.TankDrive(0, 0);
		
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
	protected void initialize() {
		Robot.driveTrain.resetLeftEnc();
		Robot.driveTrain.resetRightEnc();
	}

	@Override
	protected void interrupted() {
		end();
		
	}

	@Override
	protected boolean isFinished() {
		double left = Robot.driveTrain.getLeftDistance();
		double right = Robot.driveTrain.getRightDistance();
		double distance = Math.abs(left) > Math.abs(right) ? left : right;
		
		return distance == _distance;
	}
}


