package frc.robot.commands;

import frc.robot.StatTracker;

public class BallMechControlDown extends CommandBase {
	boolean ballAtStart;
	boolean ballTracked = false;

	public BallMechControlDown() {
		requires(ballMech);
	}

	@Override
	protected void initialize() {
		ballAtStart = ballMech.isBallPresent();
	}

	@Override
	protected void execute() {
		if (ballMech.isBallPresent() && !ballAtStart) {
			ballMech.intakeStop();
			if (!ballTracked) {
				StatTracker.addCargoHandled();
				ballTracked = true;
			}
		}
		else{
			ballMech.intakeDown();
		}
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
