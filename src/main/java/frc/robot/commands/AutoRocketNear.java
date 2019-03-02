package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.motionProfiles.MotionProfiles;
import frc.robot.Robot;

public class AutoRocketNear extends CommandGroup {
	public AutoRocketNear() {
		// addSequential(new DriveMotionProfile("test_path"));

		addParallel(new HatchGrabberExtend());
		addParallel(new HatchArmExtend());
		addSequential(new DriveMotionProfile("platform_to_rocket_near"));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(2, 12, 8, 100, false)));
		addSequential(new HatchGrabberRetract());
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(2, 12, 8, 100, true)));
		addParallel(new HatchGrabberRetract());
		addSequential(new DriveTurnToAngleWithEncoders(-159, 14, 10));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(12.3, 12, 8, 100, false)));
		addParallel(new HatchGrabberExtend());
		addSequential(new WaitCommand(0.25));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(1, 12, 8, 100, true)));
		// addSequential(new HatchArmRetract());
		// addSequential(new DriveTurnToAngleWithEncoders(180, 8, 5));
		addSequential(new DriveMotionProfile("station_backup"));
		// addSequential(new DriveMotionProfile("backup_to_rocket_near"));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(12, 12, 8, 100, false)));
		// addSequential(new DriveTurnToAngleWithEncoders(-25, 14, 10));
		// addSequential(new ElevatorToHatchMiddle());
		// addParallel(new HatchEjectExtend());
	}
}

