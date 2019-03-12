package frc.robot.commands;

public class TestDrive extends CommandBase {
	public TestDrive() {
		requires(drive);
	}

	@Override
	protected void initialize() {
		drive.selfTest();
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
