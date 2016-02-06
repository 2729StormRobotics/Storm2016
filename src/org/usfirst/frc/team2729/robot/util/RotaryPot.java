package org.usfirst.frc.team2729.robot.util;

import edu.wpi.first.wpilibj.AnalogPotentiometer;

import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    public void updateTable() {
        if(_table != null) {
            _table.putNumber("Value", get());
        }
    }
    public void startLiveWindowMode() {}
    public void stopLiveWindowMode() {}

    //All that table stuff
    public void initTable(ITable subtable) {
        _table = subtable;
        updateTable();
    }

    public ITable getTable() {
        return _table;
    }

    public String getSmartDashboardType() {
        return "Analog Input";
    }
}