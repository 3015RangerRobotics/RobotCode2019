/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.CommandBase;
import frc.robot.commands.ClimberTempFrontWheel;

public class Climber extends Subsystem {
	private TalonSRX centerJackTalonSRX;
	private TalonSRX leftJackTalonSRX;
	private TalonSRX rightJackTalonSRX;
	private VictorSP centerWheelsVictorSP;
	private AnalogInput centerWheelsAnalogInput;

	private double backP = 0.7; // 2.0
	private double backD = 0;
	private double backF = 1.2;
	private double centerP = 0.9; // 2.0
	private double centerD = 0;
	private double centerF = 1.3;

	public final double pulsesPerInchCenter = 1000;
	public final double pulsesPerInchFront = 1000; // 945

	public final double centerPosJacked = 21;
	public final double centerPosLow = 13; //10
	public final double centerPosHigh = .5; //2
	public final double backPosLow = 8;
	public final double backPosHigh = 21;

	public Climber() {
		this.centerJackTalonSRX = new TalonSRX(RobotMap.climberCenterJackTalonSRX);
		this.leftJackTalonSRX = new TalonSRX(RobotMap.climberLeftJackTalonSRX);
		this.rightJackTalonSRX = new TalonSRX(RobotMap.climberRightJackTalonSRX);
		this.centerWheelsVictorSP = new VictorSP(RobotMap.climberCenterWheelsVictorSP);
		this.centerWheelsAnalogInput = new AnalogInput(RobotMap.climberCenterWheelsAnalogInput);

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
		// setDefaultCommand(new ClimberTempFrontWheel());
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

	public void test() {
		leftJackTalonSRX.set(ControlMode.PercentOutput, CommandBase.oi.getDriverRightStickY());
		rightJackTalonSRX.set(ControlMode.PercentOutput, CommandBase.oi.getDriverRightStickY());
		centerJackTalonSRX.set(ControlMode.PercentOutput, CommandBase.oi.getDriverRightStickY());
		System.out.println("Left: " + getBackLeftPosition() + ", Right: " + getBackRightPosition() + ", Center: " + getCenterPosition());
	}
}
