package frc.robot.subsystems;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.DriveHelper;
import frc.robot.DriveSignal;
import frc.robot.GraphThread;
import frc.robot.RobotMap;
import frc.robot.StatTracker;
import frc.robot.commands.DriveWithGamepad;

public class Drive extends Subsystem {
	public final double kDriveP = 1.70;
	public final double kDriveD = 0.09;

	public final double kVTurn = 0;
	public final double kATurn = 0;

	public final double kTurnP = 0.0065;// 0.005;//0.008
	public final double kTurnI = 0.0;// 0.0006
	public final double kTurnD = 0.0;// 0.08

	public final double kV = 0.067;
	public final double kA = 0.023;

	public final double kDistancePerPulse = 0.00904774 / 4;

	private TalonSRX rightMaster;
	private VictorSPX rightFollower1;
	private VictorSPX rightFollower2;

	private TalonSRX leftMaster;
	private VictorSPX leftFollower1;
	private VictorSPX leftFollower2;

	private GraphThread graphThread;

	public AHRS imu;

	private double lastDistanceLeft = 0.0;
	private double lastDistanceRight = 0.0;

	public Drive() {
		SmartDashboard.putNumber("kDriveP", kDriveP);
		SmartDashboard.putNumber("kDriveD", kDriveD);
		SmartDashboard.putNumber("kV", kV);
		SmartDashboard.putNumber("kA", kA);

		SmartDashboard.putNumber("kTurnP", kTurnP);
		SmartDashboard.putNumber("kTurnI", kTurnI);
		SmartDashboard.putNumber("kTurnD", kTurnD);

		this.leftMaster = new TalonSRX(RobotMap.leftDriveMaster);
		this.leftFollower1 = new VictorSPX(RobotMap.leftDriveFollower1);
		this.leftFollower2 = new VictorSPX(RobotMap.leftDriveFollower2);

		this.rightMaster = new TalonSRX(RobotMap.rightDriveMaster);
		this.rightFollower1 = new VictorSPX(RobotMap.rightDriveFollower1);
		this.rightFollower2 = new VictorSPX(RobotMap.rightDriveFollower2);

		leftFollower1.follow(leftMaster);
		leftFollower2.follow(leftMaster);

		rightFollower1.follow(rightMaster);
		rightFollower2.follow(rightMaster);

		rightMaster.setInverted(true);
		rightFollower1.setInverted(true);
		rightFollower2.setInverted(true);
		leftMaster.setInverted(false);
		leftFollower1.setInverted(false);
		leftFollower2.setInverted(false);

		leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

		imu = new AHRS(Port.kOnboard);

		SmartDashboard.putNumber("Left Encoder", leftMaster.getSelectedSensorPosition());
		SmartDashboard.putNumber("Right Encoder", rightMaster.getSelectedSensorPosition());
		SmartDashboard.putData("Gyro", imu);

		graphThread = new GraphThread(leftMaster, rightMaster);
	}

	@Override
	public void initDefaultCommand() {
		this.setDefaultCommand(new DriveWithGamepad());
	}

	@Override
	public void periodic() {
		if (DriverStation.getInstance().isEnabled()) {
			double left = this.leftMaster.getSelectedSensorPosition() * this.kDistancePerPulse;
			double right = this.rightMaster.getSelectedSensorPosition() * this.kDistancePerPulse;
			StatTracker.addDriveDistance(left - this.lastDistanceLeft, right - this.lastDistanceRight);
			this.lastDistanceLeft = left;
			this.lastDistanceRight = right;
		}
	}

	public void resetEncoders() {
		leftMaster.setSelectedSensorPosition(0);
		rightMaster.setSelectedSensorPosition(0);
	}

	public void setMotorOutputs(ControlMode mode, double leftMotor, double rightMotor) {
		this.leftMaster.set(mode, leftMotor);
		this.rightMaster.set(mode, rightMotor);
	}

	public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
		DriveSignal ds = DriveHelper.arcadeDrive(moveValue, rotateValue, squaredInputs);
		setMotorOutputs(ControlMode.PercentOutput, ds.leftSignal, ds.rightSignal);
	}
	public void curvatureDrive(double throttle, double turn, boolean isQuickTurn, boolean squaredInputs) {
    	DriveSignal ds = DriveHelper.curvatureDrive(throttle, turn, isQuickTurn, squaredInputs);
		setMotorOutputs(ControlMode.PercentOutput,ds.leftSignal, ds.rightSignal);
	}

	public double getLeftDistance() {
		return leftMaster.getSelectedSensorPosition();
	}

	public double getRightDistance() {
		return rightMaster.getSelectedSensorPosition();
	}

	public double getLeftVelocity() {
		return leftMaster.getSelectedSensorVelocity();
	}

	public double getRightVelocity() {
		return rightMaster.getSelectedSensorVelocity();
	}

	public double getAngle() {
		return imu.getAngle();
	}

	public void resetGyro() {
		imu.zeroYaw();
	}

	public BufferedTrajectoryPointStream getProfileBuffer(double[][] profile) {
		BufferedTrajectoryPointStream buffer = new BufferedTrajectoryPointStream();
		TrajectoryPoint point = new TrajectoryPoint();
		for (int i = 0; i < profile.length; i++) {
			point.position = profile[i][0] / this.kDistancePerPulse;
			point.velocity = profile[i][1] / this.kDistancePerPulse / 10;
			point.timeDur = (int) profile[i][2];
			point.zeroPos = (i == 0);
			point.isLastPoint = (i == profile.length - 1);
			buffer.Write(point);
		}
		return buffer;
	}

	public void startMotionProfile(BufferedTrajectoryPointStream leftBuffer,
			BufferedTrajectoryPointStream rightBuffer) {
		leftMaster.startMotionProfile(leftBuffer, 10, ControlMode.MotionProfile);
		rightMaster.startMotionProfile(rightBuffer, 10, ControlMode.MotionProfile);
	}

	public boolean isProfileFinished() {
		return leftMaster.isMotionProfileFinished() && rightMaster.isMotionProfileFinished();
	}

	public void startGraphing() {
		graphThread.start();
	}

	public void stopGraphing() {
		graphThread.stop();
	}
}