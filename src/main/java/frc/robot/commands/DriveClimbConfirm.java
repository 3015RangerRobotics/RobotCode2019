package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class DriveClimbConfirm extends CommandBase {

	public DriveClimbConfirm() {
		requires(drive);
		requires(climber);
	}

	@Override
	protected void initialize() {
		drive.setRampRate(0);
		drive.setBrakeMode();
	}

	@Override
	protected void execute() {
		double driveValue = oi.getDriverLeftStickY();
		// double turnValue = oi.getDriverLeftStickX() * 0.5;

		drive.arcadeDrive(driveValue * 0.5, 0, true);

		climber.setBackLeft(ControlMode.PercentOutput, 0.1);
		climber.setBackRight(ControlMode.PercentOutput, 0.1);
		climber.setCenter(ControlMode.PercentOutput, 0.1);

		// if(driveValue < 0){
		// 	climber.setCenterWheels(1);
		// }else if(driveValue > 0){
		// 	climber.setCenterWheels(-1);
		// }else{
		// 	climber.setCenterWheels(0);
		// }		

		climber.setCenterWheels(-driveValue);
	}

	@Override
	protected boolean isFinished() {
		return oi.isDriverDDownPressed();
	}

	@Override
	protected void end() {
		drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
		climber.setBackLeft(ControlMode.PercentOutput, 0);
		climber.setBackRight(ControlMode.PercentOutput, 0);
		climber.setCenter(ControlMode.PercentOutput, 0);
		climber.setCenterWheels(0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
