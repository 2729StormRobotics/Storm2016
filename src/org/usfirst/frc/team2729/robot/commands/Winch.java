package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Winch extends Command{
	private boolean winch;
	public Winch(boolean _winch){
		winch = _winch;
		
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		if (Robot.hang.setWinch = true){
			
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
