package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.motionProfiles.MotionProfiles;

public class AutoRocketFar extends CommandGroup {
	public AutoRocketFar() {
		addSequential(new DriveMotionProfile("platform_backRocket"));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1D(2, 8, 5, 100, true)));
	}
}
