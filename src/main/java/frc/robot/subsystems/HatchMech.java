package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.StatTracker;
import frc.robot.commands.CommandBase;

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

		hatchArmExtend();
		Timer.delay(3);
		hatchGrabberExtend();
		Timer.delay(2);
		hatchGrabberRetract();
		Timer.delay(2);
		Robot.hatchNubs.setBoolean(true);
		hatchArmRetract();
		Timer.delay(2);
		Robot.hatchArm.setBoolean(true);

	}
}
