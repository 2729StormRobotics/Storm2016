package org.usfirst.frc.team2729.robot;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team2729.robot.commands.EnableHanging;
import org.usfirst.frc.team2729.robot.commands.IntakeTilt;
import org.usfirst.frc.team2729.robot.commands.PIDDrive;
import org.usfirst.frc.team2729.robot.commands.Shift;
import org.usfirst.frc.team2729.robot.commands.Shoot;
import org.usfirst.frc.team2729.robot.commands.ShooterSetTilt;
import org.usfirst.frc.team2729.robot.commands.ShooterSpinDown;
import org.usfirst.frc.team2729.robot.commands.ShooterSpinUp;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

public class OI {

	private final Joystick driveJoystick = new Joystick(RobotMap.PORT_JOYSTICK_DRIVE),
			armJoystick = new Joystick(RobotMap.PORT_JOYSTICK_ARMS);

	private final Button halveOne = new JoystickButton(driveJoystick, RobotMap.JOYDRIVE_BUTTON_HALVE_1),
			halveTwo = new JoystickButton(driveJoystick, RobotMap.JOYDRIVE_BUTTON_HALVE_2),
			shiftHighDrive = new JoystickButton(driveJoystick, RobotMap.JOYDRIVE_BUTTON_SHIFT_DRIVE_HIGH),
			shiftLowDrive = new JoystickButton(driveJoystick, RobotMap.JOYDRIVE_BUTTON_SHIFT_DRIVE_LOW),
			driveForward = new JoystickButton(driveJoystick, RobotMap.JOYDRIVE_BUTTON_FORWARD),
			driveBackward = new JoystickButton(driveJoystick, RobotMap.JOYDRIVE_BUTTON_BACKWARDS),
			togglePTOOn = new JoystickButton(driveJoystick, RobotMap.JOYDRIVE_BUTTON_PTO_ON),
			togglePTOOff = new JoystickButton(driveJoystick, RobotMap.JOYDRIVE_BUTTON_PTO_OFF),

			shooterTiltMax = new JoystickButton(armJoystick, RobotMap.JOYARM_BUTTON_SHOOT_MAX),
			shooterTiltMin = new JoystickButton(armJoystick, RobotMap.JOYARM_BUTTON_SHOOT_MIN),
			shooterSpinUP = new JoystickButton(armJoystick, RobotMap.JOYARM_BUTTON_SHOOTER_SPINUP),
			shooterSpinDOWN = new JoystickButton(armJoystick, RobotMap.JOYARM_BUTTON_SHOOTER_SPINDOWN),
			IntakeUP = new JoystickButton(armJoystick, RobotMap.JOYARM_BUTTON_BEAVER_UP),
			IntakeDOWN = new JoystickButton(armJoystick, RobotMap.JOYARM_BUTTON_BEAVER_DOWN),
			ShootFire = new JoystickButton(armJoystick, RobotMap.JOYARM_BUTTON_SHOOT),
			HangSafety = new JoystickButton(armJoystick, RobotMap.JOYARM_BUTTON_HANG_SAFETY);

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
	public double getHangExt(){
		return _zeroDeadzone(armJoystick.getRawAxis(RobotMap.JOYARM_AXIS_HANGING_EXTENDER), 0.15);
	}

	public OI(){
		//Driver Commands

		shiftHighDrive.whenPressed(new Shift(true));
		shiftLowDrive.whenPressed(new Shift(false));

		driveForward.whileHeld(new PIDDrive(1, true));
		driveBackward.whileHeld(new PIDDrive(1, true));

		//Operator Commands
		shooterSpinUP.whenPressed(new ShooterSpinUp());
		shooterSpinDOWN.whenPressed(new ShooterSpinDown());

		IntakeUP.whenPressed(new IntakeTilt(true));
		IntakeDOWN.whenPressed(new IntakeTilt(false));

		ShootFire.whenPressed(new Shoot());

		shooterTiltMax.whenPressed(new ShooterSetTilt(Robot.shoot.TILT_TARGET_MAX));
		shooterTiltMin.whenPressed(new ShooterSetTilt(Robot.shoot.TILT_TARGET_MIN));

		HangSafety.whileHeld(new EnableHanging());

		//Special Commands
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

		Timer _timer = new Timer();
		_timer.schedule(new TimerTask() {
			@Override
			public void run() {
				switch(armJoystick.getPOV()){
				case 0: Robot.shoot.setTargetTilt(Robot.shoot.TILT_TARGET_HIGH);
				break;
				case 90:Robot.shoot.setTargetTilt(Robot.shoot.TILT_TARGET_INTAKE);
				break;
				case 180:Robot.shoot.setTargetTilt(Robot.shoot.TILT_TARGET_LOW);
				break;
				case 270:Robot.shoot.setTargetTilt(Robot.shoot.TILT_TARGET_MED);
				break;
				};
			}
		}, 50, 50);
	}
}