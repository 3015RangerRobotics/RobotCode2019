package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.motionProfiles.MotionProfiles;
import frc.robot.Robot;

public class AutoRocketNear extends CommandGroup {
	public AutoRocketNear(boolean mirrored) {
		addSequential(new GyroReset());
		addParallel(new HatchGrabberExtend());
		addParallel(new HatchArmExtendDelayed(1));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(5, 8, 4, 100, false)));
		addSequential(new DriveTurnToAngleWithEncoders(87, 12, 8));
		// addSequential(new DriveTurnToAngle(90));
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
		

		// addSequential(new GyroReset());
		// addParallel(new HatchGrabberExtend());
		// addParallel(new HatchArmExtendDelayed(.5));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(5.2, 12, 8, 100, false)));
		// addSequential(new DriveMotionProfile("corner_to_near"));
		// addSequential(new DriveAutoConfirm());
		// addSequential(new DriveForTime(0.2, 0.25));
		// addSequential(new HatchGrabberRetract());
		// addSequential(new WaitCommand(0.2));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(1, 14, 10, 100, true)));
		// addSequential(new HatchArmRetract());
		// addSequential(new DriveTurnToAngleWithEncoders(mirrored ? 151 : -151, 14, 10));
		// addSequential(new DriveTurnToAngle(mirrored ? 132 : -132, true));
		// addParallel(new HatchArmExtend());
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(12.8, 12, 8, 100, false)));
		// addSequential(new DriveAutoConfirm());
		// addSequential(new HatchGrabberExtend());
		// addSequential(new WaitCommand(0.2));
		// addSequential(new DriveMotionProfile("wall_backup"));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(11, 12, 8, 100, true)));
		// addSequential(new DriveTurnToAngleWithEncoders(180, 12, 8));
		// addSequential(new DriveTurnToAngleWithEncoders())
		// addSequential(new DriveTurnToAngleWithEncoders/())		
	}
}

