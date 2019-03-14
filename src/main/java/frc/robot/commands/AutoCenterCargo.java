package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.motionProfiles.MotionProfiles;
import frc.robot.OI;

public class AutoCenterCargo extends CommandGroup {
	public AutoCenterCargo(boolean mirrored) {
		this.setInterruptible(false);
		addParallel(new HatchGrabberExtend());
		addParallel(new HatchArmExtendDelayed(.5));
		addSequential(new DriveMotionProfile("platform_to_cargo_center", mirrored));
		addSequential(new DriveAutoConfirm());
		addSequential(new DriveForTime(0.2, 0.25));
		addSequential(new HatchGrabberRetract());
		addSequential(new WaitCommand(0.25));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(2, 12, 8, 100, true)));
		addSequential(new DriveTurnToAngleWithEncoders(mirrored ? 90 : -90, 6, 4));
		addSequential(new DriveMotionProfile("cargo_center_to_wall", mirrored));
		addSequential(new DriveAutoConfirm());
		addSequential(new HatchGrabberExtend());
		addSequential(new WaitCommand(0.25));
		addSequential(new DriveMotionProfile("wall_backup"));
	}

	@Override
	public boolean isFinished() {
		return super.isFinished() || OI.isCancelledPressed();
	}
}
