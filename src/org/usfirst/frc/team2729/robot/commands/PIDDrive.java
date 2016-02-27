package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDDrive extends Command{

	double _power;
	
	public PIDDrive(double power){
		_power = power;
		requires(Robot.driveTrain);
	}
	
	@Override
	protected void initialize() {
		Robot.driveTrain.resetLeftEnc();
		Robot.driveTrain.resetRightEnc();
	}

	@Override
	protected void execute() {
		double gain = SmartDashboard.getNumber("Encoder feedback gain", 0.05),
			   err  = Robot.driveTrain.getRightDistance() - Robot.driveTrain.getLeftDistance(),
			   diff = err*gain,
			   left = _power + diff/2,
			   right= _power - diff/2;
		double testL = left;
		double testR = right;

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
								-Math.max(-1, Math.min(1, right)));
		
		SmartDashboard.putNumber("PID DRIVE Left TEST val", testL);
		SmartDashboard.putNumber("PID DRIVE Right TEST val", testR);
		SmartDashboard.putNumber("PID DRIVE Left OUTPUT val", left);
		SmartDashboard.putNumber("PID DRIVE Right OUTPUT val", right);
		SmartDashboard.putNumber("PID DRIVE Left Drive", Math.max(-1, Math.min(1, left)));
		SmartDashboard.putNumber("PID DRIVE Right Drive", -Math.max(-1, Math.min(1, right)));
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