package org.usfirst.frc.team2729.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

public class Shoot extends Command{
	Timer timer = new Timer();
	private final double SHOT_DURATION = 1, SHOT_DELAY = 1;
	@Override
	protected void initialize() {
		timer.start();
	}
	@Override
	protected void execute() {
		if(timer.get() > SHOT_DELAY){
			if(Robot.shoot.getTargetTicks() > 0){
				Robot.shoot.setIntake(-1);
			} else {
				Robot.shoot.setIntake(0);
			}
		}
	}

	@Override
	protected boolean isFinished() {
		return timer.get() > SHOT_DURATION + SHOT_DELAY;
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
