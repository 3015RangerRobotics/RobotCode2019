package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTurnToTarget extends CommandBase {

	@Override
	protected void initialize() {
		double targetXAngle = SmartDashboard.getNumber("TargetXAngle", -1);
		double targetYAngle = SmartDashboard.getNumber("TargetYAngle", -1);
		if (targetXAngle == -1 && targetYAngle == -1) {
			System.out.println("No target");
		} else {
			new DriveTurnToAngleWithEncoders(targetXAngle, 14, 10).start();
		}
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end();
	}
}
