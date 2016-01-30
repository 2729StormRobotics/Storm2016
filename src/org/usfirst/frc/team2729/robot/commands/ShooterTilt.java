package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShooterTilt extends Command{
	
	private double _tilt;
	
	public ShooterTilt(double tilt){
		
		requires(Robot.shoot);
		
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		
		if (Robot.shoot.isMax() == true){
			Robot.shoot.setTiltPower(0);
			
		} else if (Robot.shoot.isMin() == true){
			Robot.shoot.setTiltPower(0);
			
		} else {
			Robot.shoot.setTiltPower(_tilt);
		}
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		
		Robot.shoot.setTiltPower(0);
		
	}

	@Override
	protected void interrupted() {
		
		Robot.shoot.setTiltPower(0);
		
	}

}
