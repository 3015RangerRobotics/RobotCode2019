package frc.robot.commands;

import frc.robot.StatTracker;

public class HatchMechExtend extends CommandBase {
	public HatchMechExtend() {
		requires(hatchMech);
	}

	@Override
	protected void initialize() {
		hatchMech.hatchMechExtend();
		StatTracker.addHatchExtension();
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
