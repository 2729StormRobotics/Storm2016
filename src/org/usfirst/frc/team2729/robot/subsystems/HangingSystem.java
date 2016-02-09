package org.usfirst.frc.team2729.robot.subsystems;

import org.usfirst.frc.team2729.robot.*;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HangingSystem extends Subsystem {
	
	private final Talon _extenderLeft = new Talon(RobotMap.PORT_MOTOR_EXTENDER_LEFT),
						_extenderRight = new Talon(RobotMap.PORT_MOTOR_EXTENDER_RIGHT);
			//_winchLeft = new Talon(RobotMap.PORT_MOTOR_DRIVE_LEFT),
			//_winchRight = new Talon(RobotMap.PORT_MOTOR_DRIVE_RIGHT),
	
	@Override
	protected void initDefaultCommand() {}
	public void halt() {
		_extenderLeft.set(0);
		_extenderRight.set(0);
	}
	public void setLeftExtendPower(double power){
		_extenderLeft.set(power);
	}
	public void setRightExtendPower(double power){
		_extenderRight.set(power);
	}
	public double getLeftExtendPower(){
		return _extenderLeft.get();
	}
	public double getRighttExtendPower(){
		return _extenderRight.get();
	}
}