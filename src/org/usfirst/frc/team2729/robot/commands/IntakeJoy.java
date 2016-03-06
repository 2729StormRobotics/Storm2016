package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeJoy extends Command {
	
	public IntakeJoy(){
		requires(Robot.intake);
	}
	
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.intake.intakeDrive(Robot.oi.getIntake());
		//When Boulder is not present drive the intake belt as usual
		//when boulder is present, only drive the intake belt backwards
		Robot.shoot.setIntake(!Robot.shoot.getIntakeHalt() ? Robot.oi.getIntake() : 
								(Robot.oi.getIntake() > 0 ? Robot.oi.getIntake() : 0));
		SmartDashboard.putNumber("ROBOT.oi.getIntake()", Robot.oi.getIntake());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.intake.intakeDrive(0);
	}

	@Override
	protected void interrupted() {
		Robot.intake.intakeDrive(0);
	}
}
