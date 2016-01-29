package org.usfirst.frc.team2729.robot.subsystems;

import org.usfirst.frc.team2729.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2729.robot.util.StringPot;

public class Shooter extends Subsystem {

	//button hold spins up hte shooting wheels, button to raise and lower shooter, button whiel shooting also running intake
	
	private final Encoder _leftShooter = new Encoder(RobotMap.PORT_ENCODER_SHOOT_LEFT_1, RobotMap.PORT_ENCODER_SHOOT_LEFT_2);
	private final Encoder _rightShooter = new Encoder(RobotMap.PORT_ENCODER_SHOOT_RIGHT_1, RobotMap.PORT_ENCODER_SHOOT_RIGHT_2);
	
	private final Talon _left = new Talon(RobotMap.PORT_MOTOR_SHOOT_LEFT),
		   				_right= new Talon(RobotMap.PORT_MOTOR_SHOOT_RIGHT),
		   				_tilt = new Talon(RobotMap.PORT_MOTOR_SHOOT_TILT),
						_intake = new Talon(RobotMap.PORT_MOTOR_SHOOT_INTAKE);
	
	private StringPot _stringPot = new StringPot(RobotMap.PORT_STRINGPOT, 1);
	
	private double leftPower;
	private double rightPower;
	private double intakePower;

	//command that tilts shooter at given speed up or down, needs to have a bottom and top value set on string pot: should be saved in subsystem
	//setters and getters for right and left motors, use power
	
	public void setTiltPower(double power){
		_tilt.set(power);
	}
	
	public void setLeftPower(double power){
		_left.set(power);
		leftPower = power;
	}
	
	public void setRightPower(double power){
		_right.set(power);
		rightPower = power;
	}
	
	public void setInktakePower(double power){
		_intake.set(power);
		intakePower = power;
	}
	
	public double getLeftPower(){
		return leftPower;
	}
	
	public double getRightPower(){
		return rightPower;
	}

	
	
	public double getShooterAngle(){
		return _stringPot.get();
	}
	
	
	public Shooter(){
		
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	
}
