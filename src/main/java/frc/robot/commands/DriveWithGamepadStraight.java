package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.Robot;

public class DriveWithGamepadStraight extends CommandBase {

	public DriveWithGamepadStraight() {
		requires(drive);
	}

	@Override
	protected void initialize() {
		drive.setRampRate(0);
		drive.setBrakeMode();
		Robot.resetIMU();
	}

	@Override
	protected void execute() {
		double driveValue = oi.getDriverLeftStickY();

		if (oi.isDriverXPressed()) {
			driveValue *= drive.fineAdjustDrive;
		}

		double turnValue = 0;

		if(Robot.getYaw() >= 0.5){
			turnValue = -0.25;
		}else if(Robot.getYaw() <= -0.5){
			turnValue = 0.25;
		}

		drive.arcadeDrive(driveValue, turnValue, true);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
		drive.setCoastMode();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
