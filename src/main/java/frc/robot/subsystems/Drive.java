package frc.robot.subsystems;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.DriveHelper;
import frc.robot.DriveSignal;
import frc.robot.RobotMap;
import frc.robot.StatTracker;
import frc.robot.commands.DriveWithGamepad;

public class Drive extends Subsystem {
	public final double kV = 0.067;//0.67
	public final double kA = 0.023;

	public final double kDistancePerPulse = 0.00904774;

	public final double kDriveP = 1.8;//5//1.40;
	public final double kDriveD = 0.0;//0.0;

	public final double kVTurn = 0;
	public final double kATurn = 0;

	public final double kTurnP = 0.0065;// 0.005;//0.008
	public final double kTurnI = 0.0;// 0.0006
	public final double kTurnD = 0.0;// 0.08

	private TalonSRX rightMaster;
	private VictorSPX rightFollower1;
	private VictorSPX rightFollower2;

	private TalonSRX leftMaster;
	private VictorSPX leftFollower1;
	private VictorSPX leftFollower2;

	private Encoder leftEncoder;
	private Encoder rightEncoder;

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

		this.leftEncoder = new Encoder(RobotMap.leftDriveEncoder1, RobotMap.leftDriveEncoder2);
		this.rightEncoder = new Encoder(RobotMap.rightDriveEncoder1, RobotMap.rightDriveEncoder2);

		leftEncoder.setDistancePerPulse(kDistancePerPulse);
		leftEncoder.setReverseDirection(false);
		rightEncoder.setDistancePerPulse(kDistancePerPulse);
		rightEncoder.setReverseDirection(false);

		rightMaster.configFactoryDefault();
		leftMaster.configFactoryDefault();

		leftFollower1.follow(leftMaster);
		leftFollower2.follow(leftMaster);

		rightFollower1.follow(rightMaster);
		rightFollower2.follow(rightMaster);

		// leftMaster.configNeutralDeadband(0.01);
		// rightMaster.configNeutralDeadband(0.01);

		rightMaster.setInverted(true);
		rightFollower1.setInverted(true);
		rightFollower2.setInverted(true);
		leftMaster.setInverted(false);
		leftFollower1.setInverted(false);
		leftFollower2.setInverted(false);

		leftMaster.setNeutralMode(NeutralMode.Brake);
		leftFollower1.setNeutralMode(NeutralMode.Brake);
		leftFollower2.setNeutralMode(NeutralMode.Brake);
		rightMaster.setNeutralMode(NeutralMode.Brake);
		rightFollower1.setNeutralMode(NeutralMode.Brake);
		rightFollower2.setNeutralMode(NeutralMode.Brake);

		// leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		// rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

		// rightMaster.setSensorPhase(true);
		// leftMaster.setSensorPhase(false);

		// leftMaster.config_kP(0, kDriveP);
		// leftMaster.config_kI(0, kDriveI);
		// leftMaster.config_kD(0, kDriveD);
		// leftMaster.config_kF(0, kDriveF);
		// rightMaster.config_kP(0, kDriveP);
		// rightMaster.config_kI(0, kDriveI);
		// rightMaster.config_kD(0, kDriveD);
		// rightMaster.config_kF(0, kDriveF);

		// leftMaster.enableVoltageCompensation(true);
		// leftMaster.configVoltageCompSaturation(12.5);
		// rightMaster.enableVoltageCompensation(true);
		// rightMaster.configVoltageCompSaturation(12.5);

		imu = new AHRS(Port.kOnboard);

		SmartDashboard.putData("Left Encoder", leftEncoder);
		SmartDashboard.putData("Right Encoder", rightEncoder);
		SmartDashboard.putData("Gyro", imu);
	}

	@Override
	public void initDefaultCommand() {
		this.setDefaultCommand(new DriveWithGamepad());
	}

	@Override
	public void periodic() {
		if (DriverStation.getInstance().isEnabled()) {
			double left = getLeftDistance();
			double right = getRightDistance();
			StatTracker.addDriveDistance(left - this.lastDistanceLeft, right - this.lastDistanceRight);
			this.lastDistanceLeft = left;
			this.lastDistanceRight = right;
		}
	}

	public void resetEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
	}

	public void setMotorOutputs(ControlMode mode, double leftMotor, double rightMotor) {
		this.leftMaster.set(mode, leftMotor);
		this.rightMaster.set(mode, rightMotor);
	}

	public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
		DriveSignal ds = DriveHelper.arcadeDrive(moveValue, rotateValue, squaredInputs);
		setMotorOutputs(ControlMode.PercentOutput, ds.leftSignal, ds.rightSignal);
	}

	public double getLeftDistance() {
		return leftEncoder.getDistance();
	}

	public double getRightDistance() {
		return rightEncoder.getDistance();
	}

	public double getLeftVelocity() {
		return leftEncoder.getRate();
	}

	public double getRightVelocity() {
		return rightEncoder.getRate();
	}

	public double getAngle() {
		return imu.getAngle();
	}

	public void resetGyro() {
		imu.zeroYaw();
	}
}
