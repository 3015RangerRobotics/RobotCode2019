package frc.robot.commands;

public class BallMechControlUp extends CommandBase {
	boolean ballAtStart;

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
			oi.driverRumble(1.0);
		} else {
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
		oi.driverRumble(0.0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
