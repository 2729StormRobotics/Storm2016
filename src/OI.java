package storm.git;

import DriveTrain.java;

public class OI {

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
		
		if (value > 0){
			_right.set(1);
		}
		else if (value = 0){
			_right.set(0);
		}
		else if (value < 0){
			_right.set(-1);
		}
		
		
		if (value2 > 0){
			_left.set(1);
		}
		else if (value2 = 0){
			_left.set(0);
		}
		else if (value2 < 0){
			_left.set(-1);
		}

	}

}
