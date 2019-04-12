package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.Robot;

public class DriveTurnToTarget extends CommandBase implements PIDOutput, PIDSource {
	PIDController turnController;
	double minTurn = 0.0;//0.18;

	public DriveTurnToTarget() {
		requires(drive);
		turnController = new PIDController(drive.kTurnVisionP, drive.kTurnVisionI, drive.kTurnVisionD, this, this);
		turnController.setInputRange(-180.0, 180.0);
		turnController.setOutputRange(-1.0, 1.0);
		turnController.setAbsoluteTolerance(3);
	}

	@Override
	protected void initialize() {
		turnController.setSetpoint(0);
		turnController.enable();
	
		NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
		NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(0);
	}

	@Override
	protected void execute() {
		if(turnController.onTarget()){
			oi.driverRumble(1);
		}else {
			oi.driverRumble(0);
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
		turnController.disable();
		oi.driverRumble(0);
		
		NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
		NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
	}

	@Override
	protected void interrupted() {
		end();
	}

	@Override
	public void pidWrite(double output) {
		if (output < 0) {
			output -= minTurn;
		} else {
			output += minTurn;
		}
		// System.out.println(output);

		double driveValue = oi.getDriverLeftStickY();
		drive.arcadeDrive(Math.abs(driveValue) < 0.1 ? 0 : driveValue * driveValue * Math.signum(driveValue), -output, false);
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {

	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		return Robot.getLimelightTX();
	}
}
