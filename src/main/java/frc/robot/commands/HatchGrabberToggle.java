package frc.robot.commands;

public class HatchGrabberToggle extends CommandBase {
	public HatchGrabberToggle() {
		requires(hatchMech);
	}

	@Override
	protected void initialize() {
		if(hatchMech.isGrabberExtended()){
			hatchMech.hatchGrabberRetract();
		}else{
			hatchMech.hatchGrabberExtend();
		}	
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
