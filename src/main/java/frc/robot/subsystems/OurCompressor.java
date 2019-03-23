package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.OurCompressorAuto;

public class OurCompressor extends Subsystem {
	private Compressor compressor;
	private AnalogPotentiometer pressureSensor;

	public OurCompressor() {
		compressor = new Compressor();
		pressureSensor = new AnalogPotentiometer(RobotMap.pressureSensor, 250, -25);
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new OurCompressorAuto());
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("Pressure", Math.round(getPressure()));
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
}
