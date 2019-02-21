package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.CommandBase;

public class Robot extends TimedRobot {
	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	public static AHRS imu ;


	@Override
	public void robotInit() {
		imu = new AHRS(Port.kOnboard);
		// UsbCamera camera = new UsbCamera("USB Camera", 0);
		// camera.setResolution(640, 360);
		// camera.setFPS(30);
		CameraServer.getInstance().startAutomaticCapture(0);

		// chooser.addOption("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
		StatTracker.init();
		CommandBase.init();

		SmartDashboard.putData("Gyro", imu);

		resetIMU();
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
		// 	System.out.println("Target Distance < 0");
		// 	return -1;
		// }else{
		// 	double a = getTargetDistance();
		// 	System.out.println(a);
		// 	double correctedAngle = Math.toDegrees(Math.asin((targetDistance * Math.sin(angleInRadians)) / a));
		// 	return correctedAngle;
		// }
	}

	public static double getTargetDistance() {
		return SmartDashboard.getNumber("TargetDistance", -1);
	// 	double rawXAngle = SmartDashboard.getNumber("TargetXAngle", -1);
	// 	double targetDistance = SmartDashboard.getNumber("TargetDistance", -1);
	// 	double angleInRadians = Math.toRadians(rawXAngle);
	// 	if(targetDistance < 0) {
	// 		System.out.println("Target Distance < 0");
	// 		return -1;
	// 	}else{
	// 		double correctedDistance = Math.sqrt((RobotMap.tapeCameraOffset * RobotMap.tapeCameraOffset) + 
	// 			(targetDistance * targetDistance) - (2 * RobotMap.tapeCameraOffset * targetDistance
	// 			* Math.cos(angleInRadians)));
	// 		return correctedDistance;
	// 	}
	}
}
