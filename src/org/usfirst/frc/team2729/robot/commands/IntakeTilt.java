package org.usfirst.frc.team2729.robot.commands;


import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeTilt extends Command{

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		if (Robot.intake.isMax() == true && Robot.oi.getIntakeTilt()>0){
			Robot.intake.IntakeTilt(0);
		}
		
		else if (Robot.intake.isMin() == true && Robot.oi.getIntakeTilt()<0){
			Robot.intake.IntakeTilt(0);
		}
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
