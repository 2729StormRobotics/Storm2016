package org.usfirst.frc.team2729.robot.autoModes;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

import org.usfirst.frc.team2729.robot.Robot;
import org.usfirst.frc.team2729.robot.autoModes.BreachDefenseAuto;
import org.usfirst.frc.team2729.robot.commands.IntakeTilt;
import org.usfirst.frc.team2729.robot.commands.Shoot;
import org.usfirst.frc.team2729.robot.commands.ShooterSetTilt;
import org.usfirst.frc.team2729.robot.commands.ShooterSpinDown;
import org.usfirst.frc.team2729.robot.commands.ShooterSpinUp;
import org.usfirst.frc.team2729.robot.commands.Turn;

public class BreachLowBarAuto extends CommandGroup{
	public BreachLowBarAuto(){
		addSequential(new IntakeTilt(false));
		addSequential(new WaitCommand(1));
		addSequential(new BreachDefenseAuto(3000, .25));
	}
}
