
package org.usfirst.frc.team2729.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team2729.robot.commands.ShooterSpinUp;
import org.usfirst.frc.team2729.robot.commands.TankDrive;
import org.usfirst.frc.team2729.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2729.robot.subsystems.HangingSystem;
import org.usfirst.frc.team2729.robot.subsystems.IntakeSystem;
import org.usfirst.frc.team2729.robot.subsystems.Shooter;
import org.usfirst.frc.team2729.robot.subsystems.VisionSystem;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	public static DriveTrain driveTrain;
	public static IntakeSystem intake;
	public static Shooter shoot;
	public static HangingSystem hang;
	//public static VisionSystem vision = new VisionSystem();
	public static OI oi;

	
    Command autonomousCommand;
    SendableChooser chooser;

	public void robotInit() {
		driveTrain = new DriveTrain();
		intake = new IntakeSystem();
		shoot = new Shooter();
		hang = new HangingSystem();
		oi = new OI();
		
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", new TankDrive());
//        chooser.addObject("My Auto", new MyAutoCommand());
        SmartDashboard.putData("Auto mode", chooser);
        SmartDashboard.putNumber("Encoder", driveTrain.getRightSpeedEnc());
      
    }

    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();
        new ShooterSpinUp(15000).start();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    public void testPeriodic() {
        LiveWindow.run();
    }
}
