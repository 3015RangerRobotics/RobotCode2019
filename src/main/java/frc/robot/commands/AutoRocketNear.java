package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.motionProfiles.MotionProfiles;
import frc.robot.OI;

public class AutoRocketNear extends CommandGroup {
	public AutoRocketNear(boolean mirrored) {
		this.setInterruptible(false);
		addSequential(new GyroReset());
		addParallel(new HatchGrabberExtend());
		addParallel(new HatchArmExtendDelayed(1));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(5, 8, 4, 100, false)));
		addSequential(new DriveTurnToAngleWithEncoders(87, 12, 8));
		addSequential(new DriveMotionProfile("side_platform_to_near", mirrored));
		addSequential(new DriveAutoConfirm());
		addSequential(new DriveForTime(0.2, 0.25));
		addSequential(new HatchGrabberRetract());
		addSequential(new WaitCommand(0.25));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(2, 6, 4, 100, true)));
		addSequential(new DriveTurnToAngleWithEncoders(mirrored ? 136 : -136, 12, 8));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(11.8, 6, 4, 100, false)));
		addSequential(new DriveAutoConfirm());
		addSequential(new HatchGrabberExtend());
		addSequential(new WaitCommand(0.25));
		addSequential(new DriveMotionProfile("wall_reverse_near", mirrored));
	}

	@Override
	public boolean isFinished() {
		return super.isFinished() || OI.isCancelledPressed();
	}
}
