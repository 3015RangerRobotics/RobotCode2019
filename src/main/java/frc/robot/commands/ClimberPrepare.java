package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class ClimberPrepare extends CommandBase {
	double targetPosition = 0;
	double startPosition = 0;
	public ClimberPrepare() {
		requires(climber);
	}

	@Override
	protected void initialize() {
		startPosition = climber.getCenterPosition();
		if(startPosition < climber.centerPosHigh - 1 || startPosition > climber.centerPosLow - 1) {
			targetPosition = climber.centerPosHigh;
		}else{
			targetPosition = climber.centerPosLow;
		}
	}

	@Override
	protected void execute() {
		climber.setCenter(ControlMode.PercentOutput, (startPosition < targetPosition) ? 0.39 : -0.39);
	}

	@Override
	protected boolean isFinished() {
		if(startPosition < targetPosition){
			return climber.getCenterPosition() >= targetPosition;
		}else{
			return climber.getCenterPosition() <= targetPosition;
		}
	}

	@Override
	protected void end() {
		climber.setCenter(ControlMode.PercentOutput, 0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
