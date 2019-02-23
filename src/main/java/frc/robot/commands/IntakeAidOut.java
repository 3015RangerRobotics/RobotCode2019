package frc.robot.commands;

public class IntakeAidOut extends CommandBase {
	public IntakeAidOut() {
		requires(intakeAid);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		intakeAid.intakeAidOuttake();
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
