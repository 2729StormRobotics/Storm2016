package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeTiltToPoint extends Command {

	public IntakeTiltToPoint(int setPoint){
		switch(setPoint){
		case 1: Robot.intake.setTarget(0); //Top Position
				break;
		case 2: Robot.intake.setTarget(.5); //Mid Position
				break;
		case 3: Robot.intake.setTarget(.75); //Down Position
		}
	}
	@Override
	protected void initialize() {}
	@Override
	protected void execute() {}
	@Override
	protected boolean isFinished() {return false;}
	@Override
	protected void end() {}
	@Override
	protected void interrupted() {}
}