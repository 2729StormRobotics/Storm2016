package org.usfirst.frc.team2729.robot.autoModes;

import org.usfirst.frc.team2729.robot.commands.IntakeTilt;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ManualDriveLowBar extends CommandGroup{
	public ManualDriveLowBar(){
		addSequential(new IntakeTilt(false));
		addSequential(new ManualDriveAuto(-.4, 5, false));
	}
}
