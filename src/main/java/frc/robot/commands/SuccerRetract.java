package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Succer;

public class SuccerRetract extends CommandBase {
	public SuccerRetract() {
		requires(succer);
	}

	@Override
	protected void initialize() {
		succer.succerPistonRetract();
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end();
	}
}
