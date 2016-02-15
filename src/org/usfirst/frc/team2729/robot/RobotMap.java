package org.usfirst.frc.team2729.robot;

public class RobotMap {
	public static final int 
		PORT_JOYSTICK_DRIVE 			= 0,
    	PORT_JOYSTICK_ARMS  			= 1,
    	PORT_JOYSTICK_DEBUG 			= 2;
	
	//Buttons
	public static final int
		JOYDRIVE_BUTTON_HALVE_1			   = 8,
		JOYDRIVE_BUTTON_HALVE_2			   = 7,
		JOYDRIVE_BUTTON_SHIFT_DRIVE_HIGH   = 4,
		JOYDRIVE_BUTTON_SHIFT_DRIVE_LOW    = 2,
		JOYDRIVE_BUTTON_FORWARD			   = 10,
		JOYDRIVE_BUTTON_BACKWARDS		   = 9,
		JOYDRIVE_BUTTON_PTO_ON             = 0,
		JOYDRIVE_BUTTON_PTO_OFF            = 0,
		JOYARM_BUTTON_EXTENDER_UP		   = 6,
		JOYARM_BUTTON_EXTENDER_DOWN		   = 8,
		JOYARM_BUTTON_SHOOTER_SPINUP	   = 5,
		JOYARM_BUTTON_SHOOTER_SPINDOWN	   = 7,
		JOYARM_BUTTON_BEAVER_SP1           = 0,
		JOYARM_BUTTON_BEAVER_SP2           = 0,
		JOYARM_BUTTON_BEAVER_SP3           = 0,
		JOYARM_BUTTON_SHOOT                = 0,
		JOYARM_BUTTON_WINCH_OUT            = 0,
		JOYARM_BUTTON_WINCH_IN             = 0;		
	
	//Axes
	public static final int
		JOYDRIVE_AXIS_DRIVE_LEFT		 = 1,
		JOYDRIVE_AXIS_DRIVE_RIGHT		 = 3,
		JOYARM_AXIS_INTAKE				 = 1,
		JOYARM_AXIS_SHOOT_TILT			 = 3;
	

	//PWM Ports
	public static final int
		PORT_MOTOR_DRIVE_LEFT 			= 6,
		PORT_MOTOR_DRIVE_RIGHT 			= 7,
		PORT_MOTOR_SHOOT_LEFT 			= 0,
		PORT_MOTOR_SHOOT_RIGHT 			= 1,
		PORT_MOTOR_SHOOT_TILT			= 2,
		PORT_MOTOR_INTAKE_HOR 			= 5,
		PORT_MOTOR_INTAKE_VER 			= 3,
		PORT_MOTOR_INTAKE_TILT			= 4,
		PORT_MOTOR_EXTENDER_LEFT 		= 8,
		PORT_MOTOR_EXTENDER_RIGHT		= 9;
	
	// Analog ports
    public static final int PORT_ROTATE_POT		  = 1,
    		                PORT_STRINGPOT		  = 0;
	
	//Digital I/O Ports
	public static final int
		PORT_ENCODER_DRIVE_RIGHT_1      =  6,
		PORT_ENCODER_DRIVE_RIGHT_2      =  7,
		PORT_ENCODER_DRIVE_LEFT_1       =  4,
		PORT_ENCODER_DRIVE_LEFT_2       =  5,
		PORT_ENCODER_SHOOT_LEFT_1       =  0,
		PORT_ENCODER_SHOOT_LEFT_2       =  1,
		PORT_ENCODER_SHOOT_RIGHT_1      =  2,
		PORT_ENCODER_SHOOT_RIGHT_2      =  3,
		PORT_SHOOTER_SWITCH_MIN_TILT	=  9,
		PORT_LIMIT_SWITCH_INTAKE_HALT	=  8;
	
	//Relay
	public static final int PORT_RELAY_COMPRESSOR = 0;
	
	//Solenoids
	public static final int 
		PORT_SHIFT_DRIVE_HIGH	  		= 0,
		PORT_SHIFT_DRIVE_LOW			= 1,
		PORT_SHIFT_HANG_ON		 	 	= 2,
		PORT_SHIFT_HANG_OFF				= 3;
}