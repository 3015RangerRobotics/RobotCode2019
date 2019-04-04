package frc.robot;

import java.util.Map;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.DriverStation.MatchType;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.AutoCargoSide;
import frc.robot.commands.AutoCenterCargo;
import frc.robot.commands.AutoRocketFar;
import frc.robot.commands.AutoRocketNear;
import frc.robot.commands.CommandBase;
import frc.robot.commands.TestBallMech;
import frc.robot.commands.TestClimber;
import frc.robot.commands.TestDrive;
import frc.robot.commands.TestElevator;
import frc.robot.commands.TestHatchMech;
import frc.robot.commands.TestRobot;

public class Robot extends TimedRobot {
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	public static AHRS imu;

	public static NetworkTableEntry hatchArm;
	public static NetworkTableEntry hatchNubs;

	public static NetworkTableEntry driveLeft;
	public static NetworkTableEntry driveRight;
	public static NetworkTableEntry driveGyro;

	public static NetworkTableEntry ballIn;
	public static NetworkTableEntry ballOut;

	public static NetworkTableEntry elevatorLimit;
	public static NetworkTableEntry elevatorEncoder;
	public static NetworkTableEntry elevatorPosition;

	public static NetworkTableEntry climberLeft;
	public static NetworkTableEntry climberRight;
	public static NetworkTableEntry climberBack;
	public static NetworkTableEntry climberWheel;
	public static NetworkTableEntry climberOffsets;

	private boolean shouldStopRecording = false;

	@Override
	public void robotInit() {
		imu = new AHRS(Port.kMXP);

		CommandBase.init();

		chooser.addOption("Back Rocket", new AutoRocketFar(false));
		chooser.addOption("Near Rocket", new AutoRocketNear(false));
		chooser.addOption("Cargo Side", new AutoCargoSide(false));
		chooser.addOption("Cargo Front", new AutoCenterCargo(false));
		chooser.addOption("None", null);

		SmartDashboard.putData("Auto Mode", chooser);
		SmartDashboard.putData("Gyro", imu);

		ShuffleboardLayout testCommands = Shuffleboard.getTab("Systems Check")
				.getLayout("Test Commands", BuiltInLayouts.kList).withSize(2, 2).withPosition(9, 0)
				.withProperties(Map.of("Label position", "HIDDEN"));
		testCommands.add(new TestHatchMech());
		testCommands.add(new TestDrive());
		testCommands.add(new TestBallMech());
		testCommands.add(new TestElevator());
		testCommands.add(new TestClimber());

		ShuffleboardTab testTab = Shuffleboard.getTab("Systems Check");
		testTab.add(new TestRobot()).withSize(2, 1).withPosition(9, 2);

		ShuffleboardLayout hatchValues = Shuffleboard.getTab("Systems Check")
				.getLayout("Hatch Mech", BuiltInLayouts.kList).withSize(2, 2).withPosition(0, 0);
		hatchArm = hatchValues.add("Arm", false).getEntry();
		hatchNubs = hatchValues.add("Nubs", false).getEntry();

		ShuffleboardLayout driveValues = Shuffleboard.getTab("Systems Check").getLayout("Drive", BuiltInLayouts.kList)
				.withSize(2, 3).withPosition(2, 0);
		driveLeft = driveValues.add("Left", false).getEntry();
		driveRight = driveValues.add("Right", false).getEntry();
		driveGyro = driveValues.add("IMU", false).getEntry();

		ShuffleboardLayout ballValues = Shuffleboard.getTab("Systems Check")
				.getLayout("Ball Mech", BuiltInLayouts.kList).withSize(2, 2).withPosition(0, 2);
		ballIn = ballValues.add("In", false).getEntry();
		ballOut = ballValues.add("Out", false).getEntry();

		ShuffleboardLayout elevatorValues = Shuffleboard.getTab("Systems Check")
				.getLayout("Elevator", BuiltInLayouts.kList).withSize(2, 3).withPosition(4, 0);
		elevatorLimit = elevatorValues.add("Bottom Limit", false).getEntry();
		elevatorEncoder = elevatorValues.add("Encoder", false).getEntry();
		elevatorPosition = elevatorValues.add("Position", false).getEntry();

		ShuffleboardLayout climberValues = Shuffleboard.getTab("Systems Check")
				.getLayout("Climber", BuiltInLayouts.kList).withSize(2, 5).withPosition(6, 0);
		climberLeft = climberValues.add("Left Jack", false).getEntry();
		climberRight = climberValues.add("Right Jack", false).getEntry();
		climberBack = climberValues.add("Back Jack", false).getEntry();
		climberWheel = climberValues.add("Wheel", false).getEntry();
		climberOffsets = climberValues.add("Offsets", false).getEntry();

		resetIMU();

		SmartDashboard.putBoolean("RecordVid", false);
	}

	@Override
	public void robotPeriodic() {
		SmartDashboard.putNumber("time", DriverStation.getInstance().getMatchTime());
		String alliance = "white";
		if (DriverStation.getInstance().getAlliance() == Alliance.Red) {
			alliance = "red";
		} else if (DriverStation.getInstance().getAlliance() == Alliance.Blue) {
			alliance = "blue";
		}
		SmartDashboard.putString("alliance", alliance);
	}

	@Override
	public void disabledInit() {
		// Don't stop recording when autonomous is disabled, only stop after teleop
		if(shouldStopRecording){
			stopRecording();
		}
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		startRecording();
		shouldStopRecording = false;

		autonomousCommand = chooser.getSelected();

		resetIMU();

		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		if(DriverStation.getInstance().getMatchTime() < 5){
			shouldStopRecording = true;
		}
	}

	@Override
	public void testPeriodic() {

	}

	public static void startRecording(){
		DriverStation ds = DriverStation.getInstance();
		MatchType matchType = ds.getMatchType();
		if(matchType == MatchType.None) return;
		String type = matchType.name();
		String event = ds.getEventName();
		int matchNum = ds.getMatchNumber();
		int replayNum = ds.getReplayNumber();

		String fileName = "pov-" + event + "-" + type + "-" + matchNum + "-" + replayNum;
		SmartDashboard.putString("RecordName", fileName);
		SmartDashboard.putBoolean("RecordVid", true);
	}

	public static void stopRecording(){
		new Thread(() -> {
			// Delay the end of the pov video
			Timer.delay(10);
			SmartDashboard.putBoolean("RecordVid", false);
		}).start();
		// SmartDashboard.putBoolean("RecordVid", false);
	}

	public static float getRoll() {
		return imu.getRoll() + 36.5f; //+ 35; // RoboRIO and NavX mounted at -35 degrees
	}

	public static float getPitch() {
		return imu.getPitch();
	}

	public static float getYaw() {
		return imu.getYaw();
	}

	public static double getRotationalVelocity() {
		return Math.toDegrees(imu.getRate());
	}

	public static void resetIMU() {
		imu.reset();
	}

	public static boolean isIMUConnected() {
		return imu.isConnected();
	}

	public static double getTargetXAngle() {
		return SmartDashboard.getNumber("TargetXAngle", -1);
	}

	public static double getTargetDistance() {
		return SmartDashboard.getNumber("TargetDistance", -1);
	}

	public static void setVisionModeTape() {
		SmartDashboard.putString("ProcessingMode", "tape");
	}

	public static void setVisionModeCargo() {
		SmartDashboard.putString("ProcessingMode", "cargo");
	}

	public static void setVisionModeDriver() {
		SmartDashboard.putString("ProcessingMode", "driver");
	}

	public static double getVisionAngle1() {
		return SmartDashboard.getNumber("TargetAngle1", 0);
	}

	public static double getVisionAngle2() {
		return SmartDashboard.getNumber("TargetAngle2", 0);
	}

	public static double getVisionDistance() {
		return SmartDashboard.getNumber("TargetDistance", -1);
	}

	public static double getVisionZDistance() {
		return SmartDashboard.getNumber("TargetZDistance", -1);
	}

	public static double getVisionXDistance() {
		return SmartDashboard.getNumber("TargetXDistance", -1);
	}
}
