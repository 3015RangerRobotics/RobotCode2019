package frc.robot.commands;

public class HatchGrabberRetract extends CommandBase {
	public HatchGrabberRetract() {
		requires(hatchMech);
	}

	@Override
	protected void initialize() {
		hatchMech.hatchGrabberRetract();
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end();
	}
}
