/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorManualControl extends CommandBase {
	double position = 0;

	public ElevatorManualControl() {
		requires(elevator);
	}

	protected void initialize() {
		this.position = elevator.getRawDistance();
	}

	protected void execute() {
		if ((!elevator.isAtBottom() || oi.getCoDriverSumTriggers() > 0) || elevator.getDistance() > 65) {
			this.position += oi.getCoDriverSumTriggers() * 100;
		}
		elevator.set(ControlMode.Position, position);
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