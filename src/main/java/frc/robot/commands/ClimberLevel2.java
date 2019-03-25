package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.motionProfiles.MotionProfiles;

public class ClimberLevel2 extends CommandGroup {
	public ClimberLevel2() {
		addSequential(new ClimberJackUpLow());
		addSequential(new ClimberHoldAndDrive(1.3));
		addParallel(new DriveForTime(-0.05, 1));
		addSequential(new ClimberJackRetract());
		addParallel(new ClimberPrepareMid());
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(1, 7, 4, 100, true)));
		// addSequential(new DriveLockout());
	}
}
