package org.usfirst.frc.team2729.robot.util;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;

public class StringPot implements LiveWindowSendable {

	public double VAL_MAX_SAFE = 0;
	public final double EXTENDED_LENGTH = 0.65325625; //Length in meters
	public final double DISTANCE_EYELET = 0.092075; //TODO: Find this length from pot to eyelet
	//Pot is good, especially when it's analog
	private AnalogPotentiometer _pot;

	public StringPot(int Num, double maxSafeVal) {
		_pot = new AnalogPotentiometer(Num);
		VAL_MAX_SAFE = maxSafeVal;
	}

	public double get() {
		double val = _pot.get();
		return val > 0.06 ? val : 0;
	}

	//Makes a Readable and Writeable table
	private ITable _table = null;

	@Override
	public void updateTable() {
		if(_table != null) {
			_table.putNumber("Value", get());
		}
	}
	@Override
	public void startLiveWindowMode() {}
	@Override
	public void stopLiveWindowMode() {}

	//All that table stuff
	@Override
	public void initTable(ITable subtable) {
		_table = subtable;
		updateTable();
	}

	@Override
	public ITable getTable() {
		return _table;
	}

	@Override
	public String getSmartDashboardType() {
		return "Analog Input";
	}
	public double getLength(){
		return (_pot.get() * EXTENDED_LENGTH) - DISTANCE_EYELET + .03;
	}
}