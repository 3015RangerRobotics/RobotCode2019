package frc.robot.commands;

public class HatchMechLock extends CommandBase {
	public HatchMechLock() {
		requires(hatchMech);
	}

	@Override
	protected void initialize() {
		// System.out.println(hatchMech.isExtended());
		if(hatchMech.isExtended()){
			hatchMech.hatchGrabRetract();
		}else{
			hatchMech.hatchGrabExtend();
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
