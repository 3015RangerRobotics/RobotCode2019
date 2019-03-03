/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.motionProfiles.MotionProfiles;

public class DriveToTargetJank extends CommandGroup {
  /**
   * Add your docs here.
   */
  public DriveToTargetJank(double angle1, double angle2, double distance) {
	  addSequential(new DriveTurnToAngleWithEncoders(angle1, 12, 8));
	//   addSequential(new DriveMotionProfile(MotionProfiles.generate2DPF(distance, 0, -angle2, 8, 4, 100, false)));
	  addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(distance, 8, 4, 100, false)));
	  addSequential(new DriveTurnToAngleWithEncoders(angle2, 12, 8));
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
