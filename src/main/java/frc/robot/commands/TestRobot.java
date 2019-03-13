package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class TestRobot extends CommandGroup {

	public TestRobot() {
		addSequential(new TestElevator());
		addSequential(new TestHatchMech());
		addSequential(new TestDrive());
		addSequential(new TestClimber());
		addSequential(new TestBallMech());
	}
}
