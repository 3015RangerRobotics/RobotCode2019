package frc.robot.commands;

public class DriveToTarget extends CommandBase {
	public DriveToTarget() {

	}

	@Override
	protected void initialize() {
		// double distanceToTarget = (SmartDashboard.getNumber("TargetDistance", 0) / 12) - 2;
		// double angleToTarget = SmartDashboard.getNumber("TargetXAngle", 0);
		// double dx = Math.sin(Math.abs(angleToTarget)) * distanceToTarget;
		// double dy = Math.sqrt((distanceToTarget*distanceToTarget) - (dx*dx));
		// if(angleToTarget > 0) {
		// dx *= -1;
		// }
		// System.out.println("End angle: " + (Robot.getVisionAngle1() + Robot.getVisionAngle2()));
		// System.out.println(Robot.getVisionXDistance() + ", " + Robot.getVisionZDistance());
		// if (Robot.getVisionDistance() > 0) new DriveMotionProfile(MotionProfiles.generate2DPF(-Robot.getVisionXDistance() / 12, Robot.getVisionZDistance() / 12, Robot.getVisionAngle2(), 5, 3, 100, false)).start();
			// if(Robot.getVisionDistance() > 0) new DriveToTargetJank(Robot.getVisionAngle1(), Robot.getVisionAngle2(), Robot.getVisionDistance() / 12).start();
		// new DriveMotionProfile(MotionProfiles.generate1DPF(distanceToTarget, 8, 5, 100, false)).start();
		// if(Robot.getVisionDistance() > 0) new DriveMotionProfile(MotionProfiles.generate2DToTarget((Robot.getVisionZDistance() / 12), (Robot.getVisionXDistance() / 12), Robot.getVisionAngle1(), Robot.getVisionAngle2(), 8, 4)).start();;
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
