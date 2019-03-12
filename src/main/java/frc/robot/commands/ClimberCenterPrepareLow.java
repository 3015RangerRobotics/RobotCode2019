package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class ClimberCenterPrepareLow extends CommandBase {
	public ClimberCenterPrepareLow() {
		requires(climber);
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
		climber.setCenter(ControlMode.PercentOutput, 0.5);
		// System.out.println("hi my name is Keyshawn and I love lasagna");
		System.out.println(climber.getCenterPosition());
	}

	@Override
	protected boolean isFinished() {
		return climber.getCenterPosition() >= climber.centerPosLow;
	}

	@Override
	protected void end() {
		climber.setCenter(ControlMode.PercentOutput, 0.0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
