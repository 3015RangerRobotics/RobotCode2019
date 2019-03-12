package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.motionProfiles.MotionProfiles;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class DriveTurnMotionProfile extends CommandBase {
	private volatile boolean isFinished = false;
	private double[][] profile;
	private volatile int i = 0;
	private volatile double prevError = 0;

	public DriveTurnMotionProfile(double angle) {
		requires(drive);
		profile = MotionProfiles.generate1DPF(angle, 270, 150, 1000, false);
	}

	@Override
	protected void initialize() {
		Robot.resetIMU();
		SmartDashboard.putBoolean("PathRunning", true);
		isFinished = false;
		i = 0;
		prevError = 0;

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

	protected synchronized void threadedExecute() {
		if (i < profile.length) {
			double goalPos = profile[i][0];
			double goalVel = profile[i][1];
			double goalAcc = profile[i][2];


			double error = goalPos - Robot.getYaw();
			double errorDeriv = ((error - prevError) / RobotMap.kPeriod) - goalVel;

			double kP = drive.kTurnPGyro;
			double kD = drive.kTurnDGyro;
			double kV = drive.kVGyro;
			double kA = drive.kAGyro;

			double pwm = (kP * error) + (kD * errorDeriv) + (kV * goalVel) + (kA * goalAcc);

			SmartDashboard.putNumber("TargetLeft", goalPos);
			SmartDashboard.putNumber("ActualLeft", Robot.getYaw());

			SmartDashboard.putNumber("TargetRight", goalPos);
			SmartDashboard.putNumber("ActualRight", Robot.getYaw());

			prevError = error;

			drive.setMotorOutputs(ControlMode.PercentOutput, pwm, -pwm);
			i++;
		} else {
			isFinished = true;
		}
	}

	@Override
	protected void execute() {

	}
	@Override
	protected boolean isFinished() {
		return isFinished;
	}

	@Override
	protected void end() {
		SmartDashboard.putBoolean("PathRunning", false);
		drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
	}

	@Override
	protected void interrupted() {
		isFinished = true;
		end();
	}
}