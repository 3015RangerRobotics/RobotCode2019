package frc.robot.commands;

public class TestHatchMech extends CommandBase {
	public TestHatchMech() {
		requires(hatchMech);
	}

	@Override
	protected void initialize() {
		hatchMech.selfTest();
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
		end();
	}
}
