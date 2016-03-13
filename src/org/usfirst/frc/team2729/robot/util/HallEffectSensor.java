package org.usfirst.frc.team2729.robot.util;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;

public class HallEffectSensor implements LiveWindowSendable{

	private DigitalInput _hallEffect;
	private Counter _counter;

	public HallEffectSensor(int port){
		_hallEffect = new DigitalInput(port);
		_counter 	= new Counter(_hallEffect);
	}

	public int count(){
		return _counter.get();
	}

	public void reset(){
		_counter.reset();
	}

	ITable _table;

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
		return "Analog input";
	}

	@Override
	public void updateTable() {
		if(_table != null){
			_table.putNumber("Value", _counter.get());
		}
	}

	@Override
	public void startLiveWindowMode() {}

	@Override
	public void stopLiveWindowMode() {}

}
