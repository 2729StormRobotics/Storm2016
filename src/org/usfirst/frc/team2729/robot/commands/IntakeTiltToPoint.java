package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeTiltToPoint extends Command {
	private int _setPoint;
	public IntakeTiltToPoint(int setPoint){
		_setPoint = setPoint;
	}
	@Override
	protected void initialize() {}
	@Override
	protected void execute() {		
		switch(_setPoint){
			case 1: Robot.intake.setTarget(0.735); //Top Position
					break;
			case 2: Robot.intake.setTarget(0.510); //Mid Position
					break;
			case 3: Robot.intake.setTarget(0.414);//Down Position
					break;
			default:Robot.intake.setTarget(Robot.intake.getTarget());
					break;
		}
	}
	@Override
	protected boolean isFinished() {return true;}
	@Override
	protected void end() {}
	@Override
	protected void interrupted() {}
}