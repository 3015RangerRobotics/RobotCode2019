package frc.robot.commands;

public class DriveWithGamepad extends CommandBase {
	public DriveWithGamepad() {
		requires(drive);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double driveValue = oi.getDriverLeftStickY();
		drive.arcadeDrive(driveValue, oi.getDriverLeftStickX() / 1.25, true);
		// System.out.println(drive.getRightDistance());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		drive.setMotorOutputs(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
