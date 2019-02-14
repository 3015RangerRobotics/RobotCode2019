package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.OurCompressorAuto;

public class OurCompressor extends Subsystem {
	private Compressor compressor;

	public OurCompressor() {
		compressor = new Compressor();
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new OurCompressorAuto());
	}

	public void startCompressor() {
		compressor.start();
	}

	public void stopCompressor() {
		compressor.stop();
	}
}
