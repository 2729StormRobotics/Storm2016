package org.usfirst.frc.team2729.robot.subsystems;

import org.usfirst.frc.team2729.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2729.robot.util.StringPot;

public class Shooter extends Subsystem {

	private final Encoder _leftShooter = new Encoder(RobotMap.PORT_ENCODER_SHOOT_LEFT_1, RobotMap.PORT_ENCODER_SHOOT_LEFT_2);
	private final Encoder _rightShooter = new Encoder(RobotMap.PORT_ENCODER_SHOOT_RIGHT_1, RobotMap.PORT_ENCODER_SHOOT_RIGHT_2);
	
	private final Talon _left = new Talon(RobotMap.PORT_MOTOR_SHOOT_LEFT),
		   				_right= new Talon(RobotMap.PORT_MOTOR_SHOOT_RIGHT),
		   				_tilt = new Talon(RobotMap.PORT_MOTOR_SHOOT_TILT);
	
	private boolean _shoot;
	
	private StringPot _stringPot = new StringPot(RobotMap.PORT_STRINGPOT, 1);
	
	public double getShooterAngle(){
		return _stringPot.get();
	}
	
	public Talon get_tilt() {
		return _tilt;
	}
	
	
	Shooter(){
		
	}
	
	public void ElevateShooter(){
		
	}
	
	public void Shoot(){
		
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	
}
