package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.motionProfiles.MotionProfiles;

public class AutoRocketFar extends CommandGroup {
	public AutoRocketFar() {
		addSequential(new DriveMotionProfile("platform_to_rocket_far"));
		addParallel(new HatchEjectExtend());
		addSequential(new DriveMotionProfile(MotionProfiles.generate1D(2, 12, 8, 100, true)));
		addSequential(new DriveTurnToAngleWithEncoders(-60, 14, 10));
		addParallel(new HatchEjectRetract());
		// addSequential(new DriveMotionProfile("rocket_far_to_station"));
		// addSequential(new DriveMotionProfile("station_to_rocket_far"));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1D(2.5, 12, 8, 100, false)));
	}
}
