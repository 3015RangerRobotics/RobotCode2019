package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class ClimberJackRetract extends CommandBase {

	public ClimberJackRetract() {
		requires(climber);
	}

	@Override
	protected void initialize() {
		climber.isPrepared = false;
	}

	@Override
	protected void execute() {
		if (climber.getCenterPosition() >= 0.25) {
			climber.setCenter(ControlMode.PercentOutput, -0.75);
		} else {
			climber.setCenter(ControlMode.PercentOutput, 0);
		}

		if (climber.getBackLeftPosition() >= 0.25) {
			climber.setBackLeft(ControlMode.PercentOutput, -0.75);
		} else {
			climber.setBackLeft(ControlMode.PercentOutput, 0);
		}

		if (climber.getBackRightPosition() >= 0.25) {
			climber.setBackRight(ControlMode.PercentOutput, -0.75);
		} else {
			climber.setBackRight(ControlMode.PercentOutput, 0);
		}
	}

	@Override
	protected boolean isFinished() {
		return climber.getCenterPosition() <= 0.25 && climber.getBackLeftPosition() <= 0.25
				&& climber.getBackRightPosition() <= 0.25;
	}

	@Override
	protected void end() {
		climber.setCenter(ControlMode.PercentOutput, 0);
		climber.setBackLeft(ControlMode.PercentOutput, 0);
		climber.setBackRight(ControlMode.PercentOutput, 0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
