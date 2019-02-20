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

public class ClimberLevel3 extends CommandGroup {
  /**
   * Add your docs here.
   */
  public ClimberLevel3() {
	addSequential(new ClimberCenterPrepareHigh());
	addSequential(new WaitCommand(1));
	addSequential(new ClimberJackUpHigh()); 
	addSequential(new ClimberHoldAndDrive());
	addSequential(new ClimberJackRetract()); 
	addSequential(new DriveMotionProfile(MotionProfiles.generate1D(1, 7, 4, 100, true)));
  }
}
