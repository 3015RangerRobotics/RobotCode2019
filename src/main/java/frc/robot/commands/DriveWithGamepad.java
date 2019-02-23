package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class DriveWithGamepad extends CommandBase {

	public DriveWithGamepad() {
		requires(drive);
	}

	@Override
	protected void initialize() {
		drive.setRampRate(0);
	}

	@Override
	protected void execute() {
		double driveValue = oi.getDriverLeftStickY();

		if (elevator.getDistance() > 40) {
			driveValue *= 0.5;
			drive.setRampRate(0);// .7);
		} else if (elevator.getDistance() > 20) {
			driveValue *= 0.75;
			drive.setRampRate(0);
		}

		if (Math.abs(oi.getDriverRightStickX()) >= 0.1) {
			drive.curvatureDrive(driveValue, oi.getDriverRightStickX() / 1.25, false, true);
		} else {
			drive.arcadeDrive(driveValue, oi.getDriverLeftStickX() / 1.25, true);
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
		drive.setRampRate(0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
