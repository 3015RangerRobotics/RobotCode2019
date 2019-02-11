package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
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

	public static final int ballMechVictor = 2;
	public static final int ballLimitSwitch = 11;

	public static final int hatchSolenoid = 0;
  
	public enum Side{
		kLeft,
		kRight
	}
}
