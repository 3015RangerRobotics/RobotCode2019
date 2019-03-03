package frc.robot;

import java.util.ArrayList;

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
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.AutoCargoSide;
import frc.robot.commands.AutoRocketNear;
import frc.robot.commands.CommandBase;

public class Robot extends TimedRobot {
	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	public static AHRS imu;

	Command autoTest;

	double TARGET_STRIP_WIDTH = 2;
	double TARGET_STRIP_LENGTH = 5.5;
	double TARGET_STRIP_ROTATION = Math.toRadians(14.5);
	double TARGET_STRIP_CORNER_OFFSET = 4;

	static MatOfPoint3f objPointsMatrix = new MatOfPoint3f();
	static Mat cameraMatrix = new Mat();
	static MatOfDouble distCoefficients = new MatOfDouble();

	static double CAMERA_OFFSET_X = 0;
	static double CAMERA_OFFSET_Z = 0;
	static double CAMERA_TILT = 0;

	@Override
	public void robotInit() {
		imu = new AHRS(Port.kOnboard);
		// UsbCamera camera = new UsbCamera("USB Camera", 0);
		// camera.setResolution(640, 360);
		// camera.setFPS(30);
		// CameraServer.getInstance().startAutomaticCapture(0);

		// chooser.addOption("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);

		StatTracker.init();
		CommandBase.init();

		autoTest = new AutoCargoSide();

		SmartDashboard.putData("Gyro", imu);

		resetIMU();

		double cos_a = Math.cos(TARGET_STRIP_ROTATION);
        double sin_a = Math.sin(TARGET_STRIP_ROTATION);

        // Top left corner of the right tape [x, y, z]
		double[] pt = new double[] {TARGET_STRIP_CORNER_OFFSET, 0, 0};
		ArrayList<Point3> rightStrip = new ArrayList<>();
		rightStrip.add(new Point3(pt[0], pt[1], pt[2]));
        // Top right corner of the right tape
        pt[0] += TARGET_STRIP_WIDTH * cos_a;
        pt[1] += TARGET_STRIP_WIDTH * sin_a;
		rightStrip.add(new Point3(pt[0], pt[1], pt[2]));
        // Bottom right corner of the right tape
        pt[0] += TARGET_STRIP_LENGTH * sin_a;
        pt[1] -= TARGET_STRIP_LENGTH * cos_a;
		rightStrip.add(new Point3(pt[0], pt[1], pt[2]));
        // Bottom left corner of the right tape
        pt[0] -= TARGET_STRIP_WIDTH * cos_a;
        pt[1] -= TARGET_STRIP_WIDTH * sin_a;
		rightStrip.add(new Point3(pt[0], pt[1], pt[2]));

        // Order is clockwise from the top left for right strip (strip)
        // Left strip is a mirror of right strip
		ArrayList<Point3> leftStrip = new ArrayList<>();
		for(Point3 p: rightStrip){
			leftStrip.add(new Point3(-p.x, p.y, p.z));
		}

		ArrayList<Point3> objPoints = new ArrayList<>();
		objPoints.add(leftStrip.get(1));
		objPoints.add(leftStrip.get(2));
		objPoints.add(leftStrip.get(3));
		objPoints.add(leftStrip.get(0));
		objPoints.add(rightStrip.get(0));
		objPoints.add(rightStrip.get(3));
		objPoints.add(rightStrip.get(2));
		objPoints.add(rightStrip.get(1));

		objPointsMatrix.fromList(objPoints);
		
		cameraMatrix.put(0, 0, 772.53876202);
		cameraMatrix.put(0, 2, 479.132337442);
		cameraMatrix.put(1, 1, 769.052151477);
		cameraMatrix.put(1, 2, 359.143001808);
		cameraMatrix.put(2, 2, 1);
		
		ArrayList<Double> dist = new ArrayList<>();
		dist.add(2.9684613693070039e-01);
		dist.add(-1.4380252254747885e+00);
		dist.add(-2.2098421479494509e-03);
		dist.add(-3.3894563533907176e-03);
		dist.add(2.5344430354806740e+00);

		distCoefficients.fromList(dist);
	}

	@Override
	public void robotPeriodic() {
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
		m_autonomousCommand = m_chooser.getSelected();

		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}

		autoTest.start();

	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		autoTest.cancel();
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}

	public static float getRoll() {
		return imu.getRoll();
	}

	public static float getPitch() {
		return imu.getPitch();
	}

	public static float getYaw() {
		return imu.getYaw();
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

	public static void test() {
		double[] xPoints = SmartDashboard.getNumberArray("limelight/tcornx", new double[0]);
		double[] yPoints = SmartDashboard.getNumberArray("limelight/tcorny", new double[0]);
		ArrayList<Point> imgPoints = new ArrayList<>();
		for(int i = 0; i < xPoints.length; i++){
			imgPoints.add(new Point(xPoints[i], yPoints[i]));
		}
		MatOfPoint2f imgPointsMatrix = new MatOfPoint2f();
		imgPointsMatrix.fromList(imgPoints);

		Mat rvec = new Mat();
		Mat tvec = new Mat();

		Calib3d.solvePnP(objPointsMatrix, imgPointsMatrix, cameraMatrix, distCoefficients, rvec, tvec);

		// x = tvec[0][0] + CAMERA_OFFSET_X
        // z = (math.sin(CAMERA_TILT) * tvec[1][0]) + (math.cos(CAMERA_TILT) * tvec[2][0]) + CAMERA_OFFSET_Z
        // distance = math.sqrt(x**2 + z**2)
        // angle1 = math.degrees(math.atan2(x, z))
        // rot, _ = cv2.Rodrigues(rvec)
        // rot_inv = rot.transpose()
        // pzero_world = np.matmul(rot_inv, self.camera_offset_rotated - tvec)
        // angle2 = math.degrees(math.atan2(pzero_world[0][0], pzero_world[2][0]))
		// return distance, angle1, angle2, x, z
		
		// double x = tvec.get(0, 0) + CAMERA_OFFSET_X;

		System.out.println(tvec.get(0, 0));
	}
}
