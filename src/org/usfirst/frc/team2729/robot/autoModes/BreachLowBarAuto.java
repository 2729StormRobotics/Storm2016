package org.usfirst.frc.team2729.robot.autoModes;

import org.usfirst.frc.team2729.robot.commands.IntakeTilt;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class BreachLowBarAuto extends CommandGroup{
	public BreachLowBarAuto(){
		addSequential(new IntakeTilt(false));
		addSequential(new WaitCommand(1));
		addSequential(new PIDDriveAuto(-.25, 4, true));
		addSequential(new AlignTurn(.6));
		addSequential(new AlignTurn(.4));
		addSequential(new AlignTurn(.2));
	}
}
