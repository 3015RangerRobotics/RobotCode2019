package frc.robot.commands;

public class TestClimber extends CommandBase {
	public TestClimber() {
		requires(climber);
	}

	@Override
	protected void initialize() {
		climber.selfTest();
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
