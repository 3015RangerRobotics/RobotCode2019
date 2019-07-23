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

public class AutoTwoHatchFrontRocket extends CommandGroup {
	/**
	 * Add your docs here.
	 */
	public AutoTwoHatchFrontRocket(boolean isRightStart) {
		// addSequential(new HatchGrabberExtend());
		// addParallel(new HatchArmExtendDelayed(0.5));
		// addSequential(new DriveForTime(0.5, 0.75));
		// addSequential(new DriveVisionAuto(0.25));
		// addSequential(new HatchGrabberRetract());
		// addSequential(new WaitCommand(0.1));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(2, 6, 4, 100, true)));
		// addSequential(new DriveTurnToAngleWithEncoders(isRightStart ? 110 : -110, 14, 10));
		// addSequential(new DriveForTime(0.5, 0.5));
		// addSequential(new DriveVisionAuto(0.25));
		// addSequential(new HatchGrabberExtend());
		// addSequential(new WaitCommand(0.1));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(10, 6, 4, 100, true)));
		// addSequential(new DriveTurnToAngleWithEncoders(isRightStart ? 165 : -165, 14, 10));
		// addSequential(new DriveVisionAuto(.25));
		// addSequential(new HatchGrabberRetract());
		// addSequential(new WaitCommand(0.1));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(2, 6, 4, 100, true)));

	}
}
