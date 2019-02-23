package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class ElevatorToHatch extends CommandBase {
	public ElevatorToHatch() {
		requires(elevator);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		elevator.set(ControlMode.MotionMagic, elevator.elevatorHeightHatch * elevator.pulsesPerInch);
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
