package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.*;

public abstract class CommandBase extends Command {
	public static Drive drive;
	public static Elevator elevator;
	public static BallMech ballMech;
	public static HatchMech hatchMech;
	public static OurCompressor ourCompressor;
	public static OI oi;

	public static void init() {
		drive = new Drive();
		elevator = new Elevator();
		ballMech = new BallMech();
		hatchMech = new HatchMech();
		ourCompressor = new OurCompressor();
		oi = new OI();
	}
}
