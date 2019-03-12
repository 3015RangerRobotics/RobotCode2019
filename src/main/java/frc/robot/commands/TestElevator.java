package frc.robot.commands;

public class TestElevator extends CommandBase {
	public TestElevator() {
		requires(elevator);
	}

	@Override
	protected void initialize() {
		elevator.selfTest();
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
