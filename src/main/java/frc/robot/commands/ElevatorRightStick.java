package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class ElevatorRightStick extends CommandBase {
	public ElevatorRightStick() {
		requires(elevator);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		elevator.set(ControlMode.PercentOutput, oi.getDriverRightStickY());
		System.out.println(elevator.getVelocity());
	}

	@Override
	protected boolean isFinished() {
		return false;
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
