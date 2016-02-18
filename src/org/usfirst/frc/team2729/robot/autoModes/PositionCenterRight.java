package org.usfirst.frc.team2729.robot.autoModes;

import org.usfirst.frc.team2729.robot.commands.Shoot;
import org.usfirst.frc.team2729.robot.commands.ShootTiltToAngle;
import org.usfirst.frc.team2729.robot.commands.Turn;


import edu.wpi.first.wpilibj.command.CommandGroup;

public class PositionCenterRight extends CommandGroup {
	public PositionCenterRight(){
		addSequential(new BreachDefenseAuto(700,5));//TODO: Determine Experimentally
		addSequential(new Turn(-20)); //TODO: Determine Experimentally
		addSequential(new ShootTiltToAngle(45)); //TODO: Determine Experimentally
		addSequential(new Shoot()); 

	}
	

}
