/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;


public class BallMechDownTilBall extends CommandBase {
  public BallMechDownTilBall() {
    // Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(ballMech);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
	  if(ballMech.isBallPresent()) {
		  ballMech.ballMechDown();
	  }else {
		  ballMech.ballMechDown();
	  }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
	return ballMech.isBallPresent();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
	  ballMech.intakeStop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
	  end();
  }
}
