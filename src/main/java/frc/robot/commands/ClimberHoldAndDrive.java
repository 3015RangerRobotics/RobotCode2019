package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class ClimberHoldAndDrive extends CommandBase {
	double time; 
	public ClimberHoldAndDrive(double time) {
		requires(climber);
		requires(drive);
		this.time = time;
	}

	@Override
	protected void initialize() {
		this.setTimeout(time);
	}

	@Override
	protected void execute() {
		climber.setBackLeft(ControlMode.PercentOutput, 0.1);
		climber.setBackRight(ControlMode.PercentOutput, 0.1);
		climber.setCenter(ControlMode.PercentOutput, 0.1);
		drive.arcadeDrive(-0.4, 0, false);
		climber.setCenterWheels(1.0);
	}

	@Override
	protected boolean isFinished() {
		return this.isTimedOut();
	}

	@Override
	protected void end() {
		climber.setBackLeft(ControlMode.PercentOutput, 0);
		climber.setBackRight(ControlMode.PercentOutput, 0);
		climber.setCenter(ControlMode.PercentOutput, 0);
		drive.arcadeDrive(0, 0, false);
		climber.setCenterWheels(0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
