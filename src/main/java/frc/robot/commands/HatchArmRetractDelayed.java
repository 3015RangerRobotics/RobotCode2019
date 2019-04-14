package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class HatchArmRetractDelayed extends CommandGroup {
	public HatchArmRetractDelayed(double delay) {
		addSequential(new WaitCommand(delay));
		addSequential(new HatchArmRetract());
	}
}
