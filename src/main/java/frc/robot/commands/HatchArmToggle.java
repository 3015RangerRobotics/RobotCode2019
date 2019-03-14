package frc.robot.commands;

public class HatchArmToggle extends CommandBase {
	public HatchArmToggle() {
		requires(hatchMech);
	}

	@Override
	protected void initialize() {
		if (hatchMech.isArmExtended()) {
			hatchMech.hatchArmRetract();
		} else {
			hatchMech.hatchArmExtend();
		}
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
