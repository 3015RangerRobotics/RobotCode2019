package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.ClimberManualWheel;

public class Climber extends Subsystem {
	private TalonSRX centerJackTalonSRX;
	private TalonSRX leftJackTalonSRX;
	private TalonSRX rightJackTalonSRX;
	private VictorSP centerWheelsVictorSP;

	private DigitalInput climberBottomLimit;

	private double backP = 0.7;
	private double backD = 0;
	private double backF = 1.2;
	private double centerP = 0.9;
	private double centerD = 0;
	private double centerF = 1.3;

	public final double pulsesPerInchCenter = 1000;
	public final double pulsesPerInchFront = 1000;

	public final double centerPosJacked = 21; //22.5;
	public final double centerPosLow = 13;
	public final double centerPosMid = 6;
	public final double centerPosHigh = 1;
	public final double backPosLow = 7;
	public final double backPosMid = 14;
	public final double backPosHigh = 20;

	public final double climbSpeed = 10;

	public boolean isPrepared = false;

	public Climber() {
		this.centerWheelsVictorSP = new VictorSP(RobotMap.climberCenterWheelsVictorSP);
		this.centerJackTalonSRX = new TalonSRX(RobotMap.climberCenterJackTalonSRX);
		this.leftJackTalonSRX = new TalonSRX(RobotMap.climberLeftJackTalonSRX);
		this.rightJackTalonSRX = new TalonSRX(RobotMap.climberRightJackTalonSRX);

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
		rightJackTalonSRX.setInverted(true);
		
		centerJackTalonSRX.setSensorPhase(true);
		leftJackTalonSRX.setSensorPhase(true);
		rightJackTalonSRX.setSensorPhase(true);

		centerJackTalonSRX.config_kP(0, centerP);
		centerJackTalonSRX.config_kD(0, centerD);
		centerJackTalonSRX.config_kF(0, centerF);

		leftJackTalonSRX.config_kP(0, backP);
		leftJackTalonSRX.config_kD(0, backD);
		leftJackTalonSRX.config_kF(0, backF);

		rightJackTalonSRX.config_kP(0, backP);
		rightJackTalonSRX.config_kD(0, backD);
		rightJackTalonSRX.config_kF(0, backF);

		centerJackTalonSRX.enableVoltageCompensation(true);
		centerJackTalonSRX.configVoltageCompSaturation(12.5);
		leftJackTalonSRX.enableVoltageCompensation(true);
		leftJackTalonSRX.configVoltageCompSaturation(12.5);
		rightJackTalonSRX.enableVoltageCompensation(true);
		rightJackTalonSRX.configVoltageCompSaturation(12.5);

		centerJackTalonSRX.setSelectedSensorPosition(0);
		leftJackTalonSRX.setSelectedSensorPosition(0);
		rightJackTalonSRX.setSelectedSensorPosition(0);

		climberBottomLimit = new DigitalInput(RobotMap.climberBottomLimit);
	}

	@Override
	public void initDefaultCommand() {
		// setDefaultCommand(new ClimberManualWheel());
	}

	@Override
	public void periodic() {
		// System.out.println(this.isReadyToClimbLevel3());
		// System.out.println("Roll: " + Robot.getRoll() + ", Pitch: " + Robot.getPitch());

		// System.out.println("Center Encoder: " + getCenterPosition());
		// System.out.println("ClimberLimit: " + isAtBottom());
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

	public void setCenterWheels(double speed) {
		centerWheelsVictorSP.set(speed);
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

	public boolean isAtBottom(){
		return !climberBottomLimit.get();
	}

	public boolean isReadyToClimbLevel3(){
		return isPrepared && Math.abs(getCenterPosition() - centerPosHigh) <= 2;
	}

	public boolean isReadyToClimbLevel2(){
		return isPrepared && Math.abs(getCenterPosition() - centerPosLow) <= 2;
	}

	public void selfTest() {
		Robot.climberLeft.setBoolean(false);
		Robot.climberRight.setBoolean(false);
		Robot.climberBack.setBoolean(false);
		Robot.climberWheel.setBoolean(false);
		Robot.climberOffsets.setBoolean(false);

		double leftStart = getBackLeftPosition();
		double rightStart = getBackRightPosition();
		double backStart = getCenterPosition();

		double initPitch = Robot.getPitch();
		double initRoll = Robot.getRoll();

		System.out.println("Init Roll: " + Robot.getRoll());

		setBackLeft(ControlMode.PercentOutput, 0.25);
		Timer.delay(1);
		if (getBackLeftPosition() - leftStart > 1) {
			Robot.climberLeft.setBoolean(true);
		}

		System.out.println("Curr Roll: " + Robot.getRoll());

		Robot.climberOffsets.setBoolean(initRoll - Robot.getRoll() > 1 && initPitch - Robot.getPitch() > 0.1);

		setBackLeft(ControlMode.PercentOutput, -0.2);
		Timer.delay(1);
		setBackLeft(ControlMode.PercentOutput, 0);
		Timer.delay(1);

		setBackRight(ControlMode.PercentOutput, 0.2);
		Timer.delay(1);
		if (getBackRightPosition() - rightStart > 1) {
			Robot.climberRight.setBoolean(true);
		}
		setBackRight(ControlMode.PercentOutput, -0.2);
		Timer.delay(1);
		setBackRight(ControlMode.PercentOutput, 0);

		Timer.delay(1);

		setCenter(ControlMode.PercentOutput, 0.2);
		Timer.delay(0.5);
		if (getCenterPosition() - backStart > 1) {
			Robot.climberBack.setBoolean(true);
		}
		setCenter(ControlMode.PercentOutput, -0.2);
		Timer.delay(1);
		setCenter(ControlMode.PercentOutput, 0);

		Timer.delay(1);

		centerWheelsVictorSP.set(0.75);
		Timer.delay(1.5);
		Robot.climberWheel.setBoolean(true);
		centerWheelsVictorSP.set(0);
	}
}