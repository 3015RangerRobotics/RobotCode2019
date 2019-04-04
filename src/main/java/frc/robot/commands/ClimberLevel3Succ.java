/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.motionProfiles.MotionProfiles;

public class ClimberLevel3Succ extends CommandGroup {
	/**
	 * Add your docs here.
	 */
	public ClimberLevel3Succ() {
		addSequential(new ClimberJackUpHigh());
		addSequential(new ClimberHoldAndDrive(0.75));		
		addSequential(new DriveClimbConfirm());
		addSequential(new SuccerExtend());
		addSequential(new Succ());
		addSequential(new ClimberJackRetract());
	}
}
