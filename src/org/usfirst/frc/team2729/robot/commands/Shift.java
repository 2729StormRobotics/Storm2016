package org.usfirst.frc.team2729.robot.commands;

import org.usfirst.frc.team2729.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Shift extends Command {
    private final boolean _high;
    
    public Shift(boolean high) {
    	requires(Robot.driveTrain);
        _high = high;
    }
    
    protected void initialize() {Robot.driveTrain.setHighGear(_high);}
    protected void execute() {}
    protected boolean isFinished() {return true;}
    protected void end() {}
    protected void interrupted() {}
}
