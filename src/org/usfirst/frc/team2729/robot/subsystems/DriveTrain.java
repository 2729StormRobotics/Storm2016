
package org.usfirst.frc.team2729.robot.subsystems;

import org.usfirst.frc.team2729.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
    
	private final Talon _left = new Talon(RobotMap.PORT_MOTOR_DRIVE_LEFT),
						_right= new Talon(RobotMap.PORT_MOTOR_DRIVE_RIGHT);
	private final Encoder _leftEncoder = new Encoder(RobotMap.PORT_ENCODER_LEFT_1, RobotMap.PORT_ENCODER_LEFT_2),
						  _rightEncoder = new Encoder(RobotMap.PORT_ENCODER_RIGHT_1, RobotMap.PORT_ENCODER_RIGHT_2);;
	private final DoubleSolenoid _shifter = new DoubleSolenoid(RobotMap.PORT_SHIFT_DRIVE_HIGH, RobotMap.PORT_SHIFT_DRIVE_LOW);
	
	public DriveTrain(){
		
	}
	
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

