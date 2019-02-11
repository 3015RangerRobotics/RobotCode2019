/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Command;

public class OurCompressorAuto extends CommandBase {
	private final double CUTOFF_VOLTAGE = 0;
  public OurCompressorAuto() {
    requires(ourCompressor);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
	  ourCompressor.startCompressor();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
	  if(RobotController.getBatteryVoltage() <= CUTOFF_VOLTAGE || (DriverStation.getInstance().getMatchTime() < 20 
	  	&& DriverStation.getInstance().getMatchTime() > 0)) {
		  ourCompressor.stopCompressor();
	  }else{
		  ourCompressor.startCompressor();
	  }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
	  ourCompressor.stopCompressor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
	  end();
  }
}