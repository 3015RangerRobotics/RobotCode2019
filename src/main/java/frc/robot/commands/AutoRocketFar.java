package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.motionProfiles.MotionProfiles;

public class AutoRocketFar extends CommandGroup {
	public AutoRocketFar(boolean mirrored) {
		addParallel(new HatchGrabberExtend());
		addParallel(new HatchArmExtendDelayed(1));
		addSequential(new DriveMotionProfile("platform_to_rocket_far_new", mirrored));
		addSequential(new DriveAutoConfirm());
		addSequential(new HatchGrabberRetract());
		addSequential(new WaitCommand(.5));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(1, 12, 8, 100, true)));

		// Make sure HatchGrabber is extended before starting auto
		// addParallel(new HatchGrabberExtend());
		// addParallel(new HatchArmExtend());
		// addSequential(new DriveMotionProfile("platform_to_rocket_far", mirrored));
		// addParallel(new HatchGrabberRetract());
		// addSequential(new WaitCommand(0.25));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(2, 12, 8, 100, true)));
		// addSequential(new DriveTurnToAngleWithEncoders(-60, 14, 10));
		// addSequential(new DriveMotionProfile("back_rocket_to_alliance"));
		// addSequential(new HatchGrabberExtend());
		// addSequential(new DriveMotionProfile("rocket_far_to_station"));
		// addSequential(new DriveMotionProfile("station_to_rocket_far"));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1D(2.5, 12, 8, 100, false)));
	}
}
