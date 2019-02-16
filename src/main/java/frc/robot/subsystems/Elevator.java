package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.StatTracker;
import frc.robot.commands.*;

public class Elevator extends Subsystem {
	private TalonSRX elevatorTalonSRX;
	private DigitalInput elevatorBottomLimit;

	public final double elevatorHeightBottom = 0;
	public final double elevatorHeightHatch = 9;
	public final double elevatorHeightWall = 18;
	public final double elevatorHeightMiddle = 28.5; //27;
	public final double elevatorHeightTop = 58;
	public final double rocketSideLow = 0;
	public final double rocketSideMiddle = 0;
	public final double rocketSideHigh = 0;

	public final double kElevatorP = 0.6;
	public final double kElevatorI = 0.0;
	public final double kElevatorD = 0.2;
	public final double kElevatorF = 0.005;

	public final double pulsesPerInch = 331;

	private double lastDistance = 0.0;

	public Elevator() {
		elevatorTalonSRX = new TalonSRX(RobotMap.elevatorTalonSRX);
		elevatorTalonSRX.configVoltageCompSaturation(13, 10);
		elevatorTalonSRX.enableVoltageCompensation(true);
		elevatorTalonSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		elevatorTalonSRX.setSensorPhase(true);
		elevatorTalonSRX.setSelectedSensorPosition(0);
		elevatorTalonSRX.setInverted(false);
		elevatorTalonSRX.configPeakCurrentLimit(40, 10);
		elevatorTalonSRX.configPeakCurrentDuration(200, 10);
		elevatorTalonSRX.configContinuousCurrentLimit(30, 10);
		elevatorTalonSRX.enableCurrentLimit(true);
		elevatorTalonSRX.config_kP(0, kElevatorP, 10);
		elevatorTalonSRX.config_kI(0, kElevatorI, 10);
		elevatorTalonSRX.config_kD(0, kElevatorD, 10);
		elevatorTalonSRX.config_kF(0, kElevatorF, 10);
		elevatorTalonSRX.configPeakOutputForward(1.0, 10);
		elevatorTalonSRX.configPeakOutputReverse(-0.2, 10);

		elevatorBottomLimit = new DigitalInput(RobotMap.elevatorBottomLimit);
	}

	@Override
	public void initDefaultCommand() {
	}

	public void periodic() {
		// if (isAtBottom() && elevatorTalonSRX.getSelectedSensorPosition(0) < 65) {
		// elevatorTalonSRX.setSelectedSensorPosition(0, 0, 0);
		// }

		if (Math.abs(elevatorTalonSRX.getMotorOutputVoltage()) >= 4
				&& Math.abs(elevatorTalonSRX.getSelectedSensorVelocity(0) / pulsesPerInch) < 0.25) {
			CommandBase.oi.coDriverRumble(1.0);
		} else {
			CommandBase.oi.coDriverRumble(0);
		}

		// System.out.println(getDistance() + ", " + elevatorTalonSRX.getOutputCurrent());

		SmartDashboard.putNumber("Elevator Encoder", getDistance());
		SmartDashboard.putBoolean("Elevator Bottom", isAtBottom());

		if (DriverStation.getInstance().isEnabled()) {
			double distance = this.elevatorTalonSRX.getSelectedSensorPosition() / this.pulsesPerInch;
			StatTracker.addElevatorDistance(distance - lastDistance);
			this.lastDistance = distance;
		}

		System.out.println(getDistance());
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
