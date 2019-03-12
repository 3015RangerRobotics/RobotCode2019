package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.motionProfiles.MotionProfiles;

public class AutoCargoSideSegmented extends CommandGroup {
	public AutoCargoSideSegmented() {
		// addParallel(new HatchGrabberExtend());
		// addParallel(new HatchArmExtend());
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(16.4, 12, 8, 100, false)));
		// addSequential(new DriveTurnToAngleWithEncoders(90, 6, 4));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(.5, 12, 8, 100, false)));
		// addSequential(new HatchGrabberRetract());
		// addSequential(new WaitCommand(1));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(4, 12, 8, 100, true)));
		// addSequential(new DriveTurnToAngleWithEncoders(100, 6, 4));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(15, 12, 8, 100, false)));
		
		addParallel(new HatchGrabberExtend());
		addParallel(new HatchArmExtend());
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(16.4, 12, 8, 100, false)));
		addSequential(new DriveTurnToAngleWithEncoders(90, 6, 4));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(.5, 12, 8, 100, false)));
		addSequential(new HatchGrabberRetract());
		addSequential(new WaitCommand(1));
	}
}
