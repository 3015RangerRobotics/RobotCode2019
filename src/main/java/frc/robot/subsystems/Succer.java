package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * commence succing
 */
public class Succer extends Subsystem {
	private VictorSP vacuum;
	private AnalogPotentiometer pressureSensor;
	private DoubleSolenoid piston;
	private ArrayList<Double> pressureValues;

	public double pressureLockValue = -10.0;
	public double rateOfSucc = 1.0;

	public Succer() {
		vacuum = new VictorSP(RobotMap.succerVacuum);
		pressureSensor = new AnalogPotentiometer(RobotMap.succerSensor);
		piston = new DoubleSolenoid(RobotMap.succerPistonExtend, RobotMap.succerPistonRetract);
		pressureValues = new ArrayList<>();
		pressureValues.add(getPressure());
		succerPistonRetract();
	}

	@Override
	public void periodic() {
		if(pressureValues.size() == 20){
			pressureValues.remove(0);
			pressureValues.add(getPressure());
		} else {
			pressureValues.add(getPressure());
		}
	}

	@Override
	public void initDefaultCommand() {
		
	}

	public void succerPistonExtend() {
		piston.set(Value.kForward);
	}

	public void succerPistonRetract() {
		piston.set(Value.kReverse);
	}

	public void succ() {
		vacuum.set(rateOfSucc);	
	}

	public void noMoreSucc() {
		vacuum.set(0);
	}

	public double getPressure() {
		return pressureSensor.get();
	}

	public double getAveragePressure() {
		double sum = 0;
		for(int i = 0; i < pressureValues.size(); i++){
			sum += pressureValues.get(i);
		}
		return Math.round(sum / pressureValues.size());
	}

}
