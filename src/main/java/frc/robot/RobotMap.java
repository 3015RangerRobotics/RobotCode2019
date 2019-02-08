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

	public static final int rightDriveMaster = 0;
	public static final int rightDriveFollower1 = 0;
	public static final int rightDriveFollower2 = 0;
	public static final int leftDriveMaster = 0;
	public static final int leftDriveFollower1 = 0;
	public static final int leftDriveFollower2 = 0;

	// public static final int rightDriveMotors = 0;
	// public static final int leftDriveMotors = 1;

	// public static final int rightDriveEncoder1 = 0;
	// public static final int rightDriveEncoder2 = 1;
	// public static final int leftDriveEncoder1 = 2;
	// public static final int leftDriveEncoder2 = 3;

	public static final int elevatorTalonSRX = 3;
	public static final int elevatorBottomLimit = 4;
  
	public enum Side{
		kLeft,
		kRight
	}
}
