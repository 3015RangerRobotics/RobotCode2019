package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Drive;

public abstract class CommandBase extends Command {
	public static Drive drive;
	public static OI oi;

	public static void init() {
		drive = new Drive();
		oi = new OI();
	}
}
