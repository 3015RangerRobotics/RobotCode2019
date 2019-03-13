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
		addParallel(new ElevatorToTop());
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(3.2, 5, 3, 100, false)));
		addSequential(new DriveAutoConfirm());
		addSequential(new HatchGrabberRetract());
		addSequential(new WaitCommand(0.25));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(1, 6, 4, 100, true)));
		addParallel(new ElevatorToBottom());
		addSequential(new DriveTurnToAngleWithEncoders(mirrored ? 57 : -57, 14, 10));
		addSequential(new DriveMotionProfile("back_rocket_to_wall", mirrored));
		addSequential(new DriveAutoConfirm());
		addSequential(new HatchGrabberExtend());
		addSequential(new WaitCommand(0.25));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(7, 16, 12, 300, true)));
	}
}
