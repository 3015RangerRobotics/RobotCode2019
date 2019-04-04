package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class SuccerExtend extends CommandBase {
	public SuccerExtend() {
		requires(succer);
	}

	@Override
	protected void initialize() {
		succer.succerPistonExtend();
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
