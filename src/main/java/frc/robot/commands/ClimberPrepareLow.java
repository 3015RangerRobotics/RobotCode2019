package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class ClimberPrepareLow extends CommandBase {
	public ClimberPrepareLow() {
		requires(climber);
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
		climber.setCenter(ControlMode.PercentOutput, 0.5);
	}

	@Override
	protected boolean isFinished() {
		return climber.getCenterPosition() >= climber.centerPosLow;
	}

	@Override
	protected void end() {
		climber.setCenter(ControlMode.PercentOutput, 0.0);
		climber.isPrepared = true;
	}

	@Override
	protected void interrupted() {
		end();
	}
}
