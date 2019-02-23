package frc.robot.commands;

public class HatchEjectExtend extends CommandBase {
	public HatchEjectExtend() {
		requires(hatchMech);
	}

	@Override
	protected void initialize() {
		hatchMech.hatchEjectExtend();
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
