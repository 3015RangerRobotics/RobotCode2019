package frc.robot.commands;

public class HatchGrabRetract extends CommandBase {
	public HatchGrabRetract() {
		requires(hatchMech);
	}

	@Override
	protected void initialize() {
		hatchMech.hatchGrabRetract();
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
