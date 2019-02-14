package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;

public class OurCompressorAuto extends CommandBase {
	private final double CUTOFF_VOLTAGE = 8.5;

	public OurCompressorAuto() {
		requires(ourCompressor);
	}

	@Override
	protected void initialize() {
		ourCompressor.startCompressor();
	}

	@Override
	protected void execute() {
		if (RobotController.getBatteryVoltage() <= CUTOFF_VOLTAGE || (DriverStation.getInstance().getMatchTime() < 20
				&& DriverStation.getInstance().getMatchTime() > 0)) {
			ourCompressor.stopCompressor();
		} else {
			ourCompressor.startCompressor();
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		ourCompressor.stopCompressor();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
