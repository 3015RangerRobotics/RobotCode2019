package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.DriveHelper;
import frc.robot.DriveSignal;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.DriveWithGamepad;

public class Drive extends Subsystem {
	public final double kV = 0.08; //0.067
	public final double kA = 0.02; //0.034

	public final double kDistancePerPulse = 0.00698131; // 0.00904774;

	public final double kDriveP = 0.7; //1.8
	public final double kDriveD = 0.01; //0.0

	public final double kVTurn = 0;
	public final double kATurn = 0;

	public final double kTurnP = 0.006;
	public final double kTurnI = 0.0;
	public final double kTurnD = 0.01;

	public final double kTurnPEncoder = 6.0;
	public final double kTurnDEncoder = 0.035;
	public final double kVEncoder = 0.067;
	public final double kAEncoder = 0.025;

	public final double kTurnPGyro = 0.02;
	public final double kTurnDGyro = 0.000;
	public final double kVGyro = 1.0 / 400.0;
	public final double kAGyro = 0.0015;

	public final double kTurnVisionP = 0.018;//0.03;
	public final double kTurnVisionI = 0.0;
	public final double kTurnVisionD = 0.0;//0.0525;//.021;

	public final double speedModMidHeight = 0.75;
	public final double speedModTopHeight = 0.75; // 0.5;
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

		leftMaster.configFactoryDefault();
		rightMaster.configFactoryDefault();

		leftFollower1.follow(leftMaster);
		leftFollower2.follow(leftMaster);
		rightFollower1.follow(rightMaster);
		rightFollower2.follow(rightMaster);

		leftMaster.setInverted(false);
		leftFollower1.setInverted(false);
		leftFollower2.setInverted(false);
		rightMaster.setInverted(true);
		rightFollower1.setInverted(true);
		rightFollower2.setInverted(true);

		leftMaster.setNeutralMode(NeutralMode.Brake);
		leftFollower1.setNeutralMode(NeutralMode.Brake);
		leftFollower2.setNeutralMode(NeutralMode.Brake);
		rightMaster.setNeutralMode(NeutralMode.Brake);
		rightFollower1.setNeutralMode(NeutralMode.Brake);
		rightFollower2.setNeutralMode(NeutralMode.Brake);

		leftMaster.enableVoltageCompensation(true);
		leftMaster.configVoltageCompSaturation(12.5);
		rightMaster.enableVoltageCompensation(true);
		rightMaster.configVoltageCompSaturation(12.5);

		SmartDashboard.putData("Left Encoder", leftEncoder);
		SmartDashboard.putData("Right Encoder", rightEncoder);

		this.setRampRate(0);
		this.setCoastMode();
	}

	@Override
	public void initDefaultCommand() {
		this.setDefaultCommand(new DriveWithGamepad());
	}

	@Override
	public void periodic() {
		// System.out.println("Drive Encoder: " + getLeftVelocity());
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

	public void setBrakeMode(){
		leftMaster.setNeutralMode(NeutralMode.Brake);
		leftFollower1.setNeutralMode(NeutralMode.Brake);
		leftFollower2.setNeutralMode(NeutralMode.Brake);
		rightMaster.setNeutralMode(NeutralMode.Brake);
		rightFollower1.setNeutralMode(NeutralMode.Brake);
		rightFollower2.setNeutralMode(NeutralMode.Brake);
	}

	public void setCoastMode(){
		leftMaster.setNeutralMode(NeutralMode.Coast);
		leftFollower1.setNeutralMode(NeutralMode.Coast);
		leftFollower2.setNeutralMode(NeutralMode.Coast);
		rightMaster.setNeutralMode(NeutralMode.Coast);
		rightFollower1.setNeutralMode(NeutralMode.Coast);
		rightFollower2.setNeutralMode(NeutralMode.Coast);
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
		this.setBrakeMode();

		Robot.driveLeft.setBoolean(false);
		Robot.driveRight.setBoolean(false);
		Robot.driveGyro.setBoolean(false);

		double leftStart = getLeftDistance();
		setMotorOutputs(ControlMode.PercentOutput, 0.35, 0);
		Timer.delay(0.5);
		setMotorOutputs(ControlMode.PercentOutput, 0, 0);
		Timer.delay(0.25);
		System.out.println("Left: " + getLeftDistance());
		if (getLeftDistance() - leftStart >= 0.5) {
			Robot.driveLeft.setBoolean(true);
		}

		double rightStart = getRightDistance();
		setMotorOutputs(ControlMode.PercentOutput, 0, 0.35);
		Timer.delay(0.5);
		setMotorOutputs(ControlMode.PercentOutput, 0, 0);
		Timer.delay(0.25);
		System.out.println("Right: " + getRightDistance());
		if (getRightDistance() - rightStart >= 0.5) {
			Robot.driveRight.setBoolean(true);
		}

		Robot.driveGyro.setBoolean(Robot.isIMUConnected());
		
		this.setCoastMode();
	}
}
