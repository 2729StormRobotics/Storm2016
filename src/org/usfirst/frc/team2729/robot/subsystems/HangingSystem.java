package org.usfirst.frc.team2729.robot.subsystems;

import org.usfirst.frc.team2729.robot.*;
import org.usfirst.frc.team2729.robot.commands.ExtendHanging;
import org.usfirst.frc.team2729.robot.commands.TankDrive;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HangingSystem extends Subsystem {
	
	private final Talon _extender = new Talon(RobotMap.PORT_MOTOR_EXTENDER);
	private double winchPower = 0;
	
	@Override
	protected void initDefaultCommand() {setDefaultCommand(new ExtendHanging());}
	public void halt() {
		_extender.set(0);
	}
	public void setExtendPower(double power){
		_extender.set(power);
	}
	public double getExtendPower(){
		return _extender.get();
	}
	public void setWinch(double power){
		if(Robot.driveTrain.getPTO()){
			winchPower = power;
			Robot.driveTrain.TankDrive(power, power);
		} else {
			winchPower = 0;
		}
	}
	public double getWinch(){
		return winchPower;
	}
}