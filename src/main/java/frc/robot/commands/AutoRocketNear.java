package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.motionProfiles.MotionProfiles;
import frc.robot.Robot;

public class AutoRocketNear extends CommandGroup {
	public AutoRocketNear() {
		addParallel(new HatchGrabberExtend());
		addParallel(new HatchArmExtendDelayed(1));
		addSequential(new DriveMotionProfile("platform_to_rocket_near_with_new_wheels"));
		// addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(1, 12, 8, 100, false)));
		addSequential(new DriveAutoConfirm());
		addSequential(new HatchGrabberRetract());
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(2, 12, 8, 100, true)));
		addSequential(new DriveTurnToAngleWithEncoders(-151, 14, 10)); // was -160 before test
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(12.3, 12, 8, 100, false)));
		addSequential(new DriveAutoConfirm());
		addParallel(new HatchGrabberExtend());

		// addSequential(new DriveMotionProfile("station_backup"));
		// addSequential(new DriveMotionProfile("backup_to_rocket_near"));
		
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(1, 12, 8, 100, true)));
		// addSequential(new HatchArmRetract());
		// addSequential(new DriveMotionProfile("station_to_rocket_near"));
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(8, 12, 8, 100, true)));
		addSequential(new DriveTurnToAngleWithEncoders(-180, 8, 5));
		// addSequential(new DriveTurnToAngleWithEncoders(-25, 14, 10));
		// addSequential(new ElevatorToHatchMiddle());
		// addParallel(new HatchEjectExtend());

		// finish making k-turn 
		// finish making 180 turn
		// test the different between k-turn and 180 turn
		
	}
}

