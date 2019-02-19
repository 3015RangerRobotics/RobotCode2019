/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ClimberLevel2 extends CommandGroup {
	public ClimberLevel2() {
		addSequential(new ClimberCenterPrepareLow());
		addSequential(new ClimberJackUpLow()); 
		addSequential(new ClimberHoldAndDrive());
		addSequential(new ClimberJackRetract()); 
	}
}
