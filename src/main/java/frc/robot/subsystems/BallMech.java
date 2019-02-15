/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class BallMech extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
	private VictorSP ballMotor1;
	private DigitalInput ballSensor;

	private final double INTAKE_SPEED = 0.75;
	private final double OUTTAKE_SPEED = -0.85;

	public BallMech(){
		ballMotor1 = new VictorSP(RobotMap.ballMechVictor);
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

  public void ballMechUp(){
	  ballMotor1.set(INTAKE_SPEED);
  }

  public void ballMechDown(){
	  ballMotor1.set(OUTTAKE_SPEED);
  }

  public void intakeStop(){
	  ballMotor1.set(0);
  }
}
