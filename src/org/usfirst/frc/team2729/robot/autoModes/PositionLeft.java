package org.usfirst.frc.team2729.robot.autoModes;

import org.usfirst.frc.team2729.robot.commands.Shoot;
import org.usfirst.frc.team2729.robot.commands.ShootTiltToAngle;
import org.usfirst.frc.team2729.robot.commands.Turn;


import edu.wpi.first.wpilibj.command.CommandGroup;

public class PositionLeft extends CommandGroup {
	public PositionLeft(){
		addSequential(new BreachDefenseAuto(700,5));
		addSequential(new Turn(70));
		addSequential(new ShootTiltToAngle(45));
		addSequential(new Shoot());

	}
	

}