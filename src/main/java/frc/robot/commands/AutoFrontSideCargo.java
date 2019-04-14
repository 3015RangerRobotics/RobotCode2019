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

public class AutoFrontSideCargo extends CommandGroup {
	/**
	 * Add your docs here.
	 */
	public AutoFrontSideCargo(boolean isRightStart) {
		addSequential(new HatchGrabberExtend());
		addParallel(new HatchArmExtendDelayed(0.5));
		addSequential(new DriveForTime(0.5, 0.8));
		addSequential(new DriveVisionAuto(0.25));
		addSequential(new HatchGrabberRetract());
		addSequential(new WaitCommand(0.1));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(2, 14, 10, 100, true)));
		addSequential(new DriveTurnToAngleWithEncoders(isRightStart ? 90 : -90, 14, 10));
		addSequential(new DriveMotionProfile("front_cargo_to_alliance", isRightStart));
		addSequential(new DriveVisionAuto(0.25));
		addSequential(new HatchGrabberExtend());
		addSequential(new WaitCommand(0.1));
		addParallel(new HatchArmRetractDelayed(1));
		addSequential(new DriveMotionProfile("alliance_backwards_to_side_cargo", isRightStart));
		addSequential(new HatchArmExtend());
		addSequential(new DriveForTime(0.5, 0.2));
		addSequential(new DriveVisionAuto(0.25));
		addSequential(new HatchGrabberRetract());
		addSequential(new WaitCommand(0.1));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(2, 14, 10, 100, true)));
	}
}
