package org.usfirst.frc.team2729.robot.subsystems;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team2729.robot.Robot;
import org.usfirst.frc.team2729.robot.RobotMap;
import org.usfirst.frc.team2729.robot.commands.ShooterSpinUp;
import org.usfirst.frc.team2729.robot.commands.TankDrive;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2729.robot.util.StringPot;
import edu.wpi.first.wpilibj.DigitalInput;

public class ShootingSystem extends Subsystem {
	
	private final Encoder _leftShooter = new Encoder(RobotMap.PORT_ENCODER_SHOOT_LEFT_1, RobotMap.PORT_ENCODER_SHOOT_LEFT_2);
	private final Encoder _rightShooter = new Encoder(RobotMap.PORT_ENCODER_SHOOT_RIGHT_1, RobotMap.PORT_ENCODER_SHOOT_RIGHT_2);
	
	private final Talon _left = new Talon(RobotMap.PORT_MOTOR_SHOOT_LEFT),
		   				_right= new Talon(RobotMap.PORT_MOTOR_SHOOT_RIGHT),
		   				_tilt = new Talon(RobotMap.PORT_MOTOR_SHOOT_TILT),
		   				_intake = new Talon(RobotMap.PORT_MOTOR_SHOOT_INTAKE);
	
	private final StringPot _stringPot = new StringPot(RobotMap.PORT_STRINGPOT, 1);
	private final DigitalInput _intakeHalt = new DigitalInput(RobotMap.PORT_LIMIT_SWITCH_INTAKE_HALT);
	public final double TiltMin = .564;
	public final double TiltMax = .315;
	private double TiltSpinMin = .510;
	public final double TiltIntakePoint = 0.388;
	public final double TiltMediumShot = 0.352;
	public final double TiltHighShot = 0.329;
	public final double TiltLowShot = 0.55; //TODO: Determine
	
	private final double beta = 35.2664, phi = 43.62;
	private final double ANGLE_CONST_NUM = 0.091163234, ANGLE_CONST_DENOM = 0.0895807856;
	
	//Shooter Control Variables
	private double targetTicks = 0;
	private double IntErrorLeft = 0;
	private double IntErrorRight = 0;
	private double KiLeft = 0.000003;
	private double KpLeft = 0.000; //TODO: Tune this
	private double KiRight = 0.000003;
	private double KpRight = 0.000; //TODO: Tune this
	private double errorL = 0, errorR = 0;
	
	//Tilter Control Variables
	private double targetString = _stringPot.get();
	private double KpShoot = 12;
	private double errorShoot = 0;
	private double KiShoot = 0.001;
	private double intErrorShoot = 0;
	
	public ShootingSystem(){
		targetTicks = 0;
		Timer _timer = new Timer();
		_timer.schedule(new TimerTask() {
			public void run() {
				errorL = targetTicks - _leftShooter.getRate();
				errorR = targetTicks - _rightShooter.getRate();
				IntErrorLeft += errorL;
				IntErrorRight += errorR;
				//_right.set((KpRight * errorR) + (KiRight * IntErrorRight));
				_right.set((KpLeft * errorL) + (KiLeft * IntErrorLeft));//Temporary fix to encoder problems
				_left.set((KpLeft * errorL) + (KiLeft * IntErrorLeft));
				//_right.set(targetTicks > 0 ? 1 : 0);
				//_left.set(targetTicks > 0 ? 1 : 0);
				SmartDashboard.putNumber("Shoot Target Ticks", targetTicks);
				SmartDashboard.putNumber("Shoot Actual Ticks LEFT", _leftShooter.getRate());
				SmartDashboard.putNumber("Shoot Actual Ticks RIGHT", _rightShooter.getRate());
				//SmartDashboard.putNumber("Shoot Motor Power", _left.get());
				//SmartDashboard.putNumber("Shoot Integral Error", IntErrorLeft);
				//SmartDashboard.putNumber("PID Value", (KpLeft * errorL) + (KiLeft * IntErrorLeft));
				if(_stringPot.get() > TiltSpinMin){
					haltSpin();
				}
				
				errorShoot = targetString - _stringPot.get();
				intErrorShoot += errorShoot;
				setTiltPower((errorShoot * KpShoot) + (intErrorShoot * KiShoot));
				SmartDashboard.putNumber("Shoot Tilt Target", targetString);
				//SmartDashboard.putNumber("Shoot Tilt Int Error", intErrorShoot);
				//SmartDashboard.putNumber("Shoot Tilt PID", (errorShoot * KpShoot) + (intErrorShoot * KiShoot));
			}
		}, 50, 50);
	}
	@Override
	protected void initDefaultCommand() {}
	
	public void setTiltPower(double power){
		if (isMax() == true && power < 0){ //Inverted due to motor polarity
			_tilt.set(0);
		} else if (isMin() == true && power > 0){ //Inverted due to motor polarity
			_tilt.set(0);
		} else {
			_tilt.set(power);
		}
	}
	public void setTargetTilt(double _target){
		targetString = _target;
		intErrorShoot = 0;
	}
	public double getTargetTilt(){
		return targetString;
	}
	public void setTargetSpeed(double _target){
		targetTicks = _target;
	}
	public void setIntake(double power){
		_intake.set(-power); //negated due to electrical setup
	}
	public void haltSpin(){
		targetTicks = 0;
		IntErrorLeft = 0;
		IntErrorRight = 0;
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
		return _tilt.get();
	}
	public double getShooterPotRAW(){
		return _stringPot.get();
	}
	public double getShooterPotLength(){
		return _stringPot.getLength();
	}
	public double getShooterAngle(){
		SmartDashboard.putNumber("alpha",(Math.acos((ANGLE_CONST_NUM - (_stringPot.getLength() * _stringPot.getLength()))/ANGLE_CONST_DENOM) * (180/Math.PI)));
		return 180 - phi - beta - (Math.acos((ANGLE_CONST_NUM - (_stringPot.getLength() * _stringPot.getLength()))/ANGLE_CONST_DENOM) * (180/Math.PI));
	}
	public double getShooterAngleLSLR(){
		double x = _stringPot.get();
		SmartDashboard.putNumber("alpha LSLR", (-211.52*x) + 122.56);
		return (-211.52*x) + 122.56;
	}
	public double getShooterAnglePR(){
		double x = _stringPot.get();
		SmartDashboard.putNumber("alpha PR", (-72.993 * Math.pow(x, 2)) - (149.28 * x) + 109.77);
		return (-72.993 * Math.pow(x, 2)) - (149.28 * x) + 109.77;
	}
	public boolean isMax(){
		return (_stringPot.get() <= TiltMax); //Inequality is flipped due to string pot polarity
	}
	public boolean isMin(){
		return (_stringPot.get() >= TiltMin);//Inequality is flipped due to string pot polarity
	}
	public boolean getIntakeHalt(){
		return !_intakeHalt.get(); //Returns true when the Boulder is present
	}
}