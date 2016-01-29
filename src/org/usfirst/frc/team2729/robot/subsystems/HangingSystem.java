package org.usfirst.frc.team2729.robot.subsystems;

import org.usfirst.frc.team2729.robot.*;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HangingSystem extends Subsystem {
	
	private final Talon _extender_left = new Talon(RobotMap.PORT_MOTOR_EXTENDER_LEFT),
						_extender_right = new Talon(RobotMap.PORT_MOTOR_EXTENDER_RIGHT);
	
	private final Encoder _extender_left_encoder = new Encoder(RobotMap.PORT_ENCODER_EXTENDER_LEFT_1, RobotMap.PORT_ENCODER_EXTENDER_RIGHT_2);
	private final Encoder _extender_right_encoder = new Encoder(RobotMap.PORT_ENCODER_EXTENDER_RIGHT_1, RobotMap.PORT_ENCODER_EXTENDER_RIGHT_2);
	
	private boolean _extend;
	
	public void Extend(){
		 _extender_left.set(0.5);
		 _extender_right.set(0.5);
		 
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
