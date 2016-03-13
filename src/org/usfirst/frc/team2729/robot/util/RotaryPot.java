package org.usfirst.frc.team2729.robot.util;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;

public class RotaryPot implements LiveWindowSendable {

	public double VAL_MAX_SAFE = 0;
	//Pot is good, especially when it's analog
	private AnalogPotentiometer _pot;

	public RotaryPot(int Num, double maxSafeVal) {
		_pot = new AnalogPotentiometer(Num);
		VAL_MAX_SAFE = maxSafeVal;
	}

	public double get() {
		double val = _pot.get();
		return val > 0.00 ? val : 0; //find min value
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
}