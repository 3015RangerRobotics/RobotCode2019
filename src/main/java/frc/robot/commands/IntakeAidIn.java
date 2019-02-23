package frc.robot.commands;

public class IntakeAidIn extends CommandBase {
	public IntakeAidIn() {
		requires(intakeAid);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		intakeAid.intakeAidIntake();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		intakeAid.intakeAidStop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
