package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DriverStation;

public class ElevatorToTop extends CommandBase {
	public ElevatorToTop() {
		requires(elevator);
		this.setRunWhenDisabled(true);
	}

	@Override
	protected void initialize() {
		if(DriverStation.getInstance().isDisabled()) {
			this.cancel();
		}
	}

	@Override
	protected void execute() {
		elevator.set(ControlMode.MotionMagic, elevator.elevatorHeightTop * elevator.pulsesPerInch);
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
