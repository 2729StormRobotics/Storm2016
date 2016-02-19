package org.usfirst.frc.team2729.robot.subsystems;

import java.util.Comparator;
import java.util.Vector;

import org.usfirst.frc.team2729.robot.commands.Vision;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class VisionSystem extends Subsystem {
	
	//Return variables
	boolean targetDetected;
	double targetDistance, towerDistance;
	double targetVerticalAngle, targetHorizontalAngle;
	
	//A structure to hold measurements of a particle
	public class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport>{
		double PercentAreaToImageArea;
		double Area;
		double BoundingRectLeft;
		double BoundingRectTop;
		double BoundingRectRight;
		double BoundingRectBottom;
		
		public int compareTo(ParticleReport r)
		{
			return (int)(r.Area - this.Area);
		}
	
		public int compare(ParticleReport r1, ParticleReport r2)
		{
			return (int)(r1.Area - r2.Area);
		}
	};

	//Structure to represent the scores for the various tests used for target identification
	public class Scores {
		double Area;
		double Aspect;
	};
				
	//Session
	int session;

	//Images
	Image frame;
	Image binaryFrame;
	int imaqError;
	
	//Crosshair
	NIVision.Rect rect = new NIVision.Rect(590, 310, 100, 100);

	//Constants
	NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(101, 64);	//Default hue range for target
	NIVision.Range TARGET_SAT_RANGE = new NIVision.Range(88, 255);	//Default saturation range for target
	NIVision.Range TARGET_VAL_RANGE = new NIVision.Range(134, 255);	//Default value range for target
	double AREA_MINIMUM = 0.5; //Default Area minimum for particle as a percentage of total image area
	double SCORE_MIN = 75.0;  //Minimum score to be considered a target
	double VIEW_ANGLE = 60; //View angle for camera, set to 49.4 for Axis m1011 by default, 64 for m1013, 51.7 for 206, 52 for HD3000 square, 60 for HD3000 640x480
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
	Scores scores = new Scores();
	
	public VisionSystem(){
		//Create images
		frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM, 100.0, 0, 0);

		//The camera name (ex "cam0") can be found through the roborio web interface
		
		//add catch exception
		
		session = NIVision.IMAQdxOpenCamera("cam1", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
        
		NIVision.IMAQdxStartAcquisition(session);
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new Vision());
	}
	
	public boolean getTargetFound(){
		return targetDetected;
	}
	
	public double getDistanceToTarget(){
		return targetDistance;
	}
	
	public double getDistanceToTower(){
		return towerDistance;
	}
	
	public double getVerticalAngle(){
		return targetVerticalAngle;
	}
	
	public double getHorizontalAngle(){
		return targetHorizontalAngle;
	}

	public void detectTarget(){
		NIVision.IMAQdxGrab(session, frame, 1);
		 
		//Threshold the image looking for green (retroreflective target color)
		NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, TARGET_HUE_RANGE, TARGET_SAT_RANGE, TARGET_VAL_RANGE);

		//Send particle count to dashboard
		int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		//SmartDashboard.putNumber("Masked particles", numParticles);

		//Add crosshair
		NIVision.imaqDrawShapeOnImage(frame, frame, rect, DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);
		
		//Send image to dashboard to assist drivers
		CameraServer.getInstance().setImage(frame);
		
		//Send masked image to dashboard to assist in tweaking mask
		//CameraServer.getInstance().setImage(binaryFrame);

		//filter out small particles
		criteria[0].lower = (float) AREA_MINIMUM;
		imaqError = NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null);

		//Send particle count after filtering to dashboard
		numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		//SmartDashboard.putNumber("Filtered particles", numParticles);

		if(numParticles > 0)
		{
			//Measure particles and sort by particle size
			Vector<ParticleReport> particles = new Vector<ParticleReport>();
			for(int particleIndex = 0; particleIndex < numParticles; particleIndex++)
			{
				ParticleReport par = new ParticleReport();
				par.PercentAreaToImageArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
				par.Area = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
				par.BoundingRectTop = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
				par.BoundingRectLeft = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
				par.BoundingRectBottom = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
				par.BoundingRectRight = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
				particles.add(par);
			}
			particles.sort(null);
			
			//Scores the largest particle
			scores.Aspect = AspectScore(particles.elementAt(0));
			//SmartDashboard.putNumber("Aspect", scores.Aspect);
			scores.Area = AreaScore(particles.elementAt(0));
			//SmartDashboard.putNumber("Area", scores.Area);
			
			//Set values
			targetDetected = scores.Aspect > SCORE_MIN && scores.Area > SCORE_MIN;
			targetDistance = computeTargetDistance(binaryFrame, particles.elementAt(0));
			targetHorizontalAngle = computeHorizontalAngle(binaryFrame, particles.elementAt(0));
			targetVerticalAngle = computeVerticalAngle(binaryFrame, particles.elementAt(0));
			towerDistance = computeTowerDistance();

			//Send distance and target status to dashboard. The bounding rect, particularly the horizontal center (left - right) may be useful for rotating/driving towards a target
			//Send distance and target status to dashboard. The bounding rect, particularly the horizontal center (left - right) may be useful for rotating/driving towards a target
			//SmartDashboard.putBoolean("Target Detected", targetDetected);
			//SmartDashboard.putNumber("Distance to Target", targetDistance);
			//SmartDashboard.putNumber("Horizontal Angle to Target", targetHorizontalAngle);
			//SmartDashboard.putNumber("Vertical Angle to Target", targetVerticalAngle);
			//SmartDashboard.putNumber("Distance to Tower", towerDistance);
		} else {
			targetDetected = false;
			//SmartDashboard.putBoolean("Target Detected", targetDetected);
		}
	}

	//Comparator function for sorting particles. Returns true if particle 1 is larger
  	private static boolean CompareParticleSizes(ParticleReport particle1, ParticleReport particle2)
  	{
  		//we want descending sort order
  		return particle1.PercentAreaToImageArea > particle2.PercentAreaToImageArea;
  	}

  	/**
	 * Converts a ratio with ideal value of 1 to a score. The resulting function is piecewise
	 * linear going from (0,0) to (1,100) to (2,0) and is 0 for all inputs outside the range 0-2
	 */
  	private double ratioToScore(double ratio)
	{
		return (Math.max(0, Math.min(100*(1-Math.abs(1-ratio)), 100)));
	}

  	private double AreaScore(ParticleReport report)
	{
		double boundingArea = (report.BoundingRectBottom - report.BoundingRectTop) * (report.BoundingRectRight - report.BoundingRectLeft);
		//Tape is 12" by 20" edge so 240" bounding rect. With 2" wide tape it covers 80" of the rect.
		return ratioToScore((240/80)*report.Area/boundingArea);
	}

	/**
	 * Method to score if the aspect ratio of the particle appears to match the retro-reflective target
	 */
  	private double AspectScore(ParticleReport report)
	{
		return ratioToScore((12/20)*((report.BoundingRectRight-report.BoundingRectLeft)/(report.BoundingRectBottom-report.BoundingRectTop)));
	}

	/**
	 * Computes the estimated distance to a target using the width of the particle in the image. For more information and graphics
	 * showing the math behind this approach see the Vision Processing section of the ScreenStepsLive documentation.
	 *
	 * @param image The image to use for measuring the particle estimated rectangle
	 * @param report The Particle Analysis Report for the particle
	 * @return The estimated distance to the target in feet.
	 */
  	private double computeTargetDistance (Image image, ParticleReport report) {
		double normalizedWidth, targetWidth;
		NIVision.GetImageSizeResult size;

		size = NIVision.imaqGetImageSize(image);
		normalizedWidth = 2*(report.BoundingRectRight - report.BoundingRectLeft)/size.width;
		targetWidth = 20;
		
		//targetWidth*size.width/(2*(report.BoundingRectRight - report.BoundingRectLeft)*tan(viewangle))
		return targetWidth/(normalizedWidth*12*Math.tan(VIEW_ANGLE*Math.PI/(180*2))) * 0.0254;
	}
  	
  	private double computeHorizontalAngle (Image image, ParticleReport report) {
  		NIVision.GetImageSizeResult size;
		size = NIVision.imaqGetImageSize(image);
  		
		//(int) (((((2 * rec1.tl().x + rec1.width)) / original.width()) - 1) * (_fieldOfViewH.getValue()/2));
  		return (report.BoundingRectLeft + (report.BoundingRectRight - report.BoundingRectLeft)/2)/size.width * VIEW_ANGLE;
  	}
  	
  	private double computeVerticalAngle (Image image, ParticleReport report) {
  		NIVision.GetImageSizeResult size;
		size = NIVision.imaqGetImageSize(image);
  		
		//until vertical view angle is determined, using 2/3 horizontal view angle
		//(int) (((((2 * rec1.tl().x + rec1.width)) / original.width()) - 1) * (_fieldOfViewH.getValue()/2));
  		return (report.BoundingRectBottom - (report.BoundingRectBottom - report.BoundingRectTop)/2)/size.height * (VIEW_ANGLE * 2/3);
  	}
  	
  	private double computeTowerDistance () {
  		return targetDistance * Math.cos(targetVerticalAngle);
  	}
}