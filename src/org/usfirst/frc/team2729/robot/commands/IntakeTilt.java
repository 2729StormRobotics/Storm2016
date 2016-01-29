package org.usfirst.frc.team2729.robot.commands;


import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;

public class IntakeTilt extends Command{
	
	public IntakeTilt(){
		requires(Robot.intake);
	}
	
	@Override
	protected void initialize() {
	
	}

	@Override
	protected void execute() {
		
		double tiltPower = Robot.oi.getIntakeTilt();
		
		if (Robot.intake.isMax() == true && tiltPower>0){
			Robot.intake.IntakeTilt(0);
			
		} else if (Robot.intake.isMin() == true && tiltPower<0){
			Robot.intake.IntakeTilt(0);
			
		} else {
			Robot.intake.IntakeTilt(tiltPower);
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
		
	}

	@Override
	protected void end() {
		Robot.intake.IntakeTilt(0);
	}

	@Override
	protected void interrupted() {
		Robot.intake.IntakeTilt(0);
		
	}

}
