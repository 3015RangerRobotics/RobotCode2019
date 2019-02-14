package frc.robot;

public class RobotMap {
	public static final double kPeriod = 0.01;
	public static final double wheelBaseWidth = 2;

	public static final int leftDriveMaster = 10;
	public static final int leftDriveFollower1 = 11;
	public static final int leftDriveFollower2 = 12;
	public static final int rightDriveMaster = 13;
	public static final int rightDriveFollower1 = 14;
	public static final int rightDriveFollower2 = 15;

	public static final int elevatorTalonSRX = 16;
	public static final int elevatorBottomLimit = 4;

	public static final int intakeAidSPLeft = 0;
	public static final int intakeAidSPRight = 0;

	public static final int ballMechVictor = 2;
	public static final int ballLimitSwitch = 11;

	public static final int hatchGrabSolenoid1 = 0;
	public static final int hatchGrabSolenoid2 = 1;
	public static final int hatchEjectSolenoid1 = 2;
	public static final int hatchEjectSolenoid2 = 3;

	public enum Side {
		kLeft, kRight
	}
}
