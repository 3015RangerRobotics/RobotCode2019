package frc.robot.commands;

import frc.robot.OI;

public class CancelCommand extends CommandBase {
	public CancelCommand() {
		requires(ballMech);
		requires(climber);
		requires(drive);
		requires(elevator);
		requires(hatchMech);
		requires(intakeAid);
		requires(ourCompressor);
	}

	@Override
	protected void initialize() {
		
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
