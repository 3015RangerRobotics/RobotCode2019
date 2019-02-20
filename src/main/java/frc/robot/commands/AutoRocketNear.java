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

public class AutoRocketNear extends CommandGroup {
  public AutoRocketNear() {
	addSequential(new DriveMotionProfile("platform_to_rocket_near"));
	addParallel(new HatchEjectExtend());
	addSequential(new WaitCommand(0.75));
	addSequential(new DriveMotionProfile(MotionProfiles.generate1D(.5, 12, 8, 100, true)));
	addParallel(new HatchEjectRetract());
	addSequential(new DriveTurnToAngleWithEncoders(-148, 14, 10));
	addParallel(new ElevatorToHatch());
	addSequential(new DriveMotionProfile(MotionProfiles.generate1D(14, 12, 8, 100, false)));
	addParallel(new HatchGrabExtend());
	addSequential(new WaitCommand(0.75));
	addParallel(new HatchGrabRetract());
	addSequential(new WaitCommand(0.25));
	addSequential(new DriveMotionProfile(MotionProfiles.generate1D(.5, 12, 8, 100, true)));
	addSequential(new DriveTurnToAngleWithEncoders(180, 8, 5));
	addSequential(new DriveMotionProfile(MotionProfiles.generate1D(13, 12, 8, 100, false)));
	addSequential(new DriveTurnToAngleWithEncoders(-25, 14, 10));
	addSequential(new ElevatorToBallMiddle());
	addParallel(new HatchEjectExtend());
  }
}