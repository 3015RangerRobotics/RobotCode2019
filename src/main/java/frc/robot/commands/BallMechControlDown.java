package frc.robot.commands;

public class BallMechControlDown extends CommandBase {
	boolean ballAtStart;

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
			oi.driverRumble(1.0);
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
		oi.driverRumble(0.0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
