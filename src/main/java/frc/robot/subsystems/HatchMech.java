package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.StatTracker;

public class HatchMech extends Subsystem {
	private DoubleSolenoid hatchGrabber;
	private DoubleSolenoid hatchArm;

	public HatchMech() {
		hatchGrabber = new DoubleSolenoid(RobotMap.hatchGrabber1, RobotMap.hatchGrabber2);
		hatchArm = new DoubleSolenoid(RobotMap.hatchArm1, RobotMap.hatchArm2);
	}

	@Override
	public void initDefaultCommand() {

	}

	public void hatchGrabberExtend() {
		hatchGrabber.set(Value.kForward);
		StatTracker.addHatchExtension();
	}

	public void hatchGrabberRetract() {
		hatchGrabber.set(Value.kReverse);
	}

	public boolean isGrabberExtended() {
		return (hatchGrabber.get() == Value.kForward);
	}

	public void hatchArmExtend() {
		hatchArm.set(Value.kForward);
	}

	public void hatchArmRetract() {
		hatchArm.set(Value.kReverse);
	}

	public boolean isArmExtended() {
		return (hatchArm.get() == Value.kForward);
	}
}
