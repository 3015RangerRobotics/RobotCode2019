package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class DriveWithGamepad extends CommandBase {
	private double maxControllerChange = 0.05;
	private double lastDriveValue = 0;
	public DriveWithGamepad() {
		requires(drive);
	}

	@Override
	protected void initialize() {
		lastDriveValue = 0;
	}

	@Override
	protected void execute() {
		double driveValue = oi.getDriverLeftStickY();
		drive.arcadeDrive(driveValue, oi.getDriverLeftStickX() / 1.25, true);
		// climber.setCenterWheels(-driveValue);
		// System.out.println(oi.getDriverLeftStickX());
		// System.out.println(drive.getRightDistance() + ", " + drive.getLeftDistance());

		if (elevator.getDistance() > 25) {
			if (driveValue > lastDriveValue + maxControllerChange) {
				driveValue = lastDriveValue + maxControllerChange;
			} else if (driveValue < lastDriveValue - maxControllerChange) {
				driveValue = lastDriveValue - maxControllerChange;
			}
			if (Math.abs(oi.getDriverLeftStickY()) <= 0.1) {
				driveValue = oi.getDriverLeftStickY();
			}
		}

		lastDriveValue = driveValue;
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
