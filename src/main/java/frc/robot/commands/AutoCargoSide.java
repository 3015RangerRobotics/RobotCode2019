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
		// addParallel(new HatchArmExtendDelayed(2));
		// addSequential(new DriveMotionProfile("platform_to_cargo_side_pp"));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(.5, 6, 4,
		// 100, false)));
		// addSequential(new HatchGrabberRetract());
		// addSequential(new WaitCommand(1));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(2, 6, 4,
		// 100, true)));
		// addSequential(new DriveTurnToAngleWithEncoders(105, 14, 10));

		addParallel(new HatchGrabberExtend());
		addParallel(new HatchArmExtendDelayed(.5));
		addSequential(new DriveMotionProfile("platform_to_cargo_side_line"));
		addSequential(new DriveTurnToAngleWithEncoders(92, 12, 8));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(1, 14, 10, 100, false)));
		addSequential(new HatchGrabberRetract());
		addSequential(new WaitCommand(.5));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(1.64, 14, 10, 100, true)));
		addSequential(new DriveTurnToAngleWithEncoders(89, 14, 10));
		addSequential(new DriveMotionProfile("cargo_side_to_wall"));
		addSequential(new HatchGrabberExtend());
		addSequential(new WaitCommand(1));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(2.5, 14, 10, 100, true)));
		addSequential(new DriveTurnToAngleWithEncoders(-165, 14, 10));
	}
}

