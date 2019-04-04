package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class ElevatorManualControl extends CommandBase {
	

	public ElevatorManualControl() {
		requires(elevator);
	}

	protected void initialize() {
		
	}

	protected void execute() {
		double output = oi.getCoDriverRightStickY();
		
		if(output == 0){
			output = 0.175;
		}
		elevator.set(ControlMode.PercentOutput, output);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		elevator.set(ControlMode.PercentOutput, 0);
	}

	protected void interrupted() {
		end();
	}
}