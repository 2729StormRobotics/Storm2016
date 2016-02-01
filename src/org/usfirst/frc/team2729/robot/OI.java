package org.usfirst.frc.team2729.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2729.robot.commands.PIDDrive;
import org.usfirst.frc.team2729.robot.commands.Shift;
<<<<<<< HEAD
import org.usfirst.frc.team2729.robot.commands.ShooterSpin;
import org.usfirst.frc.team2729.robot.commands.ShooterTilt;
=======
import org.usfirst.frc.team2729.robot.commands.ShooterSpinDown;
import org.usfirst.frc.team2729.robot.commands.ShooterSpinUp;
>>>>>>> bf6e793f34aff0457c45c1c81083132c3dfabffe
import org.usfirst.frc.team2729.robot.commands.TankDrive;
import org.usfirst.frc.team2729.robot.RobotMap;

public class OI {

	private final Joystick driveJoystick = new Joystick(RobotMap.PORT_JOYSTICK_DRIVE),
						   armJoystick = new Joystick(RobotMap.PORT_JOYSTICK_ARMS);

	private final Button halveOne = new JoystickButton(driveJoystick, RobotMap.JOYDRIVE_BUTTON_HALVE_1),
						 halveTwo = new JoystickButton(driveJoystick, RobotMap.JOYDRIVE_BUTTON_HALVE_2),
						 shiftHighDrive = new JoystickButton(driveJoystick, RobotMap.JOYDRIVE_BUTTON_SHIFT_DRIVE_HIGH),
						 shiftLowDrive = new JoystickButton(driveJoystick, RobotMap.JOYDRIVE_BUTTON_SHIFT_DRIVE_LOW),
						 driveForward = new JoystickButton(driveJoystick, RobotMap.JOYDRIVE_BUTTON_FORWARD),
						 driveBackward = new JoystickButton(driveJoystick, RobotMap.JOYDRIVE_BUTTON_BACKWARDS),
						 hangingExtenderUp = new JoystickButton(driveJoystick, RobotMap.JOYARM_BUTTON_EXTENDER_UP),
	 					 hangingExtenderDown = new JoystickButton(driveJoystick, RobotMap.JOYARM_BUTTON_EXTENDER_DOWN),
						 shooterUp = new JoystickButton(driveJoystick, RobotMap.JOYARM_BUTTON_SHOOTER_UP),
						 shooterDown = new JoystickButton(driveJoystick, RobotMap.JOYARM_BUTTON_SHOOTER_DOWN),
						 shooterSpinUP = new JoystickButton(driveJoystick, RobotMap.JOYARM_BUTTON_SHOOTER_SPINUP),
						 shooterSpinDOWN = new JoystickButton(driveJoystick, RobotMap.JOYARM_BUTTON_SHOOTER_SPINDOWN);

	
	private double _zeroDeadzone(double joyValue,double dead) {
        return Math.abs(joyValue) > dead ? joyValue : 0;
	}
	public double getLeftDrive(){
	    	return _zeroDeadzone(-driveJoystick.getRawAxis(RobotMap.JOYDRIVE_AXIS_DRIVE_LEFT), 0.15);
	}
	public double getRightDrive(){
	    	return _zeroDeadzone(driveJoystick.getRawAxis(RobotMap.JOYDRIVE_AXIS_DRIVE_RIGHT), 0.15);
	}
	public double getIntake(){
		return _zeroDeadzone(armJoystick.getRawAxis(RobotMap.JOYARM_AXIS_INTAKE), 0.15);	
	}
	public double getIntakeTilt(){
		return _zeroDeadzone(armJoystick.getRawAxis(RobotMap.JOYARM_AXIS_INTAKE_TILT), 0.15);
		
	}

	public OI(){
		
		shooterSpinUP.whenPressed(new ShooterSpinUp());
		shooterSpinDOWN.whenPressed(new ShooterSpinDown());
		
		hangingExtenderDown.whileHeld(new ExtendHanging(-1));
		hangingExtenderUp.whileHeld(new ExtendHanging(1));
		
		shooterUp.whileHeld(new ShooterTilt(1));
		shooterDown.whileHeld(new ShooterTilt(-1));
		
		shiftHighDrive.whenPressed(new Shift(true));
		shiftLowDrive.whenPressed(new Shift(false));
		
		driveForward.whileHeld(new PIDDrive(0.8));
		driveBackward.whileHeld(new PIDDrive(-0.8));
		
		hangingExtenderDown.whileHeld(new ExtendHanging(-1));
		hangingExtenderUp.whileHeld(new ExtendHanging(1));
		
		halveOne.whileHeld(new Command() {
			@Override
			protected void initialize() { Robot.driveTrain.halveOne(true); }
			@Override
			protected void execute() {}
			@Override
			protected boolean isFinished() { return false; }
			@Override
			protected void end() { Robot.driveTrain.halveOne(false); }
			@Override
			protected void interrupted() { end(); }
		});
		
		halveTwo.whileHeld(new Command() {
			@Override
			protected void initialize() { Robot.driveTrain.halveTwo(true); }
			@Override
			protected void execute() {}
			@Override
			protected boolean isFinished() { return false; }
			@Override
			protected void end() { Robot.driveTrain.halveTwo(false); }
			@Override
			protected void interrupted() { end(); }
		});
	}
}

