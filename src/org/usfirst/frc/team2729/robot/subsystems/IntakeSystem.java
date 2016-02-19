package org.usfirst.frc.team2729.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team2729.robot.RobotMap;
import org.usfirst.frc.team2729.robot.commands.IntakeJoy;
import org.usfirst.frc.team2729.robot.util.RotaryPot;

public class IntakeSystem extends Subsystem {

	private final Talon _intakeDrive = new Talon(RobotMap.PORT_MOTOR_INTAKE_DRIVE);
	private final Talon _intakeTilt = new Talon(RobotMap.PORT_MOTOR_INTAKE_TILT);
	
	private final DigitalInput _switchBallin = new DigitalInput(RobotMap.PORT_LIMIT_SWITCH_INTAKE_HALT);
	
	private final RotaryPot _pot = new RotaryPot(RobotMap.PORT_ROTATE_POT,1); //TODO: Determine max safe value
	
	//Feedback Loop Variables
	private double target = 0.714;
	private double Kp = 30;
	
	Timer _timer = new Timer();
	public IntakeSystem(){
		_timer.schedule(new TimerTask() {
			public void run() {
				SmartDashboard.putNumber("Target Tilt", target);
				double error = target - _pot.get();
				_intakeTilt.set(-(Kp * error));
			}
		}, 50, 50);
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new IntakeJoy());
	}
		
	public void halt() {
		_intakeDrive.set(0);
		_intakeTilt.set(0);
	}
	
	public void intakeDrive(double power) {
		_intakeDrive.set(power);
	}
	public double getTiltPower(){
		return _intakeTilt.get();
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
	public double getPot(){
		return _pot.get();
	}
}