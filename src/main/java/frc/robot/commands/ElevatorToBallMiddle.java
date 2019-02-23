package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class ElevatorToBallMiddle extends CommandBase {
	public ElevatorToBallMiddle() {
		requires(elevator);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		elevator.set(ControlMode.MotionMagic, (elevator.ballMiddle * elevator.pulsesPerInch));
		System.out.println(elevator.getDistance());
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
