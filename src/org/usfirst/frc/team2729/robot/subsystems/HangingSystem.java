package org.usfirst.frc.team2729.robot.subsystems;

import org.usfirst.frc.team2729.robot.*;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HangingSystem extends Subsystem {
	
	private final Talon _winchLeft = new Talon(RobotMap.PORT_MOTOR_DRIVE_LEFT),
						_winchRight = new Talon(RobotMap.PORT_MOTOR_DRIVE_RIGHT),
						_extenderLeft = new Talon(RobotMap.PORT_MOTOR_EXTENDER_LEFT),
						_extenderRight = new Talon(RobotMap.PORT_MOTOR_EXTENDER_RIGHT);

	
	private final Encoder _extenderLeftEncoder = new Encoder(RobotMap.PORT_ENCODER_EXTENDER_LEFT_1, RobotMap.PORT_ENCODER_EXTENDER_RIGHT_2);
	private final Encoder _extenderRightEncoder = new Encoder(RobotMap.PORT_ENCODER_EXTENDER_RIGHT_1, RobotMap.PORT_ENCODER_EXTENDER_RIGHT_2);
	
	@Override
	protected void initDefaultCommand() {}
	public void halt() {
		_winchLeft.set(0);
		_winchRight.set(0);
	}
	public void setLeftExtendPower(double power){
		_winchLeft.set(power);
	}
	public void setRightExtendPower(double power){
		_winchRight.set(power);
	}
	public double getLeftExtendPower(){
		return _winchLeft.get();
	}
	public double getRighttExtendPower(){
		return _winchRight.get();
	}
}