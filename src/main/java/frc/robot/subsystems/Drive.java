package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.DriveHelper;
import frc.robot.DriveSignal;
import frc.robot.RobotMap;
import frc.robot.commands.DriveWithGamepad;;

public class Drive extends Subsystem {
	public final double kDriveP = 0.01;
	public final double kDriveD = 0.0;

	public final double kVTurn = 0;
	public final double kATurn = 0;

	public final double kTurnP = 0.009;
	public final double kTurnI = 0;
	public final double kTurnD = 0;

	public final double kV = 0.05;
	public final double kA = 0.0;

	public final double kDistancePerPulse = 0.00904774;

	// private VictorSP rightMotors;
	// private VictorSP leftMotors;

	// private Encoder rightEncoder;
	// private Encoder leftEncoder;

	private CANSparkMax leftDrive;
	private CANSparkMax neo2;
	private CANSparkMax neo3;
	private CANSparkMax rightDrive;
	private CANSparkMax neo5;
	private CANSparkMax neo6;

	private CANEncoder leftEncoder;
	private CANEncoder rightEncoder;

	private double offsetL = 0;
	private double offsetR = 0;

	public AHRS imu;

	public Drive() {
		SmartDashboard.putNumber("kDriveP", kDriveP);
		SmartDashboard.putNumber("kDriveD", kDriveD);
		SmartDashboard.putNumber("kV", kV);
		SmartDashboard.putNumber("kA", kA);

		// this.leftMotors = new VictorSP(RobotMap.leftDriveMotors);
		// this.leftEncoder = new Encoder(RobotMap.leftDriveEncoder1, RobotMap.leftDriveEncoder2);
		// leftMotors.setInverted(false);
		// leftEncoder.setReverseDirection(false);
		// leftEncoder.setDistancePerPulse(kDistancePerPulse);

		leftDrive = new CANSparkMax(1, MotorType.kBrushless);
		neo2 = new CANSparkMax(2, MotorType.kBrushless);
		neo3 = new CANSparkMax(3, MotorType.kBrushless);
		rightDrive = new CANSparkMax(4, MotorType.kBrushless);
		neo5 = new CANSparkMax(5, MotorType.kBrushless);
		neo6 = new CANSparkMax(6, MotorType.kBrushless);

		leftEncoder = new CANEncoder(leftDrive);
		rightEncoder = new CANEncoder(rightDrive);

		SmartDashboard.putNumber("kDriveP", kDriveP);
		SmartDashboard.putNumber("kDriveD", kDriveD);

		neo2.follow(leftDrive);
		neo3.follow(leftDrive);
		neo5.follow(rightDrive);
		neo6.follow(rightDrive);

		leftDrive.setInverted(false);
		rightDrive.setInverted(true);

		// this.rightMotors = new VictorSP(RobotMap.rightDriveMotors);
		// this.rightEncoder = new Encoder(RobotMap.rightDriveEncoder1, RobotMap.rightDriveEncoder2);
		// rightMotors.setInverted(true);
		// rightEncoder.setReverseDirection(true);
		// rightEncoder.setDistancePerPulse(kDistancePerPulse);
		imu = new AHRS(Port.kOnboard);

		// SmartDashboard.putData("Left Encoder", leftEncoder);
		// SmartDashboard.putData("Right Encoder", rightEncoder);
		SmartDashboard.putData("Gyro", imu);
		resetEncoders();		
	}

	@Override
	public void initDefaultCommand() {
		this.setDefaultCommand(new DriveWithGamepad());
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("Left-Encoder", this.getLeftDistance());
		SmartDashboard.putNumber("Right-Encoder", this.getRightDistance());
		SmartDashboard.putNumber("Left-Velocity", this.getLeftVelocity());
		SmartDashboard.putNumber("Right-Velocity", this.getRightVelocity());
		// System.out.println(getAngle());
	}

	public void resetEncoders() {
		offsetL = leftEncoder.getPosition();
		offsetR = rightEncoder.getPosition();
	}

	public void setMotorOutputs(double leftDrive, double rightDrive) {
		this.leftDrive.set(leftDrive);
		this.rightDrive.set(rightDrive);
	}

	public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
		DriveSignal ds = DriveHelper.arcadeDrive(moveValue, rotateValue, squaredInputs);
		setMotorOutputs(ds.leftSignal, ds.rightSignal);
	}

	public double getLeftDistance() {
		return (leftEncoder.getPosition() - offsetL) * 0.197;
	}

	public double getRightDistance() {
		return (rightEncoder.getPosition() - offsetR) * -0.197;
	}

	public double getLeftVelocity() {
		return (leftEncoder.getVelocity() * 0.197) / 60;
	}

	public double getRightVelocity() {
		return (rightEncoder.getVelocity() * -0.197) / 60;
	}
	public double getAngle() {
		return imu.getAngle();
	}
	public void resetGyro() {
		imu.zeroYaw();
	}
}
