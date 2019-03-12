package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.DriveHelper;
import frc.robot.DriveSignal;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.StatTracker;
import frc.robot.commands.DriveWithGamepad;

public class Drive extends Subsystem {
	public final double kV = 0.067;// 1 / 14;
	public final double kA = 0.034;// 0.04; 

	public final double kDistancePerPulse = 0.00904774;

	public final double kDriveP = 1.8; // 2.75;
	public final double kDriveD = 0.0; // 0.05;

	public final double kVTurn = 0;
	public final double kATurn = 0;

	public final double kTurnP = 0.006;// 0.014;
	public final double kTurnI = 0.0;
	public final double kTurnD = 0.01;// 0.034;

	public final double kTurnPEncoder = 6.0;// 5.5;
	public final double kTurnDEncoder = 0.035;//0.05;
	public final double kVEncoder = 0.067;//0.14;
	public final double kAEncoder = 0.025; //0.033; 

	public final double kTurnPGyro = 0.02;
	public final double kTurnDGyro = 0.000;//5;
	public final double kVGyro = 1.0 / 400.0;
	public final double kAGyro = 0.0015;//0.0012;

	public final double speedModTopHeight = 0.75; // 0.5;
	public final double speedModMidHeight = 0.75;
	public final double turnModTopHeight = 0.75;

	public final double fineAdjustDrive = 0.5;
	public final double fineAdjustTurn = 0.7;

	private TalonSRX rightMaster;
	private VictorSPX rightFollower1;
	private VictorSPX rightFollower2;

	private TalonSRX leftMaster;
	private VictorSPX leftFollower1;
	private VictorSPX leftFollower2;

	private Encoder leftEncoder;
	private Encoder rightEncoder;

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
		rightEncoder.setReverseDirection(false);// false on practice

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

		leftMaster.enableVoltageCompensation(true);
		leftMaster.configVoltageCompSaturation(12.5);
		rightMaster.enableVoltageCompensation(true);
		rightMaster.configVoltageCompSaturation(12.5);

		SmartDashboard.putData("Left Encoder", leftEncoder);
		SmartDashboard.putData("Right Encoder", rightEncoder);

		this.setRampRate(0);
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

		// System.out.println(getLeftVelocity());
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

	public void curvatureDrive(double throttle, double turn, boolean isQuickTurn, boolean squaredInputs) {
		DriveSignal ds = DriveHelper.curvatureDrive(throttle, turn, isQuickTurn, squaredInputs);
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

	public void setRampRate(double rampRate) {
		leftMaster.configOpenloopRamp(rampRate);
		rightMaster.configOpenloopRamp(rampRate);
	}

	public void selfTest() {
		Robot.driveLeft.setBoolean(false);
		Robot.driveRight.setBoolean(false);
		Robot.driveGyro.setBoolean(false);

		double leftStart = getLeftDistance();
		setMotorOutputs(ControlMode.PercentOutput, 0.35, 0);
		Timer.delay(0.5);
		setMotorOutputs(ControlMode.PercentOutput, 0, 0);
		Timer.delay(0.25);
		System.out.println("Left: " + getLeftDistance());
		if(getLeftDistance() - leftStart >= 0.5) {
			Robot.driveLeft.setBoolean(true);
		}

		double rightStart = getRightDistance();
		setMotorOutputs(ControlMode.PercentOutput, 0, 0.35);
		Timer.delay(0.5);
		setMotorOutputs(ControlMode.PercentOutput, 0, 0);
		Timer.delay(0.25);
		System.out.println("Right: " + getRightDistance());
		if(getRightDistance() - rightStart >= 0.5) {
			Robot.driveRight.setBoolean(true);
		}

		Robot.driveGyro.setBoolean(Robot.isIMUConnected());
	}
}
