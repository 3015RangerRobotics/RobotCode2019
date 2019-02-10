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

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.CommandBase;

/**
 * Add your docs here.
 */
public class Elevator extends Subsystem {
	private TalonSRX elevatorTalonSRX;
	private DigitalInput elevatorBottomLimit;

	public final double elevatorMaxV = 10.0;
	public final double elevatorAcc = 15.0;
	public final double elevatorHeightBottom = 0;
	public final double elevatorHeightMiddle = 10; // Switch = 26;
	public final double elevatorHeightTop = 20; // = 77;
	// public final double elevatorHeightScaleLow = 57;
	public final double kElevatorP = 0.6;
	public final double kElevatorI = 0.0;
	public final double kElevatorD = 0.2;
	public final double kElevatorF = 0.005;
	public final double kV = 0.04;
	public final double kA = 0.01;

	public final double pulsesPerInch = 331;

	public Elevator() {
		elevatorTalonSRX = new TalonSRX(RobotMap.elevatorTalonSRX);
		elevatorTalonSRX.configVoltageCompSaturation(13, 10);
		elevatorTalonSRX.enableVoltageCompensation(true);
		elevatorTalonSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		elevatorTalonSRX.setSensorPhase(true);
		elevatorTalonSRX.setSelectedSensorPosition(0, 0, 0);
		elevatorTalonSRX.setInverted(false);
		elevatorTalonSRX.configPeakCurrentLimit(40, 10);
		elevatorTalonSRX.configPeakCurrentDuration(200, 10);
		elevatorTalonSRX.configContinuousCurrentLimit(30, 10);
		elevatorTalonSRX.enableCurrentLimit(true);
		elevatorTalonSRX.config_kP(0, kElevatorP, 0);
		elevatorTalonSRX.config_kI(0, kElevatorI, 0);
		elevatorTalonSRX.config_kD(0, kElevatorD, 0);
		elevatorTalonSRX.config_kF(0, kElevatorF, 0);
		elevatorTalonSRX.configPeakOutputForward(1.0, 10);
		elevatorTalonSRX.configPeakOutputReverse(-0.4, 10);

		elevatorBottomLimit = new DigitalInput(RobotMap.elevatorBottomLimit);
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void periodic() {
		if (isAtBottom() && elevatorTalonSRX.getSelectedSensorPosition(0) < 65) {
			elevatorTalonSRX.setSelectedSensorPosition(0, 0, 0);
		}

		if (Math.abs(elevatorTalonSRX.getMotorOutputVoltage()) >= 4
				&& Math.abs(elevatorTalonSRX.getSelectedSensorVelocity(0) / pulsesPerInch) < 0.25) {
			CommandBase.oi.coDriverRumble(1.0);
		} else {
			CommandBase.oi.coDriverRumble(0);
		}

		System.out.println(getRawDistance());

		SmartDashboard.putNumber("Elevator Encoder", getDistance());
		SmartDashboard.putBoolean("Elevator Bottom", isAtBottom());
	}

	/**
	 * Set the elevator motor output
	 * 
	 * @param mode  The control mode of the talon
	 * @param value The value to set
	 */
	public void set(ControlMode mode, double value) {
		elevatorTalonSRX.set(mode, value);
	}

	/**
	 * @return The distance of the elevator encoder in inches
	 */
	public double getDistance() {
		return elevatorTalonSRX.getSelectedSensorPosition(0) / pulsesPerInch;
	}

	/**
	 * @return The raw distance of the elevator encoder
	 */
	public double getRawDistance() {
		return elevatorTalonSRX.getSelectedSensorPosition(0);
	}

	/**
	 * @return Is the elevator at the bottom limit
	 */
	public boolean isAtBottom() {
		return !elevatorBottomLimit.get();
	}
}
