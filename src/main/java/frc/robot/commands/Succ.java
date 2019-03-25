package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class Succ extends CommandBase {
	boolean isLocked = false;

	public Succ() {
		requires(succer);
		requires(climber);
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
		succer.succ();

		climber.setBackLeft(ControlMode.PercentOutput, 0.1);
		climber.setBackRight(ControlMode.PercentOutput, 0.1);
		climber.setCenter(ControlMode.PercentOutput, 0.1);
	}

	@Override
	protected boolean isFinished() {
		return succer.getPressure() <= succer.pressureLockValue;
	}

	@Override
	protected void end() {
		succer.noMoreSucc();
		climber.setBackLeft(ControlMode.PercentOutput, 0);
		climber.setBackRight(ControlMode.PercentOutput, 0);
		climber.setCenter(ControlMode.PercentOutput, 0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
