package frc.robot.commands;

public class HatchNubbinsExtend extends CommandBase {
	public HatchNubbinsExtend() {
		requires(hatchMech);
	}

	@Override
	protected void initialize() {
		hatchMech.hatchNubbinsExtend();
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
