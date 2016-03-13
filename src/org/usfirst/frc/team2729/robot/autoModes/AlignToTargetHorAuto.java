package org.usfirst.frc.team2729.robot.autoModes;

import edu.wpi.first.wpilibj.command.Command;

public class AlignToTargetHorAuto extends Command {
	private double target = 0;
	private double setRobotAngle;

	public AlignToTargetHorAuto(){

	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
		/*if (Robot.vision.getTargetFound()){

			if (Robot.vision.getHorizontalAngle()>0){
			double err = target - Robot.vision.getHorizontalAngle();
			double Kp = 0.0001;
			setRobotAngle = Kp*err;
			Robot.driveTrain.TankDrive(-setRobotAngle, setRobotAngle);

			}
			else {
				double err = target - Robot.vision.getHorizontalAngle();
				double Kp = 0.0001;
				setRobotAngle = Kp*err;
				Robot.driveTrain.TankDrive(setRobotAngle, -setRobotAngle);
			}
		}
		else {
		Robot.driveTrain.TankDrive(0,0);
		}*/
	}

	@Override
	protected boolean isFinished() {
		return false;

	}

	@Override
	protected void end() {


	}

	@Override
	protected void interrupted() {

	}

}
