/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class ElevatorHold extends CommandBase {
	private double startPos;

	public ElevatorHold() {
		requires(elevator);
	}

	protected void initialize() {
		startPos = elevator.getRawDistance();
	}

	protected void execute() {
		elevator.set(ControlMode.Position, startPos);

	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		elevator.set(ControlMode.PercentOutput, 0);
	}

	protected void interrupted() {
		end();
	}
}