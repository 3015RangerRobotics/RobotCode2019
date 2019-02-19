/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class ClimberHoldAndDrive extends CommandBase {
  public ClimberHoldAndDrive() {
    // Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);\
	requires (climber); 
	requires (drive);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
	  
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
	climber.setBackLeft(ControlMode.PercentOutput, 0.1);
	climber.setBackRight(ControlMode.PercentOutput, 0.1);
	climber.setCenter(ControlMode.PercentOutput, 0.1);
	drive.arcadeDrive(-0.25, 0, false);
	climber.setCenterWheels(.4);
	System.out.println("Hi");
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
	return climber.getDistanceToWall() <= 0.15;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
	climber.setBackLeft(ControlMode.PercentOutput, 0);
	climber.setBackRight(ControlMode.PercentOutput, 0);
	climber.setCenter(ControlMode.PercentOutput, 0);
	drive.arcadeDrive(0, 0, false);
	climber.setCenterWheels(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
	  end();
  }
}
