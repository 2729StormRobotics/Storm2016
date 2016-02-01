package org.usfirst.frc.team2729.robot.commands;
import org.usfirst.frc.team2729.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ExtendHanging extends Command{

	
	private double _hang;
	public ExtendHanging(double hang){
		_hang = hang;
		requires(Robot.hang);
	}

	@Override
	protected void execute() {
		Robot.hang.setLeftExtendPower(_hang);
		Robot.hang.setRightExtendPower(_hang);
		
		
	}
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
