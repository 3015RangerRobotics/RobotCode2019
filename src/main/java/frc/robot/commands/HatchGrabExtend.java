package frc.robot.commands;

public class HatchGrabExtend extends CommandBase {
	public HatchGrabExtend() {
		requires(hatchMech);
	}

	@Override
	protected void initialize() {
		hatchMech.hatchGrabExtend();
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
