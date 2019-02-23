package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class ClimberCenterPrepareHigh extends CommandBase {
	public ClimberCenterPrepareHigh() {
		requires(climber);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		climber.setCenter(ControlMode.PercentOutput, 0.39);
		System.out.println(climber.getCenterPosition());
	}

	@Override
	protected boolean isFinished() {
		return climber.getCenterPosition() >= climber.centerPosHigh;
	}

	@Override
	protected void end() {
		climber.setCenter(ControlMode.PercentOutput, 0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
