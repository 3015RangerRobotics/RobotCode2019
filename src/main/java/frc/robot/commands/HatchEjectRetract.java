package frc.robot.commands;

public class HatchEjectRetract extends CommandBase {
	public HatchEjectRetract() {
		requires(hatchMech);
	}

	@Override
	protected void initialize() {
		hatchMech.hatchEjectRetract();
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
