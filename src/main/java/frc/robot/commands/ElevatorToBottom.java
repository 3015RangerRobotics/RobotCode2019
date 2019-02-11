/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorToBottom extends CommandBase {
	public ElevatorToBottom() {
		requires(elevator);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		elevator.set(ControlMode.Position, elevator.elevatorHeightBottom * elevator.pulsesPerInch);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return elevator.getDistance() < 1;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		elevator.set(ControlMode.PercentOutput, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}