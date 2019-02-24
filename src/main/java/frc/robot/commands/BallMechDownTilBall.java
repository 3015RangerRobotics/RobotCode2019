package frc.robot.commands;

import frc.robot.StatTracker;

public class BallMechDownTilBall extends CommandBase {

	public BallMechDownTilBall() {
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
		return ballMech.isBallPresent();
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
