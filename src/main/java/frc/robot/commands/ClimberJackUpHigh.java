/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class ClimberJackUpHigh extends CommandBase {
	boolean isCenterAtTarget = false;
	boolean isLeftAtTarget = false;
	boolean isRightAtTarget = false;

	public ClimberJackUpHigh() {
		requires(climber);
	}

	@Override
	protected void initialize() {
		isCenterAtTarget = false;
		isLeftAtTarget = false;
		isRightAtTarget = false;
	}

	@Override
	protected void execute() {
		if (climber.getCenterPosition() <= climber.centerPosJacked && !isCenterAtTarget) {
			climber.setCenterVelocity(3);
		} else {
			climber.setCenter(ControlMode.PercentOutput, 0.1);
			isCenterAtTarget = true;
		}

		if (climber.getBackLeftPosition() <= climber.backPosHigh && !isLeftAtTarget) {
			climber.setBackVelocityLeft(3);
		} else {
			climber.setBackLeft(ControlMode.PercentOutput, 0.1);
			isLeftAtTarget = true;
		}

		if (climber.getBackRightPosition() <= climber.backPosHigh && !isRightAtTarget) {
			climber.setBackVelocityRight(3);
		} else {
			climber.setBackRight(ControlMode.PercentOutput, 0.1);
			isRightAtTarget = true;
		}

		System.out.println(climber.getCenterVelocity());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		climber.setCenter(ControlMode.PercentOutput, 0);
		climber.setBackLeft(ControlMode.PercentOutput, 0);
		climber.setBackRight(ControlMode.PercentOutput, 0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
