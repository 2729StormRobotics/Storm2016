package storm.git;

public class RobotMap {

	public static void main(String[] args) {
		
		rightJoy = new Joystick(1);
		leftJoy = new Joystick(2);
		
		double value;
		//value = rightJoy.getX();
		value = rightJoy.getY();
		value = rightJoy.getThrottle();
		//value = rightJoy.getTwist();
		
		double value2;
		//value2 = leftJoy.getX();
		value2 = leftJoy.getY();
		value2 = leftJoy.getThrottle();
		//value = rightJoy.getTwist();
		
		
	}

}
