package org.usfirst.frc.team2729.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team2729.robot.RobotMap;
import org.usfirst.frc.team2729.robot.commands.Intake;

public class IntakeSystem extends Subsystem {

	private final Talon _intakeHor = new Talon(RobotMap.PORT_MOTOR_INTAKE_HOR);
	private final Talon _intakeVer = new Talon(RobotMap.PORT_MOTOR_INTAKE_VER);
	private final Talon _intakeTilt = new Talon(RobotMap.PORT_MOTOR_INTAKE_TILT);
	
	private final DigitalInput _switchMin = new DigitalInput(RobotMap.PORT_LIMIT_SWITCH_MIN_TILT);
	
	protected void initDefaultCommand() {
		setDefaultCommand(new Intake());
	}
		
	public void halt() {
		_intakeHor.set(0);
		_intakeVer.set(0);
		_intakeTilt.set(0);
	}
	public void stop() {
		_intakeHor.set(0);
		_intakeVer.set(0);
		_intakeTilt.set(0);
	}

	public void Intake(double power) {
		_intakeHor.set(power);
		_intakeVer.set(power);
	}

	public void IntakeTilt(double tiltPower){
		_intakeTilt.set(tiltPower);
	}
	
	public boolean isMax(){
		return _switchMax.get();
	}
	
	public boolean isMin(){
		return _switchMin.get();	
	}
}
