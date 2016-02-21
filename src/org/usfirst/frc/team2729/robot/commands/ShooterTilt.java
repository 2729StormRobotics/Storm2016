package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShooterTilt extends Command{
	
	private double _tilt;
	
	public ShooterTilt(){
		requires(Robot.shoot);
	}

	@Override
	protected void initialize() {}
	
	@Override
	protected void execute() {	
		_tilt = Robot.oi.getShootTilt();
		if (Robot.shoot.isMax() == true && _tilt < 0){ //Inverted due to motor polarity
			Robot.shoot.setTiltPower(0);
		} else if (Robot.shoot.isMin() == true && _tilt > 0){ //Inverted due to motor polarity
			Robot.shoot.setTiltPower(0);
		} else {
			Robot.shoot.setTiltPower(_tilt);
		}
	}

	@Override
	protected boolean isFinished() {
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