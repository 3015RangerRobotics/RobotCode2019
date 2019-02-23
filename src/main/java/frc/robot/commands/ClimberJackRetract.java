package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class ClimberJackRetract extends CommandBase {

	public ClimberJackRetract() {
		requires(climber);
	}

	@Override
	protected void initialize() {
		// climber.setBackPosition(climber.backPosRetract);
		// climber.setCenterPosition(climber.centerPosRetract);
	}

	@Override
	protected void execute() {
		if (climber.getCenterPosition() >= .1) {
			climber.setCenter(ControlMode.PercentOutput, -.3);
		} else {
			climber.setCenter(ControlMode.PercentOutput, 0);
		}

		if (climber.getBackLeftPosition() >= .1) {
			climber.setBackLeft(ControlMode.PercentOutput, -.3);
		} else {
			climber.setBackLeft(ControlMode.PercentOutput, 0);
		}

		if (climber.getBackRightPosition() >= .1) {
			climber.setBackRight(ControlMode.PercentOutput, -.3);
		} else {
			climber.setBackRight(ControlMode.PercentOutput, 0);
		}
	}

	@Override
	protected boolean isFinished() {
		return climber.getCenterPosition() <= 0.1 && climber.getBackLeftPosition() <= 0.1
				&& climber.getBackRightPosition() <= 0.1;
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
