package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.Robot;

public class DriveWithGamepad extends CommandBase {

	public DriveWithGamepad() {
		requires(drive);
	}

	@Override
	protected void initialize() {
		drive.setRampRate(0);
	}

	@Override
	protected void execute() {
		double driveValue = oi.getDriverLeftStickY();
		double turnValue = oi.getDriverLeftStickX() / 1.25;

		if(oi.isDriverRightStickPressed()){
			driveValue *= drive.fineAdjustDrive;
			turnValue *= drive.fineAdjustTurn;
		}

		// if (elevator.getDistance() > 40) {
		// 	driveValue *= drive.speedModTopHeight;
		// 	turnValue *= drive.turnModTopHeight;
		// } else if (elevator.getDistance() > 20) {
		// 	driveValue *= drive.speedModMidHeight;
		// }

		if (Math.abs(oi.getDriverRightStickX()) >= 0.1 && !oi.isDriverRightStickPressed()) {
			drive.curvatureDrive(driveValue, oi.getDriverRightStickX() / 1.25, false, true);
		} else {
			drive.arcadeDrive(driveValue, turnValue, true);
		
		}

		// System.out.println(Robot.getRotationalVelocity());
		// System.out.println("Pitch: " + Robot.getPitch() + ", Roll: " + Robot.getRoll());

	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
		drive.setRampRate(0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
