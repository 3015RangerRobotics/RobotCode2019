package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.motionProfiles.MotionProfiles;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class DriveTurnToAngleWithEncoders extends CommandBase {
	private double[][] leftMotion;
	private double[][] rightMotion;
	private boolean isFinished = false;
	private int i = 0;
	private double prevErrorL = 0;
	private double prevErrorR = 0;
	private boolean isAbsolute;
	public double absoluteCurrent = 0;

	public DriveTurnToAngleWithEncoders(double angle, double vel, double acc, boolean isAbsolute) {
		requires(drive);
		this.isAbsolute = isAbsolute;
		if (!isAbsolute) {
			generateProfile(angle, vel, acc);
		}
	}

	public DriveTurnToAngleWithEncoders(double angle, double vel, double acc) {
		this(angle, vel, acc, false);
	}

	protected void initialize() {
		Robot.resetIMU();
		drive.resetEncoders();
		isFinished = false;
		i = 0;
		prevErrorL = 0;
		prevErrorR = 0;

		SmartDashboard.putBoolean("PathRunning", true);

		if (isAbsolute) {
			// angle -= drive.getAngle();
			// System.out.println(angle + ", " + drive.getAngle());
			// generateProfile(angle);
		}

		if (leftMotion.length != rightMotion.length) {
			System.out.println("Left and right profiles not of equal length!");
			this.cancel();
			return;
		}

		new Thread(() -> {
			double lastTime = 0;

			while (!isFinished && DriverStation.getInstance().isEnabled()) {
				if (Timer.getFPGATimestamp() >= lastTime + RobotMap.kPeriod) {
					lastTime = Timer.getFPGATimestamp();
					threadedExecute();
				}
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	protected void execute() {

	}

	protected synchronized void threadedExecute() {
		if (i == 24) {
			if (drive.getLeftDistance() == 0) {
				DriverStation.reportError("yo man left encoder is dead man", false);
				new DriveForTime(.5, 3).start();
			} else if (drive.getRightDistance() == 0) {
				DriverStation.reportError("aw dang right encoder is chooched", false);
				new DriveForTime(.5, 3).start();
			}
		}

		if (i < leftMotion.length) {
			double goalPosL = leftMotion[i][0];
			double goalVelL = leftMotion[i][1];
			double goalAccL = leftMotion[i][2];

			double goalPosR = rightMotion[i][0];
			double goalVelR = rightMotion[i][1];
			double goalAccR = rightMotion[i][2];

			double errorL = goalPosL - drive.getLeftDistance();
			double errorDerivL = ((errorL - prevErrorL) / RobotMap.kPeriod) - goalVelL;

			double errorR = goalPosR - drive.getRightDistance();
			double errorDerivR = ((errorR - prevErrorR) / RobotMap.kPeriod) - goalVelR;

			double kP = drive.kTurnPEncoder;
			double kD = drive.kTurnDEncoder;
			double kV = drive.kVEncoder;
			double kA = drive.kAEncoder;

			double pwmL = (kP * errorL) + (kD * errorDerivL) + (kV * goalVelL) + (kA * goalAccL);
			double pwmR = (kP * errorR) + (kD * errorDerivR) + (kV * goalVelR) + (kA * goalAccR);

			// System.out.println(goalPosL + ", " + goalPosR + ", " +
			// drive.getLeftDistance() + ", " + drive.getRightDistance());

			SmartDashboard.putNumber("TargetLeft", goalPosL);
			SmartDashboard.putNumber("ActualLeft", drive.getLeftDistance());

			SmartDashboard.putNumber("TargetRight", goalPosR);
			SmartDashboard.putNumber("ActualRight", drive.getRightDistance());

			prevErrorL = errorL;
			prevErrorR = errorR;

			drive.setMotorOutputs(ControlMode.PercentOutput, pwmL, pwmR);
			i++;
		} else {
			isFinished = true;
		}
	}

	protected boolean isFinished() {
		return isFinished;
	}

	protected void end() {
		SmartDashboard.putBoolean("PathRunning", false);
		drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
	}

	protected void interrupted() {
		end();
	}

	private void generateProfile(double profileAngle, double vel, double acc) {
		double arcLength = (RobotMap.wheelBaseWidth * Math.PI) * (Math.abs(profileAngle) / 360);
		arcLength *= 1.152;

		double[][] profile = MotionProfiles.generate1DPF(arcLength, vel, acc, 100, false);
		leftMotion = new double[profile.length][3];
		rightMotion = new double[profile.length][3];

		for (int i = 0; i < profile.length; i++) {
			if (profileAngle > 0) {
				rightMotion[i][0] = -profile[i][0];
				rightMotion[i][1] = -profile[i][1];
				rightMotion[i][2] = -profile[i][2];
				leftMotion[i][0] = profile[i][0];
				leftMotion[i][1] = profile[i][1];
				leftMotion[i][2] = profile[i][2];
			} else {
				rightMotion[i][0] = profile[i][0];
				rightMotion[i][1] = profile[i][1];
				rightMotion[i][2] = profile[i][2];
				leftMotion[i][0] = -profile[i][0];
				leftMotion[i][1] = -profile[i][1];
				leftMotion[i][2] = -profile[i][2];
			}
		}
	}
}