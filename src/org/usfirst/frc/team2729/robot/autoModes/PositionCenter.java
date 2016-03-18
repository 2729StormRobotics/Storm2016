package org.usfirst.frc.team2729.robot.autoModes;

import org.usfirst.frc.team2729.robot.Robot;
import org.usfirst.frc.team2729.robot.commands.Shoot;
import org.usfirst.frc.team2729.robot.commands.ShooterSetTilt;
import org.usfirst.frc.team2729.robot.commands.ShooterSpinDown;
import org.usfirst.frc.team2729.robot.commands.ShooterSpinUp;
import org.usfirst.frc.team2729.robot.commands.Turn;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class PositionCenter extends CommandGroup{
	public PositionCenter(){
		addSequential(new BreachDefenseAuto(100, .20, false));
		addSequential(new Command(){
			@Override
			protected void initialize() {
				// TODO Auto-generated method stub

			}
			@Override
			protected void execute() {
				// TODO Auto-generated method stub
				new Turn(Robot.vision.findCrosshairHorizontalAngle(2));
			}
			@Override
			protected boolean isFinished() {
				// TODO Auto-generated method stub
				return Math.abs(Robot.vision.findCrosshairHorizontalAngle(2)) < 2;
			}
			@Override
			protected void end() {
				// TODO Auto-generated method stub

			}
			@Override
			protected void interrupted() {
				// TODO Auto-generated method stub
				end();
			}
		});
		addSequential(new ShooterSetTilt(Robot.shoot.TILT_TARGET_MAX));
		addSequential(new ShooterSpinUp());
		addSequential(new Shoot());
		addSequential(new ShooterSetTilt(Robot.shoot.TILT_TARGET_MIN));
		addSequential(new ShooterSpinDown());
	}
}
