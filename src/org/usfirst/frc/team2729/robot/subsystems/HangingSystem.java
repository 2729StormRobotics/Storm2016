package org.usfirst.frc.team2729.robot.subsystems;

import org.usfirst.frc.team2729.robot.Robot;
import org.usfirst.frc.team2729.robot.RobotMap;
import org.usfirst.frc.team2729.robot.commands.ExtendHanging;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HangingSystem extends Subsystem {

	private final Talon _extender = new Talon(RobotMap.PORT_MOTOR_EXTENDER);
	private double winchPower = 0;
	private boolean enabled = false;

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ExtendHanging());
	}

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
			Robot.driveTrain.tankDrive(power, power);
		} else {
			winchPower = 0;
		}
	}
	public double getWinch(){
		return winchPower;
	}
	public boolean isEnabled(){
		return enabled;
	}
	public void setEnabled(boolean _enabled){
		enabled = _enabled;
	}
}