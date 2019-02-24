package frc.robot.commands;

public class HatchArmExtend extends CommandBase {
	public HatchArmExtend() {
		requires(hatchMech);
	}

	@Override
	protected void initialize() {
		hatchMech.hatchArmExtend();
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
