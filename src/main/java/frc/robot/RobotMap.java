package frc.robot;

public class RobotMap {
	public static final double kPeriod = 0.01;
	public static final double wheelBaseWidth = 2;
	public static final double tapeCameraOffset = -8.75;

	public static final int leftDriveMaster = 10; // Left Drive Master
	public static final int leftDriveFollower1 = 11; // Front Left Drive
	public static final int leftDriveFollower2 = 12; // Back Left Drive
	public static final int rightDriveMaster = 13; // Right Drive Master
	public static final int rightDriveFollower1 = 14; // Front Right Drive
	public static final int rightDriveFollower2 = 15; // Back Right Drive
	public static final int leftDriveEncoder1 = 0; 
	public static final int leftDriveEncoder2 = 1;
	public static final int rightDriveEncoder1 = 2;
	public static final int rightDriveEncoder2 = 3;

	public static final int elevatorTalonSRX = 16; // Elevator
	public static final int elevatorBottomLimit = 5;

	public static final int intakeAidSPLeft = 0;
	public static final int intakeAidSPRight = 0;

	public static final int ballMechVictor = 3; 
	public static final int ballLimitSwitch = 4;

	public static final int hatchGrabSolenoid1 = 2;//3;
	public static final int hatchGrabSolenoid2 = 3;//2;
	public static final int hatchEjectSolenoid1 = 0;//1;
	public static final int hatchEjectSolenoid2 = 1;//0;

	public static final int climberCenterJackTalonSRX = 19; // Rear Jack 
	public static final int climberLeftJackTalonSRX = 17; // Left Jack 
	public static final int climberRightJackTalonSRX = 18; // Right Jack
	public static final int climberCenterWheelsVictorSP = 4;
	public static final int climberDistanceSensor = 1;
  
	public enum Side{
		kLeft,
		kRight
	}
}
