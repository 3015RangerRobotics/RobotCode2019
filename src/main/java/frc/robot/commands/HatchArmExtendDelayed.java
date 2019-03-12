package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class HatchArmExtendDelayed extends CommandGroup {
	public HatchArmExtendDelayed(double delay) {
		addSequential(new WaitCommand(delay));
		addSequential(new HatchArmExtend());
	}
}
