/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.StatTracker;

public class BallMechDownTilBall extends CommandBase {
	boolean ballAtBegin;
	public BallMechDownTilBall() {
		requires(ballMech);
	}

	@Override
	protected void initialize() {
		setTimeout(3);
		// ballAtBegin = ballMech.isBallPresent();
	}

	@Override
	protected void execute() {
		ballMech.intakeDown();
	}

	@Override
	protected boolean isFinished() {
		if (ballMech.isBallPresent() && !isTimedOut()) {
			StatTracker.addCargoHandled();
		}
		return ballMech.isBallPresent() && !isTimedOut();
	}

	@Override
	protected void end() {
		ballMech.intakeStop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
