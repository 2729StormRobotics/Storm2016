package org.usfirst.frc.team2729.robot.commands;


import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeTilt extends Command{

	@Override
	protected void initialize() {
	
	}

	@Override
	protected void execute() {
		if (Robot.intake.isMax() == true && Robot.oi.getIntakeTilt()>0){
			Robot.intake.IntakeTilt(0);
		} else if (Robot.intake.isMin() == true && Robot.oi.getIntakeTilt()<0){
			Robot.intake.IntakeTilt(0);
		} else {
			Robot.intake.IntakeTilt(Robot.oi.getIntakeTilt());
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	
	}

	@Override
	protected void interrupted() {
		
		
	}

}
