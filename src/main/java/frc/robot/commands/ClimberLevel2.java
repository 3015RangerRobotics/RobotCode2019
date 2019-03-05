package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.motionProfiles.MotionProfiles;

public class ClimberLevel2 extends CommandGroup {
	public ClimberLevel2() {
		// addSequential(new ClimberCenterPrepareLow());
		// addSequential(new WaitCommand(1));
		addSequential(new ClimberJackUpLow());
		addSequential(new ClimberHoldAndDrive());
		addSequential(new ClimberJackRetract());
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(1, 7, 4, 100, true)));
	}
}
