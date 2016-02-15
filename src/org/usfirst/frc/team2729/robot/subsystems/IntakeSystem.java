package org.usfirst.frc.team2729.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team2729.robot.RobotMap;
import org.usfirst.frc.team2729.robot.commands.IntakeJoy;
import org.usfirst.frc.team2729.robot.util.RotaryPot;

public class IntakeSystem extends Subsystem {

	private final Talon _intakeHor = new Talon(RobotMap.PORT_MOTOR_INTAKE_HOR);
	private final Talon _intakeVer = new Talon(RobotMap.PORT_MOTOR_INTAKE_VER);
	private final Talon _intakeTilt = new Talon(RobotMap.PORT_MOTOR_INTAKE_TILT);
	
	private final DigitalInput _switchBallin = new DigitalInput(RobotMap.PORT_LIMIT_SWITCH_INTAKE_HALT);
	
	private final RotaryPot _pot = new RotaryPot(RobotMap.PORT_ROTATE_POT,1); //TODO: Determine max safe value
	
	//Feedback Loop Variables
	private double target = 0;//TODO: Determine the top value
	private double intError = 0;
	private double Ki = 0.0001;
	private double Kp = 0.0001;
	
	public IntakeSystem(){
		Timer _timer = new Timer();
		_timer.schedule(new TimerTask() {
			public void run() {
				double error = target - _pot.get();
				intError += target - _pot.get();
				_intakeTilt.set((Kp * error) + (Ki * intError));
			}
		}, 50, 50);
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new IntakeJoy());
	}
		
	public void halt() {
		_intakeHor.set(0);
		_intakeVer.set(0);
		_intakeTilt.set(0);
	}
	
	public void Intake(double power) {
		_intakeHor.set(power);
		_intakeVer.set(power);
	}
	
	public boolean isMax(){
		return _pot.get() >= _pot.VAL_MAX_SAFE;
	}
	
	public boolean isMin(){
		return _pot.get() >= _pot.VAL_MAX_SAFE;	
	}
	public void setTarget(double _target){
		target = _target;
	}
	public double getTarget(){
		return target;
	}
}
