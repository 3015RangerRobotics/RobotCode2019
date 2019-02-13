package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class HatchMech extends Subsystem {
	private Solenoid hatchGrabSolenoid;
	private Solenoid hatchEjectSolenoid;

	public HatchMech() {
		hatchGrabSolenoid = new Solenoid(RobotMap.hatchGrabSolenoid);
		hatchEjectSolenoid = new Solenoid(RobotMap.hatchEjectSolenoid);
	}

	@Override
	public void initDefaultCommand() {
		
	}

	public void hatchGrabExtend() {
		hatchGrabSolenoid.set(true);
	}

	public void hatchGrabRetract() {
		hatchGrabSolenoid.set(false);
	}

	public void hatchEjectExtend() {
		hatchEjectSolenoid.set(true);
	}

	public void hatchEjectRetract() {
		hatchEjectSolenoid.set(false);
	}
}
