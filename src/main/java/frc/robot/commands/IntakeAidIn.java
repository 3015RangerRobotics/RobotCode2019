/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.IntakeAid;

public class IntakeAidIn extends CommandBase {
	public IntakeAidIn() {
		requires(intakeAid);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		intakeAid.intakeAidIntake();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		intakeAid.intakeAidStop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
