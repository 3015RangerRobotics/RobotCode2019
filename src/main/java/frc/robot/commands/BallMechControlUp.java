package frc.robot.commands;

import frc.robot.StatTracker;

public class BallMechControlUp extends CommandBase {
	boolean ballAtStart;
	boolean ballTracked = false;

	public BallMechControlUp() {
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
			ballMech.intakeUp();
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
