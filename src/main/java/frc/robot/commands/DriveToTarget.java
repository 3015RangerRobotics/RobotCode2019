package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.motionProfiles.MotionProfiles;

public class DriveToTarget extends CommandBase {
	public DriveToTarget() {

	}

	@Override
	protected void initialize() {
		double distanceToTarget = (SmartDashboard.getNumber("TargetDistance", 0) / 12) - 2;
		// double angleToTarget = SmartDashboard.getNumber("TargetXAngle", 0);
		// double dx = Math.sin(Math.abs(angleToTarget)) * distanceToTarget;
		// double dy = Math.sqrt((distanceToTarget*distanceToTarget) - (dx*dx));
		// if(angleToTarget > 0) {
		// dx *= -1;
		// }
		// new DriveMotionProfile(MotionProfiles.generate2D(dy, dx, 0, 8, 5, 100,
		// false)).start();

		new DriveMotionProfile(MotionProfiles.generate1DPF(distanceToTarget, 8, 5, 100, false)).start();
		// new DriveMotionProfile(MotionProfiles.generate2DToTarget(zDist, xDist, angle1, angle2, maxVel, maxAcc));
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
