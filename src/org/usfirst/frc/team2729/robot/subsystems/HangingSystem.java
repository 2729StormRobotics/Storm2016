package org.usfirst.frc.team2729.robot.subsystems;

import org.usfirst.frc.team2729.robot.*;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HangingSystem extends Subsystem {
	
	private final Talon _extenderLeft = new Talon(RobotMap.PORT_MOTOR_DRIVE_LEFT),
						_extenderRight = new Talon(RobotMap.PORT_MOTOR_DRIVE_RIGHT);
	
	private final Encoder _extender_left_encoder = new Encoder(RobotMap.PORT_ENCODER_EXTENDER_LEFT_1, RobotMap.PORT_ENCODER_EXTENDER_RIGHT_2);
	private final Encoder _extender_right_encoder = new Encoder(RobotMap.PORT_ENCODER_EXTENDER_RIGHT_1, RobotMap.PORT_ENCODER_EXTENDER_RIGHT_2);
	
	private double leftExtendPower;
	private double rightExtendPower;
	@Override
	protected void initDefaultCommand() {}
	public void halt() {
		_extenderLeft.set(0);
		_extenderRight.set(0);
	}
	public void setLeftExtendPower(double power){
		_extenderLeft.set(power);
		leftExtendPower = power;
	}
	public void setRightExtendPower(double power){
		_extenderRight.set(power);
		rightExtendPower = power;
	}
	public double getLeftExtendPower(){
		return leftExtendPower;
	}
	public double getRighttExtendPower(){
		return rightExtendPower;
	}
}


