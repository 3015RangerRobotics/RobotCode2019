package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class HatchMech extends Subsystem {
	private DoubleSolenoid hatchSolenoid;

	public HatchMech() {
		hatchSolenoid = new DoubleSolenoid(RobotMap.hatchSolenoid1, RobotMap.hatchSolenoid2);
	}

	@Override
	public void initDefaultCommand() {

	}

	public void hatchNubbinsExtend() {
		hatchSolenoid.set(Value.kForward);
	}

	public void hatchNubbinsRetract() {
		hatchSolenoid.set(Value.kReverse);
	}

	public boolean isExtended() {
		if(hatchSolenoid.get() == Value.kForward){
			return true;
		}else{
			return false;
		}
	}	
}
