package org.usfirst.frc.team2729.robot.autoModes;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2729.robot.autoModes.BreachDefenseAuto;
import org.usfirst.frc.team2729.robot.commands.Shoot;
import org.usfirst.frc.team2729.robot.commands.ShootTiltToAngle;
import org.usfirst.frc.team2729.robot.commands.Turn;

public class PositionCenter extends CommandGroup{
	public PositionCenter(){
		addSequential(new BreachDefenseAuto(100, .20));
		addSequential(new Turn(0));
		addSequential(new ShootTiltToAngle(45));
		addSequential(new Shoot());
	}
}
