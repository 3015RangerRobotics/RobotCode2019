package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class BallMech extends Subsystem {
	public VictorSP intakeMotor1;
	private DigitalInput ballSensor;

	private final double INTAKE_SPEED = 1;
	private final double OUTTAKE_SPEED = -1;

	public BallMech() {
		intakeMotor1 = new VictorSP(RobotMap.ballMechVictor);
		ballSensor = new DigitalInput(RobotMap.ballLimitSwitch);
	}

	@Override
	public void initDefaultCommand() {

	}

	@Override
	public void periodic() {
		// System.out.println("isBallPresent: " + ifsBallPresent());
	}

	public boolean isBallPresent() {
		return !ballSensor.get();
	}

	public void intakeUp() {
		intakeMotor1.set(INTAKE_SPEED);
	}

	public void intakeDown() {
		intakeMotor1.set(OUTTAKE_SPEED);
	}

	public void intakeStop() {
		intakeMotor1.set(0);
	}

	public void selfTest() {
		Robot.ballIn.setBoolean(false);
		Robot.ballOut.setBoolean(false);

		intakeUp();
		while(!isBallPresent()){}
		intakeStop();	
		Robot.ballIn.setBoolean(true);
		Timer.delay(1);
		intakeDown();
		Timer.delay(0.5);
		Robot.ballOut.setBoolean(!isBallPresent());
		intakeStop();
	}
}
