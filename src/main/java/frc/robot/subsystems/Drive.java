package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

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
	public final double kDriveP = 1.70;
	public final double kDriveD = 0.09;

	public final double kVTurn = 0;
	public final double kATurn = 0;

	public final double kTurnP = 0;
	public final double kTurnI = 0;
	public final double kTurnD = 0;

	public final double kV = 0.067;
	public final double kA = 0.023;

	public final double kDistancePerPulse = 0.00904774;

	private VictorSP rightMotors;
	private VictorSP leftMotors;

	private Encoder rightEncoder;
	private Encoder leftEncoder;

	public AHRS imu;

	public Drive() {
		SmartDashboard.putNumber("kDriveP", kDriveP);
		SmartDashboard.putNumber("kDriveD", kDriveD);
		SmartDashboard.putNumber("kV", kV);
		SmartDashboard.putNumber("kA", kA);

		SmartDashboard.putNumber("kTurnP", kTurnP);
		SmartDashboard.putNumber("kTurnI", kTurnI);
		SmartDashboard.putNumber("kTurnD", kTurnD);

		this.leftMotors = new VictorSP(RobotMap.leftDriveMotors);
		this.leftEncoder = new Encoder(RobotMap.leftDriveEncoder1, RobotMap.leftDriveEncoder2);
		leftMotors.setInverted(false);
		leftEncoder.setReverseDirection(false);
		leftEncoder.setDistancePerPulse(kDistancePerPulse);

		this.rightMotors = new VictorSP(RobotMap.rightDriveMotors);
		this.rightEncoder = new Encoder(RobotMap.rightDriveEncoder1, RobotMap.rightDriveEncoder2);
		rightMotors.setInverted(true);
		rightEncoder.setReverseDirection(true);
		rightEncoder.setDistancePerPulse(kDistancePerPulse);
		imu = new AHRS(Port.kOnboard);

		SmartDashboard.putData("Left Encoder", leftEncoder);
		SmartDashboard.putData("Right Encoder", rightEncoder);
		SmartDashboard.putData("Gyro", imu);
	}

	@Override
	public void initDefaultCommand() {
		this.setDefaultCommand(new DriveWithGamepad());
	}

	public void resetEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
	}

	public void setMotorOutputs(double leftMotor, double rightMotor) {
		this.leftMotors.set(leftMotor);
		this.rightMotors.set(rightMotor);
	}

	public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
		DriveSignal ds = DriveHelper.arcadeDrive(moveValue, rotateValue, squaredInputs);
		setMotorOutputs(ds.leftSignal, ds.rightSignal);
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
