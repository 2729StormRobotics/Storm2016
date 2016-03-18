package org.usfirst.frc.team2729.robot.autoModes;

import org.usfirst.frc.team2729.robot.Robot;
import org.usfirst.frc.team2729.robot.commands.Shoot;
import org.usfirst.frc.team2729.robot.commands.ShooterSetTilt;
import org.usfirst.frc.team2729.robot.commands.ShooterSpinDown;
import org.usfirst.frc.team2729.robot.commands.ShooterSpinUp;
import org.usfirst.frc.team2729.robot.commands.Turn;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PositionCenterRight extends CommandGroup {
	public PositionCenterRight(){
		addSequential(new BreachDefenseAuto(700,5,false));//TODO: Determine Experimentally
		addSequential(new Turn(-20)); //TODO: Determine Experimentally
		addSequential(new ShooterSetTilt(Robot.shoot.TILT_TARGET_MAX)); //TODO: Determine Experimentally
		addSequential(new ShooterSpinUp());
		addSequential(new Shoot());
		addSequential(new ShooterSetTilt(Robot.shoot.TILT_TARGET_MIN));
		addSequential(new ShooterSpinDown());
	}
}
