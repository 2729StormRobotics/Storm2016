package org.usfirst.frc.team2729.robot.autoModes;

import org.usfirst.frc.team2729.robot.Robot;
import org.usfirst.frc.team2729.robot.commands.Shoot;
import org.usfirst.frc.team2729.robot.commands.ShooterSetTilt;
import org.usfirst.frc.team2729.robot.commands.ShooterSpinDown;
import org.usfirst.frc.team2729.robot.commands.ShooterSpinUp;
import org.usfirst.frc.team2729.robot.commands.Turn;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PositionCenter extends CommandGroup{
	public PositionCenter(){
		//addSequential(new BreachDefenseAuto(100, .20, false));
		//double angle = 
		addSequential(new Turn(Math.abs(Robot.vision.findCrosshairHorizontalAngle(3))> 90 ? 0 : Robot.vision.findCrosshairHorizontalAngle(3), .40));
		//SmartDashboard.putNumber("JKAFDHJHDAWHDIAWHDJAWHDI", Robot.vision.findCrosshairHorizontalAngle(3));
		//addSequential(new Turn(Math.abs(Robot.vision.findCrosshairHorizontalAngle(3))> 90 ? 0 : Robot.vision.findCrosshairHorizontalAngle(3), .30));
		//addSequential(new Turn(Math.abs(Robot.vision.findCrosshairHorizontalAngle(3))> 90 ? 0 : Robot.vision.findCrosshairHorizontalAngle(3), .20));
		//addSequential(new ShooterSetTilt(Robot.shoot.TILT_TARGET_MAX));
		//addSequential(new ShooterSpinUp());
		//addSequential(new Shoot());
		//addSequential(new ShooterSpinDown());
		//addSequential(new ShooterSetTilt(Robot.shoot.TILT_TARGET_MIN));
	}
}
