package frc.robot.commands;

public class ClimberTempFrontWheel extends CommandBase {
	public ClimberTempFrontWheel() {
		requires(climber);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		climber.test();
		// System.out.println(climber.getBackLeftPosition());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end();
	}
}
