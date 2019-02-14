package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTurnToTarget extends CommandBase {

	@Override
	protected void initialize() {
		new DriveTurnToAngle((SmartDashboard.getNumber("TargetXAngle", 0)), false).start();
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
	}
}
