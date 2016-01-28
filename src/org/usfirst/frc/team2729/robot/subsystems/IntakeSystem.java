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
	
	private final DigitalInput _switch = new DigitalInput(RobotMap.PORT_LIMIT_SWITCH_INTAKE_TILT);
	
	
	protected void initDefaultCommand() {
		setDefaultCommand(new Intake());
	
	}
	//private void setDefaultCommand(Intake intake) {
		// TODO Auto-generated method stub
		
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

	public void Intake(double intake) {
		_intakeHor.set(intake/3);
		_intakeVer.set(intake/3);
		_intakeTilt.set(intake/3);
	}

	public boolean isMax(){
		return !_switch.get();
		
	}
	


	
	//Drive Intake Talons
	
	//Drive tilt talon
	//get button state
}
