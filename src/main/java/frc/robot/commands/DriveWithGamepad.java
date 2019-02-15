package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class DriveWithGamepad extends CommandBase {
	public DriveWithGamepad() {
		requires(drive);
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
		double driveValue = oi.getDriverLeftStickY();
		drive.arcadeDrive(driveValue, oi.getDriverLeftStickX() / 1.25, true);
		// System.out.println(oi.getDriverLeftStickX());
		// System.out.println(drive.getRightVelocity());
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
