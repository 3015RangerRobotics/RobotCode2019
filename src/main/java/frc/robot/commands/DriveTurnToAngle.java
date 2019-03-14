package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotController;
import frc.robot.Robot;

public class DriveTurnToAngle extends CommandBase implements PIDOutput {
	PIDController turnController;
	double angle = 0;
	int onTargetCount = 0;
	double minTurn = 0.2;
	boolean isAbsolute = false;

	public DriveTurnToAngle(double angle) {
		this(angle, false);
	}

	public DriveTurnToAngle(double angle, boolean isAbsolute) {
		requires(drive);
		this.angle = angle;
		this.isAbsolute = isAbsolute;
		turnController = new PIDController(drive.kTurnP, drive.kTurnI, drive.kTurnD, Robot.imu, this);
		turnController.setInputRange(-180.0, 180.0);
		turnController.setOutputRange(-1.0, 1.0);
		turnController.setAbsoluteTolerance(1.75);
		turnController.setContinuous(true);
	}

	@Override
	protected void initialize() {
		double setpoint = 0;
		if (isAbsolute) {
			setpoint = this.angle;
		} else {
			setpoint = Robot.getYaw() + this.angle;
			if (setpoint > 180) {
				setpoint -= 360;
			} else if (setpoint < -180) {
				setpoint += 360;
			}
		}
		turnController.setSetpoint(setpoint);
		turnController.enable();
		onTargetCount = 0;
	}

	@Override
	protected void execute() {
		if (turnController.onTarget()) {
			onTargetCount++;
		} else {
			onTargetCount = 0;
		}
	}

	@Override
	protected boolean isFinished() {
		return onTargetCount >= 8;
	}

	@Override
	protected void end() {
		drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
		turnController.disable();
	}

	@Override
	protected void interrupted() {
		end();
	}

	@Override
	public void pidWrite(double output) {
		if (output < 0) {
			if (minTurn > Math.abs(output)) {
				output = -minTurn;
			}
		} else {
			if (minTurn > output) {
				output = minTurn;
			}
		}
		drive.arcadeDrive(0, (output * 12.5 / RobotController.getInputVoltage()), false);
	}
}
