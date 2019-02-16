/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class ClimberJackUpLow extends CommandBase {
  public ClimberJackUpLow() {
    requires(climber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
	// climber.setBackPosition(climber.backPosLow);
	// climber.setCenterPosition(climber.centerPosJacked);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
	  if(climber.getCenterPosition() <= climber.centerPosJacked) {
		climber.setCenterVelocity(2);
	  }
	  else {
		  climber.setCenterVelocity(0);
	  }

	  if(climber.getBackLeftPosition() <= climber.backPosLow) {
		  climber.setBackVelocityLeft(2);
	  }
	  else {
		  climber.setBackVelocityLeft(0);
	  }

	  if(climber.getBackRightPosition() <= climber.backPosLow) {
		  climber.setBackVelocityRight(2);
	  }
	  else {
		  climber.setBackVelocityRight(0);
	  }

	  System.out.println(climber.getCenterVelocity());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
	  climber.setCenter(ControlMode.PercentOutput, 0);
	  climber.setBackLeft(ControlMode.PercentOutput, 0);
	  climber.setBackRight(ControlMode.PercentOutput, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
	  end();
  }
}
