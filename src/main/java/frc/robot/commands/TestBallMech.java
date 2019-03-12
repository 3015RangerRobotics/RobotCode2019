package frc.robot.commands;

public class TestBallMech extends CommandBase {
	public TestBallMech() {
		requires(ballMech);
	}

	@Override
	protected void initialize() {
		ballMech.selfTest();
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
