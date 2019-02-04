/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

public class DriveTurnToAngle extends CommandBase implements PIDOutput {
	PIDController turnController;
	double setpoint = 0;
	int onTargetCount = 0;
	double minTurn = 0.2;
	public DriveTurnToAngle(double angle, boolean isAbsolute) {
		requires(drive);
        this.setpoint = angle;
        turnController = new PIDController(drive.kTurnP, drive.kTurnI, drive.kTurnD, drive.imu, this);
        turnController.setInputRange(-180.0, 180.0);
        turnController.setOutputRange(-1.0 + minTurn, 1.0 - minTurn);
        turnController.setAbsoluteTolerance(1.0);
		turnController.setContinuous(true);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		drive.resetGyro();
    	turnController.setSetpoint(setpoint);
    	turnController.enable();
		onTargetCount = 0;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double output = turnController.get();
    	
    	if(output < 0) {
    		System.out.println(output - minTurn);
    		drive.arcadeDrive(0, output - minTurn, false);
    	}else {
    		System.out.println(output + minTurn);
    		drive.arcadeDrive(0, output + minTurn, false);
    	}
    	
    	if(turnController.onTarget()) {
    		onTargetCount++;
    	}else {
    		onTargetCount = 0;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return onTargetCount >= 10;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		drive.setMotorOutputs(0, 0);
		turnController.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

	@Override
	public void pidWrite(double output) {

	}
}
