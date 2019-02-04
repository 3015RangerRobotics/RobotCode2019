/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.motionProfiles.MotionProfiles;

public class DriveToTarget extends CommandBase {
	public DriveToTarget() {

	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		double distanceToTarget = (SmartDashboard.getNumber("TargetDistance", 0) / 12) - 2;
		// double angleToTarget = SmartDashboard.getNumber("TargetXAngle", 0);
		// double dx = Math.sin(Math.abs(angleToTarget)) * distanceToTarget;
		// double dy = Math.sqrt((distanceToTarget*distanceToTarget) - (dx*dx));
		// if(angleToTarget > 0) {
		// 	dx *= -1;
		// }
		// new DriveMotionProfile(MotionProfiles.generate2D(dy, dx, 0, 8, 5, 100, false)).start();
	
		new DriveMotionProfile(MotionProfiles.generate1D(distanceToTarget, 8, 5, 100, true)).start();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
