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

	public AutoCargoSide(boolean mirrored) {
		addParallel(new HatchGrabberExtend());
		addParallel(new HatchArmExtendDelayed(.5));
		addSequential(new DriveMotionProfile("platform_to_cargo_side_line", mirrored));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate2DPF(15.9, mirrored ? -2.5 : 2.5, 0, 0, 10, 6, 100, false)));
		addSequential(new DriveTurnToAngle(mirrored ? -90 : 90, true));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(1.6, 14, 10, 100, false)));
		addSequential(new DriveAutoConfirm());
		addSequential(new HatchGrabberRetract());
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(1.64, 14, 10, 100, true)));
		addSequential(new DriveTurnToAngle(mirrored ? 164 : -164, true));

		// addSequential(new DriveMotionProfile("cargo_side_to_wall", mirrored));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate2DPF(10, mirrored ? -2 : 2, 120, 180, 6, 4, 100, false)));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(18.25, 12, 8, 100, false)));
		addSequential(new DriveTurnToAngle(180, true));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(1.3, 12, 8, 100, false)));
		addSequential(new DriveAutoConfirm());
		addSequential(new HatchGrabberExtend());
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(2.5, 14, 10, 100, true)));
		addSequential(new DriveTurnToAngle(mirrored ? -170 : 170));
	}
}

