package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class ClimberPrepareHigh extends CommandGroup {
	public ClimberPrepareHigh() {
		addSequential(new ClimberPrepareHighStep1());
		addSequential(new WaitCommand(.5));
		addSequential(new ClimberPrepareHighStep2());
	}
}
