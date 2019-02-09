/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class BallMech extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
	private TalonSRX intakeMotor1;
	private DigitalInput ballSensor;

	private final double INTAKE_SPEED = 0.75;
	private final double OUTTAKE_SPEED = -0.85;

	public BallMech(){
		intakeMotor1 = new TalonSRX(RobotMap.ballMechTalon);
		ballSensor = new DigitalInput(RobotMap.ballLimitSwitch);
	}
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
	// setDefaultCommand(new MySpecialCommand());
  }
  public boolean isBallPresent(){
	return ballSensor.get();
  }

  public void intakeUp(){
	  intakeMotor1.set(ControlMode.PercentOutput, INTAKE_SPEED);
  }

  public void intakeDown(){
	  intakeMotor1.set(ControlMode.PercentOutput, OUTTAKE_SPEED);
  }

  public void intakeStop(){
	  intakeMotor1.set(ControlMode.PercentOutput, 0);
  }
}
