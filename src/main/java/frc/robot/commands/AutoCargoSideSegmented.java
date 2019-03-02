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

public class AutoCargoSideSegmented extends CommandGroup {
	/**
	 * Add your docs here.
	 */
	public AutoCargoSideSegmented() {
		addParallel(new HatchGrabberExtend());
		addParallel(new HatchArmExtend());
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(16.4, 12, 8, 100, false)));
		addSequential(new DriveTurnToAngleWithEncoders(90, 6, 4));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(.5, 12, 8, 100, false)));
		addSequential(new HatchGrabberRetract());
		addSequential(new WaitCommand(1));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(4, 12, 8, 100, true)));
		addSequential(new DriveTurnToAngleWithEncoders(100, 6, 4));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(15, 12, 8, 100, false)));
		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
	}
}
