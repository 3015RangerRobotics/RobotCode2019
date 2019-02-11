package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.BallMech;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.HatchMech;

public abstract class CommandBase extends Command {
	public static Drive drive;
	public static Elevator elevator;
	public static BallMech ballMech;
	public static HatchMech hatchMech;
	public static OI oi;

	public static void init() {
		drive = new Drive();
		elevator = new Elevator();
		ballMech = new BallMech();
		hatchMech = new HatchMech();
		oi = new OI();
	}
}
