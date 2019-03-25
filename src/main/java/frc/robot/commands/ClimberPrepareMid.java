/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class ClimberPrepareMid extends CommandBase {
	public ClimberPrepareMid() {
		requires(climber);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		climber.setCenter(ControlMode.PercentOutput, 0.5);

	}

	@Override
	protected boolean isFinished() {
		return climber.getCenterPosition() >= climber.centerPosMid;
	}

	@Override
	protected void end() {
		climber.setCenter(ControlMode.PercentOutput, 0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
