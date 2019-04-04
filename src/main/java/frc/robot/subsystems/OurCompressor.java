package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.OurCompressorAuto;

public class OurCompressor extends Subsystem {
	private Compressor compressor;
	private AnalogPotentiometer pressureSensor;
	private ArrayList<Double> pressureValues;

	public OurCompressor() {
		compressor = new Compressor();
		pressureSensor = new AnalogPotentiometer(RobotMap.pressureSensor, 250, -25);
		pressureValues = new ArrayList<>();
		pressureValues.add(getPressure());
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new OurCompressorAuto());
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("Pressure", getAveragePressure());
		
		if(pressureValues.size() == 20){
			pressureValues.remove(0);
			pressureValues.add(getPressure());
		} else {
			pressureValues.add(getPressure());
		}
	}

	public void startCompressor() {
		compressor.start();
	}

	public void stopCompressor() {
		compressor.stop();
	}

	public double getPressure() {
		return pressureSensor.get();
	}

	public double getAveragePressure() {
		double sum = 0;
		for(int i = 0; i < pressureValues.size(); i++){
			sum += pressureValues.get(i);
		}
		return Math.round(sum / pressureValues.size());
	}
}
