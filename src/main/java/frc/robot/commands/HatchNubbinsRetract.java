package frc.robot.commands;

public class HatchNubbinsRetract extends CommandBase {
	public HatchNubbinsRetract() {
		requires(hatchMech);
	}

	@Override
	protected void initialize() {
		hatchMech.hatchNubbinsRetract();
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
