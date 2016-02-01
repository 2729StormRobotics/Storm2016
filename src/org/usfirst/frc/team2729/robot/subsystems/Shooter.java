package org.usfirst.frc.team2729.robot.subsystems;

import java.util.Timer;
import java.util.TimerTask;

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
	private double TiltMax = 1; //TODO: Find these values experimentally
	private double TiltMin = 0;
	
	private double leftPower;
	private double rightPower;
	private double intakePower;
	private double tiltPower;

	//Integral Control Variables
	private double targetTicks = 0;
	private double IntErrorLeft = 0;
	private double IntErrorRight = 0;
	private double KiLeft = 0.001;
	private double KiRight = 0.001;

	public Shooter(){
		Timer _timer = new Timer();
		_timer.schedule(new TimerTask() {
			public void run() {
				IntErrorLeft += targetTicks - _leftShooter.getRate();
				IntErrorRight += targetTicks - _rightShooter.getRate();
				_right.set(KiRight * IntErrorRight);
				_left.set(KiLeft * IntErrorLeft);
			}
		}, 50, 50);
	}

	public void setTiltPower(double power){
		_tilt.set(power);
		tiltPower = power;
	}
	
	public void setTargetSpeed(double _target){
		targetTicks = _target;
	}
	
	public void haltSpin(){
		targetTicks = 0;
		_right.set(0);
		_left.set(0);
	}
	
	public double getLeftPower(){
		return leftPower;
	}
	
	public double getRightPower(){
		return rightPower;
	}
	
	public double getShooterTilt(){
		return _stringPot.get();
	}
	
	public double getTiltPower(){
		return tiltPower;
	}
	
	@Override
	protected void initDefaultCommand() {
	}

	public double getTiltMin() {
		return TiltMin;
	}

	public double getTiltMax() {
		return TiltMax;
	}
	
	public boolean isMax(){
		
		if (tiltPower == TiltMax){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public boolean isMin(){
		
		if (tiltPower == TiltMin){
			return true;
		}
		else{
			return false;
		}
		
	}
}
