package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class HatchMech extends Subsystem {
	private DoubleSolenoid hatchGrabber;
	private DoubleSolenoid hatchArm;

	public HatchMech() {
		hatchGrabber = new DoubleSolenoid(RobotMap.hatchGrabber1, RobotMap.hatchGrabber2);
		hatchArm = new DoubleSolenoid(RobotMap.hatchArm1, RobotMap.hatchArm2);
		hatchGrabberExtend();
		hatchArmRetract();
	}

	@Override
	public void initDefaultCommand() {

	}

	public void hatchGrabberExtend() {
		hatchGrabber.set(Value.kForward);
	}

	public void hatchGrabberRetract() {
		hatchGrabber.set(Value.kReverse);
	}

	public boolean isGrabberExtended() {
		return (hatchGrabber.get() == Value.kForward);
	}

	public void hatchArmExtend() {
		hatchArm.set(Value.kForward);
		System.out.println("Extending Arm");
	}

	public void hatchArmRetract() {
		hatchArm.set(Value.kReverse);
		System.out.println("Retracting arm");
	}

	public boolean isArmExtended() {
		return (hatchArm.get() == Value.kForward);
	}

	public void selfTest() {
		Robot.hatchArm.setBoolean(false);
		Robot.hatchNubs.setBoolean(false);

		hatchGrabberRetract();

		hatchArmExtend();
		Timer.delay(2);
		hatchGrabberExtend();
		Timer.delay(1);
		hatchGrabberRetract();
		Timer.delay(1);
		Robot.hatchNubs.setBoolean(true);
		hatchArmRetract();
		Timer.delay(1);
		Robot.hatchArm.setBoolean(true);

	}
}
