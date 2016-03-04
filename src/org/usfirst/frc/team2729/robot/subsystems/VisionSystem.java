package org.usfirst.frc.team2729.robot.subsystems;

import java.util.Comparator;
import java.util.Vector;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.Point;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.AxisCamera;

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
	
	//Camera
	AxisCamera camera;

	//Images
	Image frame;
	Image binaryFrame;
	Image testFrame;
	int imaqError;
	
	//Crosshair
	Point crosshairTopLeft[] = {new Point(320-50,240-50), new Point(390-50,225-50), new Point(400-50,330-50), new Point(420-50,390-50)};
	Point crosshairBottomLeft[] = {new Point(320-50,240), new Point(390-50,225), new Point(400-50,330), new Point(420-50,390)};
	Point crosshairTopRight[] = {new Point(320+50,240-50), new Point(390+50,225-50), new Point(400+50,330-50), new Point(420+50,390-50)};
	Point crosshairBottomRight[] = {new Point(320+50,240), new Point(390+50,225), new Point(400+50,330), new Point(420+50,390)};
	Point crosshairLeft[] = {new Point(320-50,240), new Point(390-50,225), new Point(400-50,330), new Point(420-50,390)};
	Point crosshairRight[] = {new Point(320+50,240), new Point(390+50,225), new Point(400+50,330), new Point(420+50,390)};
	double proximity[] = {0,0,0,0};
	
	//Ideal values
	double IDEAL_DISTANCE[] = {4, 4, 4}; //86
	double IDEAL_HORIZONTAL_ANGLE[] = {0, 0, 0};
	double IDEAL_VERTICAL_ANGLE[] = {0, 0, 0};	

	//Constants
	NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(70, 90);	//Default hue range for target //50 140
	NIVision.Range TARGET_SAT_RANGE = new NIVision.Range(100, 140);	//Default saturation range for target //100 255
	NIVision.Range TARGET_VAL_RANGE = new NIVision.Range(250, 255);	//Default value range for target
	double AREA_MINIMUM = 0.5; //Default Area minimum for particle as a percentage of total image area
	double SCORE_MIN = 50.0;  //Minimum score to be considered a target
	double VIEW_ANGLE = 60; //View angle for camera, set to 49.4 for Axis m1011 by default, 64 for m1013, 51.7 for 206, 52 for HD3000 square, 60 for HD3000 640x480
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
	Scores scores = new Scores();
	
	//Robot preferences
	Preferences prefs;
	
	//SendableChooser
	SendableChooser chooser;
	
	public VisionSystem(){
		//Create images
    	frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
    	binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		testFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
    	criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM, 100.0, 0, 0);

    	//The camera name (ex "cam0") can be found through the roborio web interface
    	session = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
    	NIVision.IMAQdxConfigureGrab(session);
    	NIVision.IMAQdxStartAcquisition(session);
    	
    	//switch cameras with button
    	//camera = new AxisCamera("axis-camera.local");
    	
    	//TESTING
    	/*
    	prefs = Preferences.getInstance();
    	crosshairTop[0].x = (int) prefs.getDouble("Crosshair 0 X", 320);
		crosshairTop[0].y = (int) prefs.getDouble("Crosshair 0 Y", 240) - 50;
		crosshairBottom[0].x = (int) prefs.getDouble("Crosshair 0 X", 320);
		crosshairBottom[0].y = (int) prefs.getDouble("Crosshair 0 Y", 240) + 50;
		crosshairLeft[0].x = (int) prefs.getDouble("Crosshair 0 X", 320) - 50;
		crosshairLeft[0].y = (int) prefs.getDouble("Crosshair 0 Y", 240);
		crosshairRight[0].x = (int) prefs.getDouble("Crosshair 0 X", 320) + 50;
		crosshairRight[0].y = (int) prefs.getDouble("Crosshair 0 Y", 240);
		
    	crosshairTop[1].x = (int) prefs.getDouble("Crosshair 1 X", 320);
		crosshairTop[1].y = (int) prefs.getDouble("Crosshair 1 Y", 240) - 50;
		crosshairBottom[1].x = (int) prefs.getDouble("Crosshair 1 X", 320);
		crosshairBottom[1].y = (int) prefs.getDouble("Crosshair 1 Y", 240) + 50;
		crosshairLeft[1].x = (int) prefs.getDouble("Crosshair 1 X", 320) - 50;
		crosshairLeft[1].y = (int) prefs.getDouble("Crosshair 1 Y", 240);
		crosshairRight[1].x = (int) prefs.getDouble("Crosshair 1 X", 320) + 50;
		crosshairRight[1].y = (int) prefs.getDouble("Crosshair 1 Y", 240);
		
		crosshairTop[2].x = (int) prefs.getDouble("Crosshair 2 X", 320);
		crosshairTop[2].y = (int) prefs.getDouble("Crosshair 2 Y", 240) - 50;
		crosshairBottom[2].x = (int) prefs.getDouble("Crosshair 2 X", 320);
		crosshairBottom[2].y = (int) prefs.getDouble("Crosshair 2 Y", 240) + 50;
		crosshairLeft[2].x = (int) prefs.getDouble("Crosshair 2 X", 320) - 50;
		crosshairLeft[2].y = (int) prefs.getDouble("Crosshair 2 Y", 240);
		crosshairRight[2].x = (int) prefs.getDouble("Crosshair 2 X", 320) + 50;
		crosshairRight[2].y = (int) prefs.getDouble("Crosshair 2 Y", 240);
		
		crosshairTop[3].x = (int) prefs.getDouble("Crosshair 3 X", 320);
		crosshairTop[3].y = (int) prefs.getDouble("Crosshair 3 Y", 240) - 50;
		crosshairBottom[3].x = (int) prefs.getDouble("Crosshair 3 X", 320);
		crosshairBottom[3].y = (int) prefs.getDouble("Crosshair 3 Y", 240) + 50;
		crosshairLeft[3].x = (int) prefs.getDouble("Crosshair 3 X", 320) - 50;
		crosshairLeft[3].y = (int) prefs.getDouble("Crosshair 3 Y", 240);
		crosshairRight[3].x = (int) prefs.getDouble("Crosshair 3 X", 320) + 50;
		crosshairRight[3].y = (int) prefs.getDouble("Crosshair 3 Y", 240);
		*/
    	
    	//add chooser
    	chooser = new SendableChooser();
    	chooser.addDefault("Crosshair 1", 1);
        chooser.addObject("Crosshair 2", 2);
        chooser.addObject("Crosshair 3", 3);
        SmartDashboard.putData("Crosshair", chooser);
	}
	
	protected void initDefaultCommand() {
		
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
	
	public void addCrosshairs(){
		NIVision.IMAQdxGrab(session, frame, 1);
		
		//Add crosshairs
		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, crosshairTopLeft[0], crosshairBottomLeft[0], 0f);
		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairTopLeft[0].x+1, crosshairTopLeft[0].y), new Point(crosshairBottomLeft[0].x+1, crosshairBottomLeft[0].y), 0f);
		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairTopLeft[0].x-1, crosshairTopLeft[0].y), new Point(crosshairBottomLeft[0].x-1, crosshairBottomLeft[0].y), 0f);
		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, crosshairTopRight[0], crosshairBottomRight[0], 0f);
		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairTopRight[0].x+1, crosshairTopRight[0].y), new Point(crosshairBottomRight[0].x+1, crosshairBottomRight[0].y), 0f);
		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairTopRight[0].x-1, crosshairTopRight[0].y), new Point(crosshairBottomRight[0].x-1, crosshairBottomRight[0].y), 0f);
		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, crosshairLeft[0], crosshairRight[0], 0f);
		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairLeft[0].x, crosshairLeft[0].y+1), new Point(crosshairRight[0].x, crosshairRight[0].y+1), 0f);
		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairLeft[0].x, crosshairLeft[0].y-1), new Point(crosshairRight[0].x, crosshairRight[0].y-1), 0f);
		
		for(int i=1; i<4; i++){
			NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, crosshairTopLeft[i], crosshairBottomLeft[i], 255f);
			NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairTopLeft[i].x+1, crosshairTopLeft[i].y), new Point(crosshairBottomLeft[i].x+1, crosshairBottomLeft[i].y), 255f);
			NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairTopLeft[i].x-1, crosshairTopLeft[i].y), new Point(crosshairBottomLeft[i].x-1, crosshairBottomLeft[i].y), 255f);
			NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, crosshairTopRight[i], crosshairBottomRight[i], 255f);
			NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairTopRight[i].x+1, crosshairTopRight[i].y), new Point(crosshairBottomRight[i].x+1, crosshairBottomRight[i].y), 255f);
			NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairTopRight[i].x-1, crosshairTopRight[i].y), new Point(crosshairBottomRight[i].x-1, crosshairBottomRight[i].y), 255f);
			NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, crosshairLeft[i], crosshairRight[i], 255f);
			NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairLeft[i].x, crosshairLeft[i].y+1), new Point(crosshairRight[i].x, crosshairRight[i].y+1), 255f);
			NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairLeft[i].x, crosshairLeft[i].y-1), new Point(crosshairRight[i].x, crosshairRight[i].y-1), 255f);
		}
		
		//Send image to dashboard to assist drivers
		CameraServer.getInstance().setImage(frame);
	}
	
	public void processImage(){
		NIVision.IMAQdxGrab(session, frame, 1);
    	//camera.getImage(frame);

		//Threshold the image looking for green (retroreflective target color)
		NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, TARGET_HUE_RANGE, TARGET_SAT_RANGE, TARGET_VAL_RANGE);
		
		//TESTING
		//NIVision.imaqColorThreshold(testFrame, frame, 255, NIVision.ColorMode.HSV, TARGET_HUE_RANGE, TARGET_SAT_RANGE, TARGET_VAL_RANGE);

		//Send particle count to dashboard
		int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		//SmartDashboard.putNumber("Masked particles", numParticles);
		
		//Add crosshairs
		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, crosshairTopLeft[0], crosshairBottomLeft[0], 0f);
		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairTopLeft[0].x+1, crosshairTopLeft[0].y), new Point(crosshairBottomLeft[0].x+1, crosshairBottomLeft[0].y), 0f);
		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairTopLeft[0].x-1, crosshairTopLeft[0].y), new Point(crosshairBottomLeft[0].x-1, crosshairBottomLeft[0].y), 0f);
		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, crosshairTopRight[0], crosshairBottomRight[0], 0f);
		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairTopRight[0].x+1, crosshairTopRight[0].y), new Point(crosshairBottomRight[0].x+1, crosshairBottomRight[0].y), 0f);
		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairTopRight[0].x-1, crosshairTopRight[0].y), new Point(crosshairBottomRight[0].x-1, crosshairBottomRight[0].y), 0f);
		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, crosshairLeft[0], crosshairRight[0], 0f);
		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairLeft[0].x, crosshairLeft[0].y+1), new Point(crosshairRight[0].x, crosshairRight[0].y+1), 0f);
		NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairLeft[0].x, crosshairLeft[0].y-1), new Point(crosshairRight[0].x, crosshairRight[0].y-1), 0f);
		
		for(int i=1; i<4; i++){
			NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, crosshairTopLeft[i], crosshairBottomLeft[i], 255f);
			NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairTopLeft[i].x+1, crosshairTopLeft[i].y), new Point(crosshairBottomLeft[i].x+1, crosshairBottomLeft[i].y), 255f);
			NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairTopLeft[i].x-1, crosshairTopLeft[i].y), new Point(crosshairBottomLeft[i].x-1, crosshairBottomLeft[i].y), 255f);
			NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, crosshairTopRight[i], crosshairBottomRight[i], 255f);
			NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairTopRight[i].x+1, crosshairTopRight[i].y), new Point(crosshairBottomRight[i].x+1, crosshairBottomRight[i].y), 255f);
			NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairTopRight[i].x-1, crosshairTopRight[i].y), new Point(crosshairBottomRight[i].x-1, crosshairBottomRight[i].y), 255f);
			NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, crosshairLeft[i], crosshairRight[i], 255f);
			NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairLeft[i].x, crosshairLeft[i].y+1), new Point(crosshairRight[i].x, crosshairRight[i].y+1), 255f);
			NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(crosshairLeft[i].x, crosshairLeft[i].y-1), new Point(crosshairRight[i].x, crosshairRight[i].y-1), 255f);
		}
		
		//Send image to dashboard to assist drivers
		//CameraServer.getInstance().setImage(frame);
		
		//Send masked image to dashboard to assist in tweaking mask
		//CameraServer.getInstance().setImage(binaryFrame);

		//filter out small particles
		float areaMin = (float)AREA_MINIMUM;
		criteria[0].lower = areaMin;
		imaqError = NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null);

		//Send particle count after filtering to dashboard
		numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		//SmartDashboard.putNumber("Filtered particles", numParticles);
		
		if(numParticles > 0)
		{
			//Measure particles and sort by particle size
			Vector<ParticleReport> particles = new Vector<ParticleReport>();
			int topWidthIndex = 0;
			for(int particleIndex = 0; particleIndex < numParticles; particleIndex++)
			{
				ParticleReport par = new ParticleReport();
				par.PercentAreaToImageArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
				par.Area = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
				par.BoundingRectTop = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
				par.BoundingRectLeft = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
				par.BoundingRectBottom = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
				par.BoundingRectRight = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
				if(AreaScore(par) > 70 && (par.BoundingRectRight-par.BoundingRectLeft) > 100) {
					particles.add(par); //if(par.BoundingRectTop>480)	
					if((par.BoundingRectRight - par.BoundingRectLeft) > (particles.elementAt(topWidthIndex).BoundingRectRight - particles.elementAt(topWidthIndex).BoundingRectLeft)) {
						topWidthIndex = particles.size()-1;
					}
				}
			}
			//particles.sort(null);
			
			//TESTING
			SmartDashboard.putNumber("Number of sorted particles", particles.size());
			
			if(particles.size()>0){
				particles.set(0, particles.elementAt(topWidthIndex));
				//Scores the largest particle
				scores.Aspect = AspectScore(particles.elementAt(0));
				//SmartDashboard.putNumber("Aspect", scores.Aspect);
				scores.Area = AreaScore(particles.elementAt(0));
				//SmartDashboard.putNumber("Area", scores.Area);
				
				//Set values
				targetDetected = scores.Aspect > SCORE_MIN && scores.Area > SCORE_MIN;
				SmartDashboard.putNumber("Aspect", scores.Aspect);
				SmartDashboard.putNumber("Area", scores.Area);
				targetDistance = computeTargetDistance(binaryFrame, particles.elementAt(0));
				targetHorizontalAngle = computeHorizontalAngle(binaryFrame, particles.elementAt(0));
				targetVerticalAngle = computeVerticalAngle(binaryFrame, particles.elementAt(0));
				towerDistance = computeTowerDistance();
				for(int i=0; i<4; i++) {proximity[i] = computeProximity(particles.elementAt(0), i);}
				
				//TESTING
				//NIVision.imaqDrawShapeOnImage(testFrame, testFrame, new NIVision.Rect((int) particles.elementAt(0).BoundingRectTop, (int) particles.elementAt(0).BoundingRectLeft, (int) (particles.elementAt(0).BoundingRectBottom-particles.elementAt(0).BoundingRectTop), (int) (particles.elementAt(0).BoundingRectRight-particles.elementAt(0).BoundingRectLeft)), DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 500f);	
				//SmartDashboard.putNumber("Target pixel width", particles.elementAt(0).BoundingRectRight - particles.elementAt(0).BoundingRectLeft);
				//SmartDashboard.putNumber("Target aspect", (12)*(particles.elementAt(0).BoundingRectRight-particles.elementAt(0).BoundingRectLeft)/(particles.elementAt(0).BoundingRectBottom-particles.elementAt(0).BoundingRectTop));
			}
			
			//Send distance and target status to dashboard. The bounding rect, particularly the horizontal center (left - right) may be useful for rotating/driving towards a target
			SmartDashboard.putBoolean("Target Detected", targetDetected);
			SmartDashboard.putNumber("Distance to Target", targetDistance);
			SmartDashboard.putNumber("Horizontal Angle to Target", targetHorizontalAngle);
			SmartDashboard.putNumber("Vertical Angle to Target", targetVerticalAngle);
			SmartDashboard.putNumber("Distance to Tower", towerDistance);
			//SmartDashboard.putNumber("BottomRectLeft", particles.elementAt(0).BoundingRectBottom);
						
			//return whether both target is near the crosshair and the proper distance away (use hardcoded values and compare)
			SmartDashboard.putNumber("Distance to Crosshair 0", proximity[0]);
			SmartDashboard.putNumber("Distance to Crosshair 1", proximity[1]);
			SmartDashboard.putNumber("Distance to Crosshair 2", proximity[2]);
			SmartDashboard.putNumber("Distance to Crosshair 3", proximity[3]);
			
			if(targetDistance > (IDEAL_DISTANCE[(int) chooser.getSelected()] - 0.5) && targetDistance < (IDEAL_DISTANCE[(int) chooser.getSelected()] + 0.5)){
				SmartDashboard.putString("Drive instruction", "POSITION OPTIMAL");
			} else if(targetDistance > IDEAL_DISTANCE[(int) chooser.getSelected()]) {
				SmartDashboard.putString("Drive instruction", "MOVE FORWARD");
			} else if(targetDistance < IDEAL_DISTANCE[(int) chooser.getSelected()]) {
				SmartDashboard.putString("Drive instruction", "MOVE BACK");
			}
			if(targetHorizontalAngle > (IDEAL_HORIZONTAL_ANGLE[(int) chooser.getSelected()] - 7.5) && targetHorizontalAngle < (IDEAL_HORIZONTAL_ANGLE[(int) chooser.getSelected()] + 7.5)){
				SmartDashboard.putString("Aim instruction", "AIM OPTIMAL");
			} else if(targetHorizontalAngle > IDEAL_HORIZONTAL_ANGLE[(int) chooser.getSelected()]) {
				SmartDashboard.putString("Aim instruction", "SHIFT LEFT");
			} else if(targetHorizontalAngle < IDEAL_HORIZONTAL_ANGLE[(int) chooser.getSelected()]) {
				SmartDashboard.putString("Aim instruction", "SHIFT RIGHT");
			}
		} else {
			targetDetected = false;
			SmartDashboard.putBoolean("Target Detected", targetDetected);
		}
		
		//Send masked image to dashboard to assist in tweaking mask
		//CameraServer.getInstance().setImage(testFrame);
		
		//Send image to dashboard to assist drivers
		CameraServer.getInstance().setImage(frame);
	}

	//Comparator function for sorting particles. Returns true if particle 1 is larger
  	static boolean CompareParticleSizes(ParticleReport particle1, ParticleReport particle2)
  	{
  		//we want descending sort order
  		return particle1.PercentAreaToImageArea > particle2.PercentAreaToImageArea;
  	}

  	/**
	 * Converts a ratio with ideal value of 1 to a score. The resulting function is piecewise
	 * linear going from (0,0) to (1,100) to (2,0) and is 0 for all inputs outside the range 0-2
	 */
	double ratioToScore(double ratio)
	{
		return (Math.max(0, Math.min(100*(1-Math.abs(1-ratio)), 100)));
	}

	double AreaScore(ParticleReport report)
	{
		double boundingArea = (report.BoundingRectBottom - report.BoundingRectTop) * (report.BoundingRectRight - report.BoundingRectLeft);
		//Tape is 12" by 20" edge so 240" bounding rect. With 2" wide tape it covers 80" of the rect.
		return ratioToScore((240/80)*report.Area/boundingArea);
	}

	/**
	 * Method to score if the aspect ratio of the particle appears to match the retro-reflective target. Target is 7"x7" so aspect should be 1
	 */
	double AspectScore(ParticleReport report)
	{
		return ratioToScore((12/20)*(report.BoundingRectRight-report.BoundingRectLeft)/(report.BoundingRectBottom-report.BoundingRectTop));
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
		return targetWidth/(normalizedWidth*12*Math.tan(VIEW_ANGLE*Math.PI/(180*2))) * 0.3048;
	}
  	
  	private double computeHorizontalAngle (Image image, ParticleReport report) {
  		NIVision.GetImageSizeResult size;
		size = NIVision.imaqGetImageSize(image);
  		
		//(int) (((((2 * rec1.tl().x + rec1.width)) / original.width()) - 1) * (_fieldOfViewH.getValue()/2));
  		return (report.BoundingRectLeft + (report.BoundingRectRight - report.BoundingRectLeft)/2)/size.width * VIEW_ANGLE - 30;
  	}
  	
  	private double computeVerticalAngle (Image image, ParticleReport report) {
  		NIVision.GetImageSizeResult size;
		size = NIVision.imaqGetImageSize(image);
  		
		//until vertical view angle is determined, using 2/3 horizontal view angle
		//(int) (((((2 * rec1.tl().x + rec1.width)) / original.width()) - 1) * (_fieldOfViewH.getValue()/2));
  		return (report.BoundingRectBottom - (report.BoundingRectBottom - report.BoundingRectTop)/2)/size.height * (VIEW_ANGLE * 2/3) - 22.5;
  	}
  	
  	private double computeTowerDistance () {
  		return targetDistance * Math.cos(targetVerticalAngle);
  	}
  	
  	private double computeProximity(ParticleReport report, int crosshairNum) {
  		double particleCenterX = report.BoundingRectLeft + (report.BoundingRectRight - report.BoundingRectLeft)/2;
  		double particleCenterY = report.BoundingRectTop + (report.BoundingRectBottom - report.BoundingRectTop)/2;
  		SmartDashboard.putNumber("Particle Center X", particleCenterX);
  		SmartDashboard.putNumber("Particle Center Y", particleCenterY);
  		
  		return Math.sqrt((Math.pow(particleCenterX - ((double) (crosshairRight[crosshairNum].x-crosshairLeft[crosshairNum].x))/2, 2) + Math.pow(particleCenterY - crosshairLeft[crosshairNum].y, 2)));
  	}
}