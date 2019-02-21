/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.ControllerPower;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class DriveAlignWithTarget extends CommandBase implements PIDOutput {
	PIDController turnController;
	double setpoint = 0;
	double targetY = 0;
	double targetX = 0;
	double minTurn = 0.25;// 18;//.2;

	public DriveAlignWithTarget() {
		requires(drive);
		turnController = new PIDController(drive.kTurnP, drive.kTurnI, drive.kTurnD, Robot.imu, this);
		turnController.setInputRange(-180.0, 180.0);
		turnController.setOutputRange(-1.0, 1.0);
		turnController.setAbsoluteTolerance(2.0);
		turnController.setContinuous(true);
	}

	@Override
	protected void initialize() {
		double targetDistance = Robot.getTargetDistance();
		if (targetDistance < 0 || !Robot.isIMUConnected()) {
			System.out.println("oopsies");
			this.cancel();
		}

		this.setpoint = Robot.getTargetXAngle();
		Robot.resetIMU();
		turnController.setP(SmartDashboard.getNumber("kTurnP", 0));
		turnController.setI(SmartDashboard.getNumber("kTurnI", 0));
		turnController.setD(SmartDashboard.getNumber("kTurnD", 0));
		turnController.setSetpoint(setpoint);
		turnController.enable();

		System.out.println(this.setpoint);
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return false;
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

		drive.arcadeDrive(oi.getDriverLeftStickY(), turnController.onTarget() ? 0 : output, false);
	}
}