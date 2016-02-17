package org.usfirst.frc.team2729.robot.autoModes;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class PositionCenterRight extends CommandGroup {
	public PositionCenterRight(){
		addSequential(new BreachDefenseAuto(700,5));
	}
	

}
