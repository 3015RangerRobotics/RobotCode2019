package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.motionProfiles.MotionProfiles;

public class ClimberLevel3 extends CommandGroup {
	public ClimberLevel3() {
		// addSequential(new ClimberCenterPrepareHigh());
		// addSequential(new WaitCommand(1));
		addSequential(new ClimberJackUpHigh());
		addSequential(new ClimberHoldAndDrive());
		addParallel(new DriveForTime(-0.05, 1));
		addSequential(new ClimberJackRetract());
		addSequential(new DriveMotionProfile(MotionProfiles.generate1DPF(1, 7, 4, 100, true)));
		addSequential(new DriveLockout());
	}
}
