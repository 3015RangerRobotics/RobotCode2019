/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class ClimberCenterPrepareLow extends CommandBase {
	public ClimberCenterPrepareLow() {
		requires(climber);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		climber.setCenter(ControlMode.PercentOutput, 0.39);
		// System.out.println("hi my name is Keyshawn and I love lasagna");
		System.out.println(climber.getCenterPosition());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return climber.getCenterPosition() >= climber.centerPosLow;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		climber.setCenter(ControlMode.PercentOutput, 0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
