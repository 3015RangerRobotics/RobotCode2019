package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class ClimberJackUpLow extends CommandBase {
	boolean isCenterAtTarget = false;
	boolean isLeftAtTarget = false;
	boolean isRightAtTarget = false;
	boolean isAtBottom = false;

	public ClimberJackUpLow() {
		requires(climber);
	}

	@Override
	protected void initialize() {
		isCenterAtTarget = false;
		isLeftAtTarget = false;
		isRightAtTarget = false;
		isAtBottom = false;
	}

	@Override
	protected void execute() {
		if (climber.getCenterPosition() <= climber.centerPosJacked && !isCenterAtTarget) {
			climber.setCenterVelocity(climber.climbSpeed - climber.getRollOffset());
		} else {
			climber.setCenter(ControlMode.PercentOutput, 0.1);
			isCenterAtTarget = true;
		}

		if (climber.getBackLeftPosition() <= climber.backPosLow && !isLeftAtTarget) {
			climber.setBackVelocityLeft(climber.climbSpeed + climber.getRollOffset() + climber.getPitchOffset());
		} else {
			climber.setBackLeft(ControlMode.PercentOutput, 0.1);
			isLeftAtTarget = true;
		}

		if (climber.getBackRightPosition() <= climber.backPosLow && !isRightAtTarget) {
			climber.setBackVelocityRight(climber.climbSpeed + climber.getRollOffset() - climber.getPitchOffset());
		} else {
			climber.setBackRight(ControlMode.PercentOutput, 0.1);
			isRightAtTarget = true;
		}
	
		isAtBottom = climber.isAtBottom();

	}

	@Override
	protected boolean isFinished() {
		return (isRightAtTarget && isLeftAtTarget) && (isCenterAtTarget || isAtBottom);
	}

	@Override
	protected void end() {
		climber.setCenter(ControlMode.PercentOutput, 0);
		climber.setBackLeft(ControlMode.PercentOutput, 0);
		climber.setBackRight(ControlMode.PercentOutput, 0);
		System.out.println("neat neat");
	}

	@Override
	protected void interrupted() {
		end();
	}
}
