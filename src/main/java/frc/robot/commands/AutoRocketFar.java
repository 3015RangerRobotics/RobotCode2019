package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.motionProfiles.MotionProfiles;

public class AutoRocketFar extends CommandGroup {
	public AutoRocketFar(boolean mirrored) {
		addSequential(new GyroReset());
		addParallel(new HatchGrabberExtend());
		addParallel(new HatchArmExtendDelayed(1));
		addSequential(new DriveMotionProfile("backwards_to_far_rocket", mirrored));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(3.4, 14, 10, 100, false)));
		addSequential(new DriveAutoConfirm());
		addSequential(new HatchGrabberRetract());
		addSequential(new WaitCommand(0.25));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(1, 14, 10, 100, true)));
		addSequential(new DriveTurnToAngleWithEncoders(mirrored ? 58 : -58, 14, 10));
		addSequential(new DriveMotionProfile("back_rocket_to_wall", mirrored));
		addSequential(new DriveAutoConfirm());
		addSequential(new HatchGrabberExtend());
		addSequential(new WaitCommand(0.25));
		addSequential(new DriveMotionProfile("wall_backup", mirrored));
	}
}
