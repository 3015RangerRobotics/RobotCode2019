package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.OurCompressor;

public abstract class CommandBase extends Command {
	public static Drive drive;
	public static Elevator elevator;
	public static OurCompressor ourCompressor;
	public static OI oi;

	public static void init() {
		drive = new Drive();
		elevator = new Elevator();
		ourCompressor = new OurCompressor();
		oi = new OI();
	}
}
