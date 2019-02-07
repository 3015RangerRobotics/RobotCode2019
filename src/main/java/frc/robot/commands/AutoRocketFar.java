package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.motionProfiles.MotionProfiles;

public class AutoRocketFar extends CommandGroup {
	public AutoRocketFar() {
		addSequential(new DriveMotionProfile("platform_farRocket"));
		addSequential(new DriveMotionProfile(MotionProfiles.generate2D(2, .5, 70, 6, 3, 100, true)));
		addSequential(new DriveMotionProfile("farRocket_station"));
		addSequential(new DriveMotionProfile("station_farRocket"));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1D(1, 6, 3, 100, false)));
	}
}