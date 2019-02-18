/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class IntakeAid extends Subsystem {
	private VictorSP intakeAidSPLeft;
	private VictorSP intakeAidSPRight;

	private final double AID_INTAKE_SPEED = 1;
	private final double AID_OUTTAKE_SPEED = -1;

	public IntakeAid() {
		// intakeAidSPLeft = new VictorSP(RobotMap.intakeAidSPLeft);
		// intakeAidSPRight = new VictorSP(RobotMap.intakeAidSPRight);
	}

	@Override
	public void initDefaultCommand() {

	}

	public void intakeAidIntake() {
		intakeAidSPLeft.set(AID_INTAKE_SPEED);
		intakeAidSPRight.set(AID_INTAKE_SPEED);
	}

	public void intakeAidOuttake() {
		intakeAidSPLeft.set(AID_OUTTAKE_SPEED);
		intakeAidSPRight.set(AID_OUTTAKE_SPEED);
	}

	public void intakeAidStop() {
		intakeAidSPLeft.set(0);
		intakeAidSPRight.set(0);
	}
}
