package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class ClimberPrepareHighStep2 extends CommandBase {
	public ClimberPrepareHighStep2() {
		requires(climber);
	}

	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		climber.setCenter(ControlMode.PercentOutput, -0.5);
	}

	@Override
	protected boolean isFinished() {
		return climber.getCenterPosition() <= climber.centerPosHigh;
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
