package org.usfirst.frc.team2729.robot.subsystems;

import org.usfirst.frc.team2729.robot.RobotMap;
import org.usfirst.frc.team2729.robot.commands.IntakeJoy;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeSystem extends Subsystem {

	private final Talon _intakeDrive = new Talon(RobotMap.PORT_MOTOR_INTAKE_DRIVE);
	private final Talon _intakeTilt = new Talon(RobotMap.PORT_MOTOR_INTAKE_TILT);
	private final DoubleSolenoid _intake = new DoubleSolenoid(RobotMap.PORT_SHIFT_HANG_ON, RobotMap.PORT_SHIFT_HANG_OFF);

	private boolean _isTopPosition = false;

	public IntakeSystem(){
		_intake.set(DoubleSolenoid.Value.kForward);
	}

	@Override
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


	public void setIntakeTilt(boolean top){
		_isTopPosition  = top;
		_intake.set(top ? DoubleSolenoid.Value.kForward
				: DoubleSolenoid.Value.kReverse); //Direction decided by electrical setup
	}
}