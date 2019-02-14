package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class ElevatorToBottom extends CommandBase {
	public ElevatorToBottom() {
		requires(elevator);
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
		elevator.set(ControlMode.Position, elevator.elevatorHeightBottom * elevator.pulsesPerInch);
	}

	@Override
	protected boolean isFinished() {
		return elevator.getDistance() < 1;
	}

	@Override
	protected void end() {
		elevator.set(ControlMode.PercentOutput, 0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
