package frc.robot.commands;

public class HatchMechRetract extends CommandBase {
	public HatchMechRetract() {
		requires(hatchMech);
	}

	@Override
	protected void initialize() {
		hatchMech.hatchMechRetract();
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
