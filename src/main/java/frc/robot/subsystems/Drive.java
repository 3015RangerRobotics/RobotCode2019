package frc.robot.subsystems;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.DriveHelper;
import frc.robot.DriveSignal;
import frc.robot.RobotMap;
import frc.robot.commands.DriveWithGamepad;

public class Drive extends Subsystem {
	public final double kDriveP = 1.70;
	public final double kDriveD = 0.09;

	public final double kVTurn = 0;
	public final double kATurn = 0;

	public final double kTurnP = 0.0065;//0.005;//0.008
	public final double kTurnI = 0.0;//0.0006
	public final double kTurnD = 0.0;//0.08

	public final double kV = 0.067;
	public final double kA = 0.023;

	public final double kDistancePerPulse = 0.00904774;

	private TalonSRX rightMaster;
	private VictorSPX rightFollower1;
	private VictorSPX rightFollower2;

	private TalonSRX leftMaster;
	private VictorSPX leftFollower1;
	private VictorSPX leftFollower2;

	public AHRS imu;

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

		leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

		// this.leftMotors = new VictorSP(RobotMap.leftDriveMotors);
		// this.leftEncoder = new Encoder(RobotMap.leftDriveEncoder1, RobotMap.leftDriveEncoder2);
		// leftMotors.setInverted(false);
		// leftEncoder.setReverseDirection(false);
		// leftEncoder.setDistancePerPulse(kDistancePerPulse);

		// this.rightMotors = new VictorSP(RobotMap.rightDriveMotors);
		// this.rightEncoder = new Encoder(RobotMap.rightDriveEncoder1, RobotMap.rightDriveEncoder2);
		// rightMotors.setInverted(true);
		// rightEncoder.setReverseDirection(true);
		// rightEncoder.setDistancePerPulse(kDistancePerPulse);
		// imu = new AHRS(Port.kOnboard);

		SmartDashboard.putNumber("Left Encoder", leftMaster.getSelectedSensorPosition());
		SmartDashboard.putNumber("Right Encoder", rightMaster.getSelectedSensorPosition());
		SmartDashboard.putData("Gyro", imu);
	}

	@Override
	public void initDefaultCommand() {
		this.setDefaultCommand(new DriveWithGamepad());
	}

	public void resetEncoders() {
		leftMaster.setSelectedSensorPosition(0);
		rightMaster.setSelectedSensorPosition(0);
	}

	public void setMotorOutputs(double leftMotor, double rightMotor) {
		this.leftMaster.set(ControlMode.PercentOutput, leftMotor);
		this.rightMaster.set(ControlMode.PercentOutput, rightMotor);
	}

	public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
		DriveSignal ds = DriveHelper.arcadeDrive(moveValue, rotateValue, squaredInputs);
		setMotorOutputs(ds.leftSignal, ds.rightSignal);
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
		for(int i = 0; i < profile.length; i++) {
			point.position = profile[i][0] / this.kDistancePerPulse;
			point.velocity = profile[i][1] / this.kDistancePerPulse / 10;
			point.timeDur = (int) profile[i][2];
			point.zeroPos = (i == 0);
			point.isLastPoint = (i == profile.length - 1);
			buffer.Write(point);
		}
		return buffer;
	}
	
	public void startMotionProfile(BufferedTrajectoryPointStream leftBuffer, BufferedTrajectoryPointStream rightBuffer) {
		leftMaster.startMotionProfile(leftBuffer, 10, ControlMode.MotionProfile);
		rightMaster.startMotionProfile(rightBuffer, 10, ControlMode.MotionProfile);
	}
	
	public boolean isProfileFinished() {
		return leftMaster.isMotionProfileFinished() && rightMaster.isMotionProfileFinished();
	}
}