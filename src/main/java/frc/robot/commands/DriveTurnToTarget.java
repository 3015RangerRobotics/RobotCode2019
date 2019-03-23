package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.Robot;

public class DriveTurnToTarget extends CommandBase implements PIDOutput {
	PIDController turnController;
	int onTargetCount = 0;
	double minTurn = 0.18;
	boolean isCancelled = false;

	public DriveTurnToTarget() {
		requires(drive);
		turnController = new PIDController(drive.kTurnVisionP, drive.kTurnVisionI, drive.kTurnVisionD, Robot.imu, this);
		turnController.setInputRange(-180.0, 180.0);
		turnController.setOutputRange(-1.0, 1.0);
		turnController.setAbsoluteTolerance(1);
		turnController.setContinuous(true);
	}

	@Override
	protected void initialize() {
		Robot.resetIMU();
		isCancelled = false;
		if (Robot.getVisionAngle1() == 0) {
			isCancelled = true;
		}

		turnController.setSetpoint(Robot.getVisionAngle1());
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
		return onTargetCount >= 10 || isCancelled;
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
		System.out.println(output);
		drive.arcadeDrive(0, output, false);
	}
}
