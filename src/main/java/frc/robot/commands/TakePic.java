package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TakePic extends CommandBase {
	public TakePic() {

	}

	@Override
	protected void initialize() {
		SmartDashboard.putBoolean("TakePic", true);
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
	}
}
