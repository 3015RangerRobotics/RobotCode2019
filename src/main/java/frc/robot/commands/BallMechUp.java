package frc.robot.commands;

public class BallMechUp extends CommandBase {
	public BallMechUp() {
		requires(ballMech);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		ballMech.intakeUp();
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
