package org.usfirst.frc.team2729.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team2729.robot.autoModes.BreachDefenseAuto;
import org.usfirst.frc.team2729.robot.autoModes.DoNothing;
import org.usfirst.frc.team2729.robot.autoModes.PositionCenter;
import org.usfirst.frc.team2729.robot.autoModes.PositionCenterLeft;
import org.usfirst.frc.team2729.robot.autoModes.PositionCenterRight;
import org.usfirst.frc.team2729.robot.autoModes.PositionLeft;
import org.usfirst.frc.team2729.robot.autoModes.PositionRight;
import org.usfirst.frc.team2729.robot.commands.ShooterSpinUp;
import org.usfirst.frc.team2729.robot.commands.TankDrive;
import org.usfirst.frc.team2729.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2729.robot.subsystems.HangingSystem;
import org.usfirst.frc.team2729.robot.subsystems.IntakeSystem;
import org.usfirst.frc.team2729.robot.subsystems.ShootingSystem;
import org.usfirst.frc.team2729.robot.subsystems.VisionSystem;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	public static DriveTrain driveTrain;
	public static IntakeSystem intake;
	public static ShootingSystem shoot;
	public static HangingSystem hang;
	//public static VisionSystem vision;
	public static OI oi;
	private Compressor compressor;
	
    Command autonomousCommand;
    SendableChooser chooser;

	public void robotInit() {
		Command autoCommand;
		String[] autoModeNames;
		Command[] autoModes;
		driveTrain = new DriveTrain();
		intake = new IntakeSystem();
		shoot = new ShootingSystem();
		hang = new HangingSystem();
		oi = new OI();
		//vision = new VisionSystem();
		compressor = new Compressor();
		compressor.start();
        chooser = new SendableChooser();
        
		autoModeNames = new String[]{"Do Nothing","Drive To Defense", "Drive to Defense Backwards","Position Center Left", "Position Left", "Position Center", "Position Center Right", "Position Right" };
		autoModes = new Command[]{new DoNothing(), new BreachDefenseAuto(3000,.4), new BreachDefenseAuto(-3000, .4), new PositionCenterLeft(), new PositionLeft(), new PositionCenter(), new PositionCenterRight(), new PositionRight()};
		
		//configure and send the sendableChooser, which allows the modes
		//to be chosen via radio button on the SmartDashboard
		for(int i = 0; i < autoModes.length; ++i){
			chooser.addObject(autoModeNames[i], autoModes[i]);
		}
        SmartDashboard.putData("Auto mode", chooser);
        SmartDashboard.putNumber("Encoder", driveTrain.getRightSpeedEnc());
    }

    public void disabledInit(){
    	Robot.shoot.setTargetSpeed(0);
    	Robot.shoot.setTargetTilt(Robot.shoot.getShooterPotRAW());
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
        sendSensorData();
    	Robot.shoot.setTargetTilt(Robot.shoot.getShooterPotRAW());
        //Robot.vision.addCrosshairs();
	}

	public void sendSensorData() {
		SmartDashboard.putNumber("Right Encoder", driveTrain.getRightDistance());
		SmartDashboard.putNumber("Left Encoder", driveTrain.getLeftDistance());
		//SmartDashboard.putNumber("Rot Pot", intake.getPot());
		//SmartDashboard.putNumber("Motor", intake.getTiltPower());
		SmartDashboard.putNumber("Raw String Pot", Robot.shoot.getShooterPotRAW());
		//SmartDashboard.putNumber("String Pot Length", Robot.shoot.getShooterPotLength());
		//SmartDashboard.putNumber("String Pot Angle", Robot.shoot.getShooterAngle());
		SmartDashboard.putBoolean("Is Shooter MAX", Robot.shoot.isMax());
		Robot.shoot.getShooterAngleLSLR();
		//Robot.shoot.getShooterAnglePR();
		SmartDashboard.putNumber("Robot.shoot.TiltPower()",Robot.shoot.getTiltPower());
		SmartDashboard.putBoolean("Intake Halt", Robot.shoot.getIntakeHalt());
		SmartDashboard.putBoolean("High Gear", Robot.driveTrain.getHighGear());
		SmartDashboard.putBoolean("PTO On", Robot.driveTrain.getPTO());
		SmartDashboard.putBoolean("Max Tilt", Robot.shoot.isMax());
		SmartDashboard.putBoolean("Tilt Stalled", Robot.shoot.isStalled());
		SmartDashboard.putBoolean("Tilt E-Stopped", Robot.shoot.isTiltEStopped());
		SmartDashboard.putBoolean("Tilt Out of Bounds", Robot.shoot.isOutBounds());
		//SmartDashboard.putBoolean("Intake Top", (Robot.intake.getPot()<0.850 && Robot.intake.getPot()>0.775 ? true : false));
		//SmartDashboard.putBoolean("Intake Mid", (Robot.intake.getPot()<0.500 && Robot.intake.getPot()>0.490 ? true : false));
		//SmartDashboard.putBoolean("Intake Bottom", (Robot.intake.getPot()<0.411 && Robot.intake.getPot()>0.401 ? true : false));
		SmartDashboard.putBoolean("Tilt Max", (Robot.shoot.getShooterTilt()<.320 && Robot.shoot.getShooterTilt()>.310 ? true : false));
		SmartDashboard.putBoolean("Tilt High", (Robot.shoot.getShooterTilt()<.334 && Robot.shoot.getShooterTilt()>.324 ? true : false));
		SmartDashboard.putBoolean("Tilt Medium", (Robot.shoot.getShooterTilt()<.357 && Robot.shoot.getShooterTilt()>.347 ? true : false));
		SmartDashboard.putBoolean("Tilt Intake", (Robot.shoot.getShooterTilt()<.393 && Robot.shoot.getShooterTilt()>.383 ? true : false));
		SmartDashboard.putBoolean("Tilt Low", (Robot.shoot.getShooterTilt()<.560 && Robot.shoot.getShooterTilt()>.550 ? true : false));
		SmartDashboard.putBoolean("Tilt Min", (Robot.shoot.getShooterTilt()<.569 && Robot.shoot.getShooterTilt()>.559 ? true : false));
		SmartDashboard.putBoolean("Transitioning", (Robot.shoot.getTiltPower()>.01 ? true : false));
		SmartDashboard.putBoolean("String Target Point", Robot.shoot.getTargetReached());
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
        sendSensorData();
        //Robot.vision.addCrosshairs();
    }

    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();
        Robot.shoot.unStall();
        Robot.shoot.setTiltEStopped(false);
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        sendSensorData();
        //Robot.vision.addCrosshairs();
    }
    
    public void testPeriodic() {
        LiveWindow.run();
        ///Robot.vision.addCrosshairs();
    }
}