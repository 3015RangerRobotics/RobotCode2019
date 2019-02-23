package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.CommandBase;

public class Climber extends Subsystem {
	private TalonSRX centerJackTalonSRX;
	private TalonSRX leftJackTalonSRX;
	private TalonSRX rightJackTalonSRX;
	private VictorSP centerWheelsVictorSP;
	private AnalogInput distanceSensor;

	private double backP = 0.7;
	private double backD = 0;
	private double backF = 1.2;
	private double centerP = 0.9;
	private double centerD = 0;
	private double centerF = 1.3;

	public final double pulsesPerInchCenter = 1000;
	public final double pulsesPerInchFront = 1000;

	public final double centerPosJacked = 21;
	public final double centerPosLow = 13;
	public final double centerPosHigh = 1;
	public final double backPosLow = 7;
	public final double backPosHigh = 20;

	public final double climbSpeed = 10;

	// +roll = lean right
	// -roll = lean left
	// +pitch = lean forward
	// -pitch = lean backward

	public Climber() {
		this.centerJackTalonSRX = new TalonSRX(RobotMap.climberCenterJackTalonSRX);
		this.leftJackTalonSRX = new TalonSRX(RobotMap.climberLeftJackTalonSRX);
		this.rightJackTalonSRX = new TalonSRX(RobotMap.climberRightJackTalonSRX);
		this.centerWheelsVictorSP = new VictorSP(RobotMap.climberCenterWheelsVictorSP);
		this.distanceSensor = new AnalogInput(RobotMap.climberDistanceSensor);

		centerJackTalonSRX.configFactoryDefault();
		leftJackTalonSRX.configFactoryDefault();
		rightJackTalonSRX.configFactoryDefault();

		centerJackTalonSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		leftJackTalonSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		rightJackTalonSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

		centerJackTalonSRX.setNeutralMode(NeutralMode.Brake);
		leftJackTalonSRX.setNeutralMode(NeutralMode.Brake);
		rightJackTalonSRX.setNeutralMode(NeutralMode.Brake);

		centerWheelsVictorSP.setInverted(true);
		// centerJackTalonSRX.setInverted(true);
		rightJackTalonSRX.setInverted(true);
		leftJackTalonSRX.setSensorPhase(true);
		rightJackTalonSRX.setSensorPhase(true);
		centerJackTalonSRX.setSensorPhase(true);

		leftJackTalonSRX.config_kP(0, backP);
		leftJackTalonSRX.config_kD(0, backD);
		leftJackTalonSRX.config_kF(0, backF);

		rightJackTalonSRX.config_kP(0, backP);
		rightJackTalonSRX.config_kD(0, backD);
		rightJackTalonSRX.config_kF(0, backF);

		centerJackTalonSRX.config_kP(0, centerP);
		centerJackTalonSRX.config_kD(0, centerD);
		centerJackTalonSRX.config_kF(0, centerF);

		centerJackTalonSRX.enableVoltageCompensation(true);
		centerJackTalonSRX.configVoltageCompSaturation(12.5);
		leftJackTalonSRX.enableVoltageCompensation(true);
		leftJackTalonSRX.configVoltageCompSaturation(12.5);
		rightJackTalonSRX.enableVoltageCompensation(true);
		rightJackTalonSRX.configVoltageCompSaturation(12.5);

		centerJackTalonSRX.setSelectedSensorPosition(0);
		leftJackTalonSRX.setSelectedSensorPosition(0);
		rightJackTalonSRX.setSelectedSensorPosition(0);
	}

	@Override
	public void initDefaultCommand() {
	}

	@Override
	public void periodic() {
		// System.out.println("roll: " + Robot.getRoll() + ", pitch: " +
		// Robot.getPitch() + ", yaw: " + Robot.getYaw());
		// System.out.println(getDistanceToWall());
		// System.out.println("X: " + Robot.getXPos() + ", Y: " + Robot.getYPos() + ",
		// Z: " + Robot.getZPos());
	}

	public void setCenter(ControlMode mode, double value) {
		centerJackTalonSRX.set(mode, value);
	}

	public void setBackLeft(ControlMode mode, double value) {
		leftJackTalonSRX.set(mode, value);
	}

	public void setBackRight(ControlMode mode, double value) {
		rightJackTalonSRX.set(mode, value);
	}

	public void setCenterVelocity(double velocity) {
		centerJackTalonSRX.set(ControlMode.Velocity, (velocity * pulsesPerInchCenter) / 10);
	}

	public void setBackVelocityLeft(double velocity) {
		leftJackTalonSRX.set(ControlMode.Velocity, (velocity * pulsesPerInchFront) / 10);
	}

	public void setBackVelocityRight(double velocity) {
		rightJackTalonSRX.set(ControlMode.Velocity, (velocity * pulsesPerInchFront) / 10);
	}

	public double getCenterPosition() {
		return centerJackTalonSRX.getSelectedSensorPosition() / pulsesPerInchCenter;
	}

	public double getBackRightPosition() {
		return rightJackTalonSRX.getSelectedSensorPosition() / pulsesPerInchFront;
	}

	public double getBackLeftPosition() {
		return leftJackTalonSRX.getSelectedSensorPosition() / pulsesPerInchFront;
	}

	public double getCenterVelocity() {
		return (centerJackTalonSRX.getSelectedSensorVelocity() / pulsesPerInchCenter) * 10;
	}

	public double getDistanceToWall() {
		return distanceSensor.getAverageVoltage() / RobotController.getInputVoltage() * 12.5;
	}

	public void setCenterWheels(double speed) {
		centerWheelsVictorSP.set(speed);
	}

	public void test() {
		leftJackTalonSRX.set(ControlMode.PercentOutput, CommandBase.oi.getDriverRightStickY());
		rightJackTalonSRX.set(ControlMode.PercentOutput, CommandBase.oi.getDriverRightStickY());
		centerJackTalonSRX.set(ControlMode.PercentOutput, CommandBase.oi.getDriverRightStickY());
		System.out.println("Left: " + getBackLeftPosition() + ", Right: " + getBackRightPosition() + ", Center: "
				+ getCenterPosition());
	}

	public double getRollOffset() {
		if (!Robot.isIMUConnected())
			return 0;

		double offset = 0;

		if (Math.abs(Robot.getRoll()) >= 2) {
			offset = (Robot.getRoll() < 0) ? -0.5 : 0.5;
		} else if (Math.abs(Robot.getRoll()) >= 5) {
			offset = (Robot.getRoll() < 0) ? -1.0 : 1.0;
		}

		return offset;
	}

	public double getPitchOffset() {
		if (!Robot.isIMUConnected())
			return 0;

		double offset = 0;

		if (Math.abs(Robot.getPitch()) >= 2) {
			offset = (Robot.getPitch() < 0) ? -0.5 : 0.5;
		} else if (Math.abs(Robot.getPitch()) >= 5) {
			offset = (Robot.getPitch() < 0) ? -1.0 : 1.0;
		}

		return offset;
	}
}