package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.BallMech;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.HatchMech;
import frc.robot.subsystems.OurCompressor;

public abstract class CommandBase extends Command {
	public static Drive drive;
	public static Elevator elevator;
	public static Climber climber;
	public static BallMech ballMech;
	public static HatchMech hatchMech;
	public static IntakeAid intakeAid;
	public static OurCompressor ourCompressor;
	public static OI oi;

	public static void init() {
		drive = new Drive();
		elevator = new Elevator();
		climber = new Climber();
		ballMech = new BallMech();
		SmartDashboard.putData("ballmech", ballMech);
		hatchMech = new HatchMech();
		intakeAid = new IntakeAid();
		ourCompressor = new OurCompressor();
		oi = new OI();
	}
}
