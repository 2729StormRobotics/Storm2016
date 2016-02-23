package org.usfirst.frc.team2729.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2729.robot.RobotMap;
import org.usfirst.frc.team2729.robot.commands.ExtendHanging;
import org.usfirst.frc.team2729.robot.commands.IntakeTiltToPoint;
import org.usfirst.frc.team2729.robot.commands.PIDDrive;
import org.usfirst.frc.team2729.robot.commands.ActuatePTO;
import org.usfirst.frc.team2729.robot.commands.Shift;
import org.usfirst.frc.team2729.robot.commands.Shoot;
import org.usfirst.frc.team2729.robot.commands.ShootTiltToAngle;
import org.usfirst.frc.team2729.robot.commands.ShooterTilt;
import org.usfirst.frc.team2729.robot.commands.ShooterSpinDown;
import org.usfirst.frc.team2729.robot.commands.ShooterSpinUp;
import org.usfirst.frc.team2729.robot.commands.TankDrive;
import org.usfirst.frc.team2729.robot.commands.Winch;

import java.util.Timer;
import java.util.TimerTask;

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
						 hangingExtenderUp = new JoystickButton(armJoystick, RobotMap.JOYARM_BUTTON_EXTENDER_UP),
	 					 hangingExtenderDown = new JoystickButton(armJoystick, RobotMap.JOYARM_BUTTON_EXTENDER_DOWN),
						 shooterSpinUP = new JoystickButton(armJoystick, RobotMap.JOYARM_BUTTON_SHOOTER_SPINUP),
						 shooterSpinDOWN = new JoystickButton(armJoystick, RobotMap.JOYARM_BUTTON_SHOOTER_SPINDOWN),
						 winchIN = new JoystickButton(armJoystick, RobotMap.JOYARM_BUTTON_WINCH_IN),
						 winchOUT = new JoystickButton(armJoystick, RobotMap.JOYARM_BUTTON_WINCH_OUT),
						 IntakeSP1 = new JoystickButton(armJoystick, RobotMap.JOYARM_BUTTON_BEAVER_SP1),
						 IntakeSP2 = new JoystickButton(armJoystick, RobotMap.JOYARM_BUTTON_BEAVER_SP2),
						 IntakeSP3 = new JoystickButton(armJoystick, RobotMap.JOYARM_BUTTON_BEAVER_SP3),
						 ShootFire = new JoystickButton(armJoystick, RobotMap.JOYARM_BUTTON_SHOOT);
	
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
	public double getShootTilt(){
		return _zeroDeadzone(armJoystick.getRawAxis(RobotMap.JOYARM_AXIS_SHOOT_TILT), 0.15);
	}
	
	public OI(){
		
		shooterSpinUP.whenPressed(new ShooterSpinUp());
		shooterSpinDOWN.whenPressed(new ShooterSpinDown());
		
		togglePTOOn.whenPressed(new ActuatePTO(true));
		togglePTOOff.whenPressed(new ActuatePTO(false));
		
		shiftHighDrive.whenPressed(new Shift(true));
		shiftLowDrive.whenPressed(new Shift(false));
		
		driveForward.whileHeld(new PIDDrive(0.8));
		driveBackward.whileHeld(new PIDDrive(-0.8));
		
		winchIN.whileHeld(new Winch(true));
		winchOUT.whileHeld(new Winch(false));
		
		hangingExtenderUp.whileHeld(new ExtendHanging(true));
		hangingExtenderDown.whileHeld(new ExtendHanging(false));

		IntakeSP1.whenPressed(new IntakeTiltToPoint(1));
		IntakeSP2.whenPressed(new IntakeTiltToPoint(2));
		IntakeSP3.whenPressed(new IntakeTiltToPoint(3));
		
		ShootFire.whenPressed(new Shoot());
		
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
			public void run() {
				switch(armJoystick.getPOV()){
				case 1: Robot.shoot.setTargetSpeed(22500);
						break;
				case 2: Robot.shoot.setTargetSpeed(21875);
						break;
				case 3: Robot.shoot.setTargetSpeed(18750);
						break;
				case 4:	Robot.shoot.setTargetSpeed(15625);
						break;
				case 5: Robot.shoot.setTargetSpeed(12500);
						break;
				case 6: Robot.shoot.setTargetSpeed(9375);
						break;
				case 7: Robot.shoot.setTargetSpeed(6250);
						break;
				case 8:	Robot.shoot.setTargetSpeed(3125);
						break;
				};
			}
		}, 50, 50);
	}
}