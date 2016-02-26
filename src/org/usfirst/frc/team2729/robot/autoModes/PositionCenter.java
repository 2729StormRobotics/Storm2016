package org.usfirst.frc.team2729.robot.autoModes;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team2729.robot.Robot;
import org.usfirst.frc.team2729.robot.autoModes.BreachDefenseAuto;
import org.usfirst.frc.team2729.robot.commands.Shoot;
import org.usfirst.frc.team2729.robot.commands.ShooterSetTilt;
import org.usfirst.frc.team2729.robot.commands.ShooterSpinDown;
import org.usfirst.frc.team2729.robot.commands.ShooterSpinUp;
import org.usfirst.frc.team2729.robot.commands.Turn;

public class PositionCenter extends CommandGroup{
	public PositionCenter(){
		addSequential(new BreachDefenseAuto(100, .20));
		addSequential(new Turn(0));
		addSequential(new ShooterSetTilt(Robot.shoot.TiltMax));
		addSequential(new ShooterSpinUp());
		addSequential(new Shoot());
		addSequential(new ShooterSetTilt(Robot.shoot.TiltMin));
		addSequential(new ShooterSpinDown());
	}
}
