package org.usfirst.frc.team2729.robot.commands;
import org.usfirst.frc.team2729.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

public class ExtendHangingAuto extends Command{
	
	private double _hang, _time;
	private Timer _timer = new Timer();
	public ExtendHangingAuto(double hang, double time){
		_hang = hang;
		_time = time;
		requires(Robot.hang);
	}

	@Override
	protected void execute() {
		Robot.hang.setExtendPower(_hang);
	}
	
	protected void initialize() {
		_timer.start();
	}

	@Override
	protected boolean isFinished() {
		return (_timer.get() < _time) ? false : true;
	}

	@Override
	protected void end() {
		Robot.hang.setExtendPower(0);
	}

	@Override
	protected void interrupted() {
		Robot.hang.setExtendPower(0);
	}
}