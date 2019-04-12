package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import frc.motionProfiles.MotionProfiles;
import frc.robot.Robot;

public class DriveToTarget extends CommandBase {
	public DriveToTarget() {

	}

	@Override
	protected void initialize() {
		double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
		double[] camtran = NetworkTableInstance.getDefault().getTable("limelight").getEntry("camtran").getDoubleArray(new double[] {0, 0, 0, 0, 0, 0});
		double yaw = camtran[4];
		double x = camtran[0];
		double z = camtran[2];

		double offset = 12;
		double theta = Math.toRadians(yaw + tx);

		double correctedX = x * Math.cos(theta) + z * Math.sin(theta);
		double correctedZ = -(-x * Math.sin(theta) + z * Math.cos(theta)) - offset;
		double correctedTX = Math.toDegrees(Math.atan2(correctedZ, x)) - 90;
		double distance = Math.sqrt((correctedX * correctedX) + (correctedZ * correctedZ));

		new DriveMotionProfile(MotionProfiles.generate2DToTarget(correctedZ / 12, -correctedX / 12, tx, yaw, 5, 3)).start();
		// Values for corrected x and z are still in inches when put on SmartDashboard in Robot.java
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
