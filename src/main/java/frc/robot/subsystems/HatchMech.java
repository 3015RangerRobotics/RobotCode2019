package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class HatchMech extends Subsystem {
	private DoubleSolenoid hatchGrabSolenoid;
	private DoubleSolenoid hatchEjectSolenoid;

	public HatchMech() {
		hatchGrabSolenoid = new DoubleSolenoid(RobotMap.hatchGrabSolenoid1, RobotMap.hatchGrabSolenoid2);
		hatchEjectSolenoid = new DoubleSolenoid(RobotMap.hatchEjectSolenoid1, RobotMap.hatchEjectSolenoid2);
	}

	@Override
	public void initDefaultCommand() {

	}

	public void hatchGrabExtend() {
		hatchGrabSolenoid.set(Value.kForward);
	}

	public void hatchGrabRetract() {
		hatchGrabSolenoid.set(Value.kReverse);
	}

	public void hatchEjectExtend() {
		hatchEjectSolenoid.set(Value.kForward);
	}

	public void hatchEjectRetract() {
		hatchEjectSolenoid.set(Value.kReverse);
	}
}
