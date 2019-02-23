package frc.robot.commands;

public class HatchMechToggle extends CommandBase {
	public HatchMechToggle() {
		requires(hatchMech);
	}

	@Override
	protected void initialize() {
		// System.out.println(hatchMech.isExtended());
		if(hatchMech.isExtended()){
			hatchMech.hatchNubbinsRetract();
		}else{
			hatchMech.hatchNubbinsExtend();
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
