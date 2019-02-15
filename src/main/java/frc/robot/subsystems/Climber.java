/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.CommandBase;
import frc.robot.commands.ClimberTempFrontWheel;

/**
 * Add your docs here.
 */
public class Climber extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private TalonSRX centerJackTalonSRX;
	private TalonSRX leftJackTalonSRX;
	private TalonSRX rightJackTalonSRX;
	private VictorSP centerWheelsVictorSP;
	private AnalogInput centerWheelsAnalogInput;

	private double backP = 0.0;
	private double backD = 0.0;
	private double backF = 0.0;
	private double centerP = 0.03;
	private double centerD = 0.0;
	private double centerF = 0.03;

	public final double pulsesPerInch = 200;
	public final double centerPosJacked = 24;
	public final double centerPosLow = 13000;
	public final double centerPosRetract = 0;
	public final double backPosLow = 8000;
	public final double backPosHigh = 0;
	public final double backPosRetract = 0;

	public Climber() {
		this.centerJackTalonSRX = new TalonSRX(RobotMap.climberCenterJackTalonSRX);
		this.leftJackTalonSRX = new TalonSRX(RobotMap.climberLeftJackTalonSRX);
		this.rightJackTalonSRX = new TalonSRX(RobotMap.climberRightJackTalonSRX);
		this.centerWheelsVictorSP = new VictorSP(RobotMap.climberCenterWheelsVictorSP);
		this.centerWheelsAnalogInput = new AnalogInput(RobotMap.climberCenterWheelsAnalogInput);

		centerJackTalonSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		leftJackTalonSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		rightJackTalonSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

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

	public void setBack(ControlMode mode, double value) {
		leftJackTalonSRX.set(mode, value);
		rightJackTalonSRX.set(mode, value);
	}

	public double getCenterPosition()
	{
		return centerJackTalonSRX.getSelectedSensorPosition(); 
	}

	public void test() {
		leftJackTalonSRX.set(ControlMode.PercentOutput, CommandBase.oi.getDriverRightStickY());
		rightJackTalonSRX.set(ControlMode.PercentOutput, CommandBase.oi.getDriverRightStickY());
		centerJackTalonSRX.set(ControlMode.PercentOutput, CommandBase.oi.getDriverRightStickY());
		System.out.println("Left: " + leftJackTalonSRX.getSelectedSensorPosition() + ", Right: " + rightJackTalonSRX.getSelectedSensorPosition() + ", Center: " + centerJackTalonSRX.getSelectedSensorPosition());
	}

}
