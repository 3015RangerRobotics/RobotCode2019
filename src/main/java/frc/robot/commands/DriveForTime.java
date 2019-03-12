package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class DriveForTime extends CommandBase {
	private double speed;
	private double time;

	public DriveForTime(double speed, double time) {
		requires(drive);
		this.speed = speed;
		this.time = time;
	}

	@Override
	protected void initialize() {
		this.setTimeout(time);
		drive.resetEncoders();
		drive.setMotorOutputs(ControlMode.PercentOutput, speed, speed);
	}

	@Override
	protected void execute() {
		drive.setMotorOutputs(ControlMode.PercentOutput, speed, speed);
	}

	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	@Override
	protected void end() {
		drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
