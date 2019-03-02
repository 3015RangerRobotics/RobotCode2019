/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.motionProfiles.MotionProfiles;

public class AutoCargoSide extends CommandGroup {

	public AutoCargoSide() {
		// addParallel(new HatchGrabberExtend());
		// addParallel(new HatchArmExtendDelayed(1));
		addSequential(new DriveMotionProfile("platform_to_cargo_side_pp"));
		// addSequential(new HatchGrabberRetract());
		// addSequential(new WaitCommand(.5));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(d, maxV, a, jerk, reverse)))
	}
}
