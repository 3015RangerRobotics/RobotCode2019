package frc.robot.commands;

import frc.robot.StatTracker;

public class BallMechUpTilBall extends CommandBase {
	// boolean ballAtBegin;

	public BallMechUpTilBall() {
		requires(ballMech);
	}

	@Override
	protected void initialize() {
		setTimeout(3);
		// ballAtBegin = ballMech.isBallPresent();
	}

	@Override
	protected void execute() {
		ballMech.intakeUp();
	}

	@Override
	protected boolean isFinished() {
		if (ballMech.isBallPresent() && isTimedOut()) {
			StatTracker.addCargoHandled();
		}
		return ballMech.isBallPresent() && isTimedOut();
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
