/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.HashMap;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.motionProfiles.MotionProfiles;
import frc.robot.RobotMap.Side;

public class DriveMotionProfile extends CommandBase {
	private double[][] leftMotion;
	private double[][] rightMotion;

	public DriveMotionProfile(String filename) {
		requires(drive);
		this.leftMotion = MotionProfiles.loadProfile(filename + "_left");
		this.rightMotion = MotionProfiles.loadProfile(filename + "_right");
	}

	public DriveMotionProfile(double[][] motionProfile) {
		requires(drive);
		this.leftMotion = motionProfile;
		this.rightMotion = motionProfile;
	}

	public DriveMotionProfile(double[][] leftMotion, double[][] rightMotion) {
		requires(drive);
		this.leftMotion = leftMotion;
		this.rightMotion = rightMotion;
	}

	public DriveMotionProfile(HashMap<Side, double[][]> profiles) {
		requires(drive);
		this.leftMotion = profiles.get(Side.kLeft);
		this.rightMotion = profiles.get(Side.kRight);
	}

	public DriveMotionProfile(String filename, boolean mirrored) {
		requires(drive);
		if (!mirrored) {
			this.leftMotion = MotionProfiles.loadProfile(filename + "_left");
			this.rightMotion = MotionProfiles.loadProfile(filename + "_right");
		} else {
			this.leftMotion = MotionProfiles.loadProfile(filename + "_right");
			this.rightMotion = MotionProfiles.loadProfile(filename + "_left");
		}
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		SmartDashboard.putBoolean("PathRunning", true);
		BufferedTrajectoryPointStream leftBuffer = drive.getProfileBuffer(this.leftMotion);
		BufferedTrajectoryPointStream rightBuffer = drive.getProfileBuffer(this.rightMotion);

		drive.startMotionProfile(leftBuffer, rightBuffer);
		drive.startGraphing();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return drive.isProfileFinished();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		SmartDashboard.putBoolean("PathRunning", false);
		drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
		drive.stopGraphing();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
