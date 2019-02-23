package frc.robot.commands;

public class TestCommand extends CommandBase {
	public TestCommand() {
		requires(intakeAid);
		requires(ballMech);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		intakeAid.intakeAidIntake();
		ballMech.intakeDown();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		intakeAid.intakeAidStop();
		ballMech.intakeStop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
