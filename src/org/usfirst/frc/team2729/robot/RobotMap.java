package org.usfirst.frc.team2729.robot;

public class RobotMap {
	public static final int 
		PORT_JOYSTICK_DRIVE 			= 0,
    	PORT_JOYSTICK_ARMS  			= 1,
    	PORT_JOYSTICK_DEBUG 			= 2;
	
	//Axes
	public static final int
		JOYDRIVE_AXIS_DRIVE_LEFT		 = 1,
		JOYDRIVE_AXIS_DRIVE_RIGHT		 = 3;
	
	//PWM Ports
	public static final int
		PORT_MOTOR_DRIVE_LEFT 			= 0,
		PORT_MOTOR_DRIVE_RIGHT 			= 1,
		PORT_MOTOR_SHOOT_LEFT 			= 2,
		PORT_MOTOR_SHOOT_RIGHT 			= 3,
		PORT_MOTOR_INTAKE_HOR 			= 4,
		PORT_MOTOR_INTAKE_VER 			= 5;
	
	//Digital I/O Ports
	public static final int
		PORT_ENCODER_DRIVE_RIGHT_1      =  0,
		PORT_ENCODER_DRIVE_RIGHT_2      =  1,
		PORT_ENCODER_DRIVE_LEFT_1       =  2,
		PORT_ENCODER_DRIVE_LEFT_2       =  3,
		PORT_ENCODER_SHOOT_LEFT_1       =  4,
		PORT_ENCODER_SHOOT_LEFT_2       =  5,
		PORT_ENCODER_SHOOT_RIGHT_1      =  6,
		PORT_ENCODER_SHOOT_RIGHT_2      =  7;
	
	//Relay
	public static final int PORT_RELAY_COMPRESSOR = 0;
	
	//Solenoids
	public static final int 
		PORT_SHIFT_DRIVE_HIGH	  		= 0,
		PORT_SHIFT_DRIVE_LOW			= 1,
		PORT_SHIFT_HANG_ON		 	 	= 2,
		PORT_SHIFT_HANG_OFF				= 3;
}
