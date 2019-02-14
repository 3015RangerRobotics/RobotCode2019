package frc.robot.commands;

import frc.robot.StatTracker;

public class BallMechUpTilBall extends CommandBase {

	public BallMechUpTilBall() {
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
		if (ballMech.isBallPresent()) {
			StatTracker.addCargoHandled();
		}
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
