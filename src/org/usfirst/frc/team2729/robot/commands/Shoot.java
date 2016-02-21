package org.usfirst.frc.team2729.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

public class Shoot extends Command{
	Timer timer;
	private final double SHOT_DURATION = 0.5;
	@Override
	protected void initialize() {
		timer.start();
	}
	@Override
	protected void execute() {
		if(Robot.shoot.getTargetTicks() > 0){
			Robot.shoot.setIntake(1);
		} else {
			Robot.shoot.setIntake(0);
		}
	}

	@Override
	protected boolean isFinished() {
		return timer.get() > SHOT_DURATION;
	}

	@Override
	protected void end() {
		Robot.shoot.setIntake(0);
	}

	@Override
	protected void interrupted() {
		Robot.shoot.setIntake(0);
	}

}
