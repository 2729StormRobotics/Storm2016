package org.usfirst.frc.team2729.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2729.robot.RobotMap;
public class Intake extends Subsystem {

	private final Talon _intakeHor = new Talon(RobotMap.PORT_MOTOR_INTAKE_HOR);
	private final Talon _intakeVer = new Talon(RobotMap.PORT_MOTOR_INTAKE_VER);
	private final Talon _intakeTilt = new Talon(RobotMap.PORT_MOTOR_INTAKE_TILT);
	private final DigitalInput _switch = new DigitalInput(RobotMap.PORT_LIMIT_SWITCH_INTAKE);
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public Intake(){
	//TODO Constructor?
	}
	


	

}
