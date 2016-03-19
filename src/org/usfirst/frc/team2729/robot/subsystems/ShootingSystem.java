package org.usfirst.frc.team2729.robot.subsystems;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team2729.robot.RobotMap;
import org.usfirst.frc.team2729.robot.util.StringPot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShootingSystem extends Subsystem {

	private final Encoder _leftShooter = new Encoder(RobotMap.PORT_ENCODER_SHOOT_LEFT_1, RobotMap.PORT_ENCODER_SHOOT_LEFT_2);
	private final Encoder _rightShooter = new Encoder(RobotMap.PORT_ENCODER_SHOOT_RIGHT_1, RobotMap.PORT_ENCODER_SHOOT_RIGHT_2);

	private final Talon _left = new Talon(RobotMap.PORT_MOTOR_SHOOT_LEFT),
			_right = new Talon(RobotMap.PORT_MOTOR_SHOOT_RIGHT),
			_tilt = new Talon(RobotMap.PORT_MOTOR_SHOOT_TILT),
			_intake = new Talon(RobotMap.PORT_MOTOR_SHOOT_INTAKE);

	private double prevString = -1; // For first run through
	private static final int stallTimeStep = 200;
	private boolean tiltStalled = false;
	private boolean tiltEStopped = false;
	private static final int STALL_FAILED_LIMIT = 3;
	private static final int ESTOP_FAILED_LIMIT = 4;
	private static final double TILT_STALL_MOTOR_LIMIT = 0.15;
	private static final double TILT_ESTOP_STRING_DELTA_MIN = 0.005;
	private static final double TILT_ESTOP_MOTOR_LIMIT = 0.5;

	private final StringPot _stringPot = new StringPot(RobotMap.PORT_STRINGPOT, 1);
	private final DigitalInput _intakeHalt = new DigitalInput(RobotMap.PORT_LIMIT_SWITCH_INTAKE_HALT);
	private final DigitalInput _maxSwitch = new DigitalInput(RobotMap.PORT_SHOOTER_SWITCH_MAX_TILT);

	// Off robot simulation variables
	public final double TILT_TARGET_MIN = .530;
	public final double TILT_TARGET_MAX = .317;
	private double TiltSpinMin = .467;
	public final double TILT_TARGET_INTAKE = 0.363;
	public final double TILT_TARGET_MED = 0.378;
	public final double TILT_TARGET_HIGH = 0.327;
	public final double TILT_TARGET_LOW = 0.395; // TODO: Determine

	private final double beta = 35.2664, phi = 43.62;
	private final double ANGLE_CONST_NUM = 0.091163234, ANGLE_CONST_DENOM = 0.0895807856;

	// Shooter Control Variables
	private double targetTicks = 0;
	private double IntErrorLeft = 0;
	private double IntErrorRight = 0;
	private double KiLeft = 0.000003;
	private double KpLeft = 0.000; // TODO: Tune this
	private double KiRight = 0.000003;
	private double KpRight = 0.000; // TODO: Tune this
	private double errorL = 0, errorR = 0;

	// Tilter Control Variables
	private double targetString = _stringPot.get();
	private double KpShoot = 10;
	private double KiShoot = 0.1;
	private double errorShoot = 0;
	private double intErrorShoot = 0;
	private boolean targetStringReached = true;

	public ShootingSystem() {
		targetTicks = 0;
		Timer _timer = new Timer();
		_timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// Shooter Wheel PID Calculations
				errorL = targetTicks - _leftShooter.getRate();
				errorR = targetTicks - _rightShooter.getRate();
				IntErrorLeft += errorL;
				IntErrorRight += errorR;
				// _right.set((KpRight * errorR) + (KiRight * IntErrorRight));
				_right.set((KpLeft * errorL) + (KiLeft * IntErrorLeft)); //Temporary fix to encoder problems
				_left.set((KpLeft * errorL) + (KiLeft * IntErrorLeft));
				SmartDashboard.putNumber("SHOOT PID OUT", (KpLeft * errorL) + (KiLeft * IntErrorLeft));
				if (_stringPot.get() > TiltSpinMin) {
					haltSpin();
				}
				SmartDashboard.putNumber("Shoot Target Ticks", targetTicks);
				SmartDashboard.putNumber("Shoot Actual Ticks LEFT", _leftShooter.getRate());
				SmartDashboard.putNumber("Shoot Actual Ticks RIGHT", _rightShooter.getRate());

				// Shooter Tilt PID Calculations
				if (!targetStringReached) {
					errorShoot = targetString - _stringPot.get();
					intErrorShoot += errorShoot;
					if (Math.abs((errorShoot * KpShoot) + (intErrorShoot * KiShoot)) > 0 && Math.abs(errorShoot) > 0.002) {
						setTiltPower((errorShoot * KpShoot) + (intErrorShoot * KiShoot));
						SmartDashboard.putNumber("PID SHOOT TILT", (errorShoot * KpShoot) + (intErrorShoot * KiShoot));
						targetStringReached = (getTiltPower() == 0);
					} else {
						targetStringReached = true;
						setTiltPower(0);
					}
				} else {
					intErrorShoot = 0;
					setTiltPower(0);
				}

				if (isMax() || isMin()) {
					intErrorShoot = 0;
				}

				SmartDashboard.putNumber("Shoot Tilt Target", targetString);
			}
		}, 50, 50);
		_timer.schedule(new TimerTask() { // Stall Detection
			int stallFailCount = 0;
			int estopFailCount = 0;
			@Override
			public void run() {
				if (prevString != 1) {
					if (!tiltStalled) {
						if (Math.abs(prevString - _stringPot.get()) <= 0.001 && Math.abs(_tilt.get()) > TILT_STALL_MOTOR_LIMIT) {
							stallFailCount++;
						} else {
							stallFailCount = 0;
						}
						if (stallFailCount == STALL_FAILED_LIMIT) {// If consecutive errors occur, set stall to true
							tiltStalled = true;
							stallFailCount = 0;
						}
					}
					if (!tiltEStopped) {
						// Checks motor polarity.
						if (Math.abs(_stringPot.get() - prevString) > TILT_ESTOP_STRING_DELTA_MIN && Math.abs(_tilt.get()) > TILT_ESTOP_MOTOR_LIMIT) {
							//Designed to catch macro scale movements.
							if (Math.signum(_stringPot.get() - prevString) != Math.signum(_tilt.get())) {
								estopFailCount++;
							} else {
								estopFailCount = 0;
							}
							if(estopFailCount == ESTOP_FAILED_LIMIT){
								tiltEStopped = true;
							}
						}
					}
					prevString = _stringPot.get();
				} else {
					prevString = _stringPot.get();
				}
			}
		}, stallTimeStep, stallTimeStep);
	}

	@Override
	protected void initDefaultCommand() {
	}

	public void setTiltPower(double power) {
		if (!Double.isNaN(power) && !isOutBounds() && !tiltStalled && !tiltEStopped) {
			if ((isMax() == true && power < 0)) { //Inverted due to motor polarity
				_tilt.set(0);
			} else if (isMin() == true && power > 0) { //Inverted due to motor polarity
				_tilt.set(0);
			} else {
				_tilt.set(power);
			}
		} else {
			_tilt.set(0);
		}
	}

	public void setTargetTilt(double _target) {
		if (targetString != _target) {
			tiltStalled = false;
		}
		targetString = _target;
		intErrorShoot = 0;
		targetStringReached = false;
	}

	public double getTargetTilt() {
		return targetString;
	}

	public void setTargetSpeed(double _target) {
		targetTicks = _target;
	}

	public void setIntake(double power) {
		_intake.set(power); // negated due to electrical setup
	}

	public void haltSpin() {
		targetTicks = 0;
		IntErrorLeft = 0;
		IntErrorRight = 0;
		_right.set(0);
		_left.set(0);
	}

	public double getTargetTicks() {
		return targetTicks;
	}

	public double getShooterTilt() {
		return _stringPot.get();
	}

	public double getTiltPower() {
		return _tilt.get();
	}

	public double getShooterPotRAW() {
		return _stringPot.get();
	}

	public double getShooterPotLength() {
		return _stringPot.getLength();
	}

	public double getShooterAngle() {
		SmartDashboard.putNumber("alpha",
				(Math.acos((ANGLE_CONST_NUM - (_stringPot.getLength() * _stringPot.getLength())) / ANGLE_CONST_DENOM)
						* (180 / Math.PI)));
		return 180 - phi - beta
				- (Math.acos((ANGLE_CONST_NUM - (_stringPot.getLength() * _stringPot.getLength())) / ANGLE_CONST_DENOM)
						* (180 / Math.PI));
	}

	public double getShooterAngleLSLR() {
		double x = _stringPot.get();
		SmartDashboard.putNumber("alpha LSLR", (-211.52 * x) + 122.56);
		return (-211.52 * x) + 122.56;
	}

	public double getShooterAnglePR() {
		double x = _stringPot.get();
		SmartDashboard.putNumber("alpha PR", (-72.993 * Math.pow(x, 2)) - (149.28 * x) + 109.77);
		return (-72.993 * Math.pow(x, 2)) - (149.28 * x) + 109.77;
	}

	public boolean isMax() {
		//Inequality is flipped due to string pot polarity switch as well
		return (_stringPot.get() <= TILT_TARGET_MAX || !_maxSwitch.get());
	}

	public boolean isMin() {
		//Inequality is flipped due to string pot polarity
		return (_stringPot.get() >= TILT_TARGET_MIN);
	}

	public boolean getIntakeHalt() {
		// Returns true when the Boulder is present
		return !_intakeHalt.get();
	}

	public boolean isOutBounds() {
		return (_stringPot.get() < TILT_TARGET_MAX - .030) || (_stringPot.get() > TILT_TARGET_MIN + .030) ? true: false;
	}

	public boolean isStalled() {
		return tiltStalled;
	}

	public void unStall() {
		tiltStalled = false;
	}

	public void setTargetReached(boolean _reached) {
		targetStringReached = _reached;
	}

	public boolean getTargetReached() {
		return targetStringReached;
	}

	public boolean isTiltEStopped() {
		return tiltEStopped;
	}

	public void setTiltEStopped(boolean tiltEStopped) {
		this.tiltEStopped = tiltEStopped;
	}
}