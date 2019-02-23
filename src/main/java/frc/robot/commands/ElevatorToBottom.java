package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class ElevatorToBottom extends CommandBase {
	private double elevatorAtBottomCount = 0;

	public ElevatorToBottom() {
		requires(elevator);
	}

	@Override
	protected void initialize() {
		this.elevatorAtBottomCount = 0;
	}

	@Override
	protected void execute() {
		elevator.set(ControlMode.MotionMagic, elevator.elevatorHeightBottom * elevator.pulsesPerInch);
		if(elevator.isAtBottom()){
			this.elevatorAtBottomCount++;	 
		}else{
			this.elevatorAtBottomCount = 0;
		}

	}
	@Override
	protected boolean isFinished() {
		return this.elevatorAtBottomCount >= 15 && elevator.getDistance() < 1;
	}

	@Override
	protected void end() {
		elevator.set(ControlMode.PercentOutput, 0);
		if(elevator.isAtBottom()){
			elevator.resetEncoderPosition();
		}
	}

	@Override
	protected void interrupted() {
		end();
	}
}
