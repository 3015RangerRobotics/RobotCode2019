package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class HatchMech extends Subsystem {
	private Solenoid hatchSolenoid;

	public HatchMech() {
		hatchSolenoid = new Solenoid(RobotMap.hatchSolenoid);
	}

	@Override
	public void initDefaultCommand() {
		
	}

	public void hatchMechExtend() {
		hatchSolenoid.set(true);
	}

	public void hatchMechRetract() {
		hatchSolenoid.set(false);
	}
}
