package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestRobot extends CommandGroup {

	public TestRobot() {
		addSequential(new TestBallMech());
		addSequential(new TestHatchMech());
		addSequential(new TestClimber());
		addSequential(new TestDrive());
		addSequential(new TestElevator());
	}
}
