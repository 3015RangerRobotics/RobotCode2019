package frc.robot.commands;

public class BallMechDown extends CommandBase {
	public BallMechDown() {
		requires(ballMech);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		ballMech.intakeDown();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		ballMech.intakeStop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
