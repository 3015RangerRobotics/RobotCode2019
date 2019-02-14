package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.ControllerPower;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTurnToAngle extends CommandBase implements PIDOutput {
	PIDController turnController;
	double setpoint = 0;
	int onTargetCount = 0;
	double minTurn = 0.22;

	public DriveTurnToAngle(double angle) {
		this(angle, false);
	}

	public DriveTurnToAngle(double angle, boolean isAbsolute) {
		requires(drive);
		this.setpoint = angle;
		turnController = new PIDController(drive.kTurnP, drive.kTurnI, drive.kTurnD, drive.imu, this);
		turnController.setInputRange(-180.0, 180.0);
		turnController.setOutputRange(-1.0, 1.0);
		turnController.setAbsoluteTolerance(5.0);
		turnController.setContinuous(true);
	}

	@Override
	protected void initialize() {
		drive.resetGyro();
		turnController.setP(SmartDashboard.getNumber("kTurnP", 0));
		turnController.setI(SmartDashboard.getNumber("kTurnI", 0));
		turnController.setD(SmartDashboard.getNumber("kTurnD", 0));
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
		return onTargetCount >= 10;
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
		drive.arcadeDrive(0, (output * 12.5 / ControllerPower.getInputVoltage()), false);
	}
}
