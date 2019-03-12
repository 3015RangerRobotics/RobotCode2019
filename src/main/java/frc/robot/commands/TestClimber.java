package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class TestClimber extends CommandBase {
	public TestClimber() {
		requires(climber);
	}

	@Override
	protected void initialize() {
		climber.selfTest();
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
