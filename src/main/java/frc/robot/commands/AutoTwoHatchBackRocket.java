package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.motionProfiles.MotionProfiles;

public class AutoTwoHatchBackRocket extends CommandGroup {
	public AutoTwoHatchBackRocket(boolean isRightStart) {
		addSequential(new HatchGrabberExtend());
		addParallel(new HatchArmExtendDelayed(0.4));
		addSequential(new DriveMotionProfile("platform_to_back_rocket", isRightStart));
		addSequential(new DriveVisionAuto(0.25));
		addSequential(new HatchGrabberRetract());
		addSequential(new WaitCommand(0.1));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(2, 14, 10, 100, true)));
		addSequential(new DriveTurnToAngleWithEncoders(isRightStart? 45 : -45, 14, 10));
		addSequential(new DriveMotionProfile("back_rocket_to_alliance", true));
		addSequential(new DriveVisionAuto(0.25));
		addSequential(new HatchGrabberExtend());
		addSequential(new WaitCommand(0.1));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(2, 14, 10, 100, true)));
		addSequential(new DriveTurnToAngleWithEncoders(isRightStart ? 170 : -170, 14, 10));
		addSequential(new DriveForTime(0.5, 0.8));
		addSequential(new DriveVisionAuto(0.25));
		addSequential(new HatchGrabberRetract());
		addSequential(new WaitCommand(0.1));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(2, 14, 10, 100, true)));
		addSequential(new DriveTurnToAngleWithEncoders(isRightStart ? -110 : 110, 14, 10));

		// addSequential(new DriveForTime(0.5, 1.1));
		// addSequential(new DriveVisionAuto(0.25));
		// addSequential(new HatchGrabberRetract());
		// addSequential(new WaitCommand(0.1));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1D(2, 14, 10, true)));
		// addSequential(new DriveTurnToAngleWithEncoders(isRightStart ? 130 : -130, 14, 10));
		// addSequential(new DriveForTime(0.5, 1.3));
		// addSequential(new DriveVisionAuto(0.25));
		// addSequential(new HatchGrabberExtend());
		// addSequential(new WaitCommand(0.2));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1D(2, 6, 4, true)));

	}
}
