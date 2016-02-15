package org.usfirst.frc.team2729.robot.subsystems;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team2729.robot.RobotMap;
import org.usfirst.frc.team2729.robot.commands.ShooterSpinUp;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2729.robot.util.StringPot;
import edu.wpi.first.wpilibj.DigitalInput;

public class Shooter extends Subsystem {

	//button hold spins up hte shooting wheels, button to raise and lower shooter, button whiel shooting also running intake
	
	private final Encoder _leftShooter = new Encoder(RobotMap.PORT_ENCODER_SHOOT_LEFT_1, RobotMap.PORT_ENCODER_SHOOT_LEFT_2);
	private final Encoder _rightShooter = new Encoder(RobotMap.PORT_ENCODER_SHOOT_RIGHT_1, RobotMap.PORT_ENCODER_SHOOT_RIGHT_2);
	
	private final Talon _left = new Talon(RobotMap.PORT_MOTOR_SHOOT_LEFT),
		   				_right= new Talon(RobotMap.PORT_MOTOR_SHOOT_RIGHT),
		   				_tilt = new Talon(RobotMap.PORT_MOTOR_SHOOT_TILT),
		   				_intake = new Talon(RobotMap.PORT_MOTOR_SHOOT_INTAKE);
	
	private final StringPot _stringPot = new StringPot(RobotMap.PORT_STRINGPOT, 1);
	private final DigitalInput _minSwitch = new DigitalInput(RobotMap.PORT_SHOOTER_SWITCH_MIN_TILT);
	private final DigitalInput _intakeHalt= new DigitalInput(RobotMap.PORT_LIMIT_SWITCH_INTAKE_HALT);
	private double TiltMax = 1; //TODO: Find these values experimentally
	private double TiltMin = 0;
	public final double SHOOTER_BASE = 0.24050625;
	
	private double shootPower;
	private double intakePower;
	private double tiltPower;

	//Integral Control Variables
	private double targetTicks = 0;
	private double IntErrorLeft = 0;
	private double IntErrorRight = 0;
	private double KiLeft = 0.000003;
	private double KpLeft = 0.000003;
	private double KiRight = 0.000003;
	private double KpRight = 0.000003;
	private double errorL = 0, errorR = 0;

	public Shooter(){
		targetTicks = 0;
		Timer _timer = new Timer();
		_timer.schedule(new TimerTask() {
			public void run() {
				errorL = targetTicks - _leftShooter.getRate();
				errorR = targetTicks - _rightShooter.getRate();
				IntErrorLeft += errorL;
				IntErrorRight += errorR;
				_right.set((KpRight * errorR) + (KiRight * IntErrorRight));
				_left.set((KpLeft * errorL) + (KiLeft * IntErrorLeft));
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
	
	public void setIntake(double power){
		_intake.set(0);
	}
	public void haltSpin(){
		targetTicks = 0;
		_right.set(0);
		_left.set(0);
	}
	
	public double getTargetTicks(){
		return targetTicks;
	}
	
	public double getShooterTilt(){
		return _stringPot.get();
	}
	
	public double getTiltPower(){
		return tiltPower;
	}

	public double getShooterAngle(){
		return Math.acos(1 - (Math.pow(_stringPot.getLength(),2)/(2*Math.pow(SHOOTER_BASE, 2))));
	}
	
	@Override
	protected void initDefaultCommand() {
	}
	
	public boolean isMax(){
		return (_stringPot.get() > TiltMax);
	}
	
	public boolean isMin(){
		return _minSwitch.get();
	}
	public boolean getIntakeHalt(){
		return _intakeHalt.get();
	}
}