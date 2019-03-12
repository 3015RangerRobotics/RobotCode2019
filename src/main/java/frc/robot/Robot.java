package frc.robot;

import java.util.ArrayList;
import java.util.Map;

import com.kauailabs.navx.frc.AHRS;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.MatOfPoint3f;
import org.opencv.core.Point;
import org.opencv.core.Point3;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.AutoCargoSide;
import frc.robot.commands.AutoCenterCargo;
import frc.robot.commands.AutoRocketFar;
import frc.robot.commands.AutoRocketNear;
import frc.robot.commands.CommandBase;
import frc.robot.commands.TestBallMech;
import frc.robot.commands.TestDrive;
import frc.robot.commands.TestHatchMech;

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

	@Override
	public void robotInit() {
		imu = new AHRS(Port.kMXP);
		// UsbCamera camera = new UsbCamera("USB Camera", 0);
		// camera.setResolution(640, 360);
		// camera.setFPS(30);
		// CameraServer.getInstance().startAutomaticCapture(0);

		StatTracker.init();
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
				.getLayout("Ball Mech", BuiltInLayouts.kList).withSize(2, 2).withPosition(3, 0);
		ballIn = ballValues.add("In", false).getEntry();
		ballOut = ballValues.add("Out", false).getEntry();

		resetIMU();
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
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
		resetIMU();
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
		SmartDashboard.putNumber("Gyro1", imu.getAngle());
		// System.out.println(imu.getAngle());
	}

	@Override
	public void testPeriodic() {
	}

	public static float getRoll() {
		return imu.getRoll() + 35;
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
		// double rawXAngle = SmartDashboard.getNumber("TargetXAngle", -1);
		// double targetDistance = SmartDashboard.getNumber("TargetDistance", -1);
		// double angleInRadians = Math.toRadians(rawXAngle);
		// if(targetDistance < 0){
		// System.out.println("Target Distance < 0");
		// return -1;
		// }else{
		// double a = getTargetDistance();
		// System.out.println(a);
		// double correctedAngle = Math.toDegrees(Math.asin((targetDistance *
		// Math.sin(angleInRadians)) / a));
		// return correctedAngle;
		// }
	}

	public static double getTargetDistance() {
		return SmartDashboard.getNumber("TargetDistance", -1);
		// double rawXAngle = SmartDashboard.getNumber("TargetXAngle", -1);
		// double targetDistance = SmartDashboard.getNumber("TargetDistance", -1);
		// double angleInRadians = Math.toRadians(rawXAngle);
		// if(targetDistance < 0) {
		// System.out.println("Target Distance < 0");
		// return -1;
		// }else{
		// double correctedDistance = Math.sqrt((RobotMap.tapeCameraOffset *
		// RobotMap.tapeCameraOffset) +
		// (targetDistance * targetDistance) - (2 * RobotMap.tapeCameraOffset *
		// targetDistance
		// * Math.cos(angleInRadians)));
		// return correctedDistance;
		// }
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
